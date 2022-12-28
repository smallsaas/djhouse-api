package com.jfeat.am.module.house.services.utility;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.UserHouseExcel;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {


    public static List<UserHouseExcel> readExcel(MultipartFile file) {

        List<UserHouseExcel> houseExcelList = new ArrayList<>();

        try {

            String originalFileName = file.getOriginalFilename();
            String extensionName = FilenameUtils.getExtension(originalFileName);
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();


            Workbook workbook = null;
            // 截取路径名 . 后面的后缀名，判断是xls还是xlsx
            // 如果这个判断不对，就把equals换成 equalsIgnoreCase()
            if (extensionName.equals("xls")){
                workbook = new HSSFWorkbook(inputStream);
            }else if (extensionName.equals("xlsx")){
                workbook = new XSSFWorkbook(inputStream);
            }

            // 获取第一张表
            Sheet sheet = workbook.getSheetAt(0);



            // sheet.getPhysicalNumberOfRows()获取总的行数
            // 循环读取每一行
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                // 循环读取每一个格
                Row row = sheet.getRow(i);
                // row.getPhysicalNumberOfCells()获取总的列数

                UserHouseExcel excel = new UserHouseExcel();

                for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
                    Cell cell = row.getCell(index);
                    // 转换为字符串类型
                    cell.setCellType(CellType.STRING);
                    // 获取得到字符串
                    String value = cell.getStringCellValue();

                    switch (index){
                        case 0:
                            excel.setId(Long.parseLong(value));
                            break;
                        case 1:
                            excel.setUserName(value);
                            break;
                        case 2:
                            excel.setCommunityName(value);
                            break;
                        case 3:
                            excel.setBuildingCode(value);
                            break;
                        case 4:
                            excel.setHouseNumber(value);
                            break;
                    }

                }

                if (excel!=null){
                    houseExcelList.add(excel);
                }


            }

            return houseExcelList;
        } catch (Exception e) {
            e.printStackTrace();
        }



        return houseExcelList;
    }


    public static ResponseEntity<byte[]> export2Excel(List<HouseAssetExchangeRequestRecord> exchangeRequestRecordList) {
        HttpHeaders headers = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.创建Excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            //2.创建文档摘要
            workbook.createInformationProperties();
            //3.获取文档信息，并配置
            DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();
            SummaryInformation si = workbook.getSummaryInformation();
            //4.1设置文档主题
            si.setSubject("同层匹配结果");
            //4.2.设置文档标题
            si.setTitle("同层匹配结果");
            //创建Excel表单
            HSSFSheet sheet = workbook.createSheet("同层匹配结果");
            //创建日期显示格式
            HSSFCellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //创建标题的显示样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //定义列的宽度
            sheet.setColumnWidth(0, 5 * 256);
            sheet.setColumnWidth(1, 12 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 5 * 256);
            sheet.setColumnWidth(4, 12 * 256);
            sheet.setColumnWidth(5, 20 * 256);

            //5.设置表头
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cell0 = headerRow.createCell(0);
            cell0.setCellValue("序号");
            cell0.setCellStyle(headerStyle);
            HSSFCell cell1 = headerRow.createCell(1);
            cell1.setCellValue("小区名");
            cell1.setCellStyle(headerStyle);
            HSSFCell cell2 = headerRow.createCell(2);
            cell2.setCellValue("业主楼栋编号");
            cell2.setCellStyle(headerStyle);
            HSSFCell cell3 = headerRow.createCell(3);
            cell3.setCellValue("业主门牌地址");
            cell3.setCellStyle(headerStyle);
            HSSFCell cell4 = headerRow.createCell(4);
            cell4.setCellValue("目标楼栋编号");
            cell4.setCellStyle(headerStyle);
            HSSFCell cell5 = headerRow.createCell(5);
            cell5.setCellValue("目标门牌地址");
            cell5.setCellStyle(headerStyle);
//            HSSFCell cell6 = headerRow.createCell(6);
//            cell6.setCellValue(" 匹配成功时间");
//            cell6.setCellStyle(headerStyle);

            //6.装数据
            for (int i = 0; i < exchangeRequestRecordList.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                HouseAssetExchangeRequestRecord record = exchangeRequestRecordList.get(i);
                row.createCell(0).setCellValue(i+1);
                row.createCell(1).setCellValue(record.getCommunityName());
                row.createCell(2).setCellValue(record.getBuildingCode());
                row.createCell(3).setCellValue(record.getNumber());
                row.createCell(4).setCellValue(record.getTargetBuildingCode());
                row.createCell(5).setCellValue(record.getTargetHouseNumber());
//                row.createCell(6).setCellValue(record.getCreateTime());
            }
            headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    "同层匹配成功记录.xls");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }


    public static void exportExcel(HttpServletResponse response,
                                   List<HouseAssetMatchLog>  houseAssetMatchLogList,
                                   String sheetName,
                                   String fileName,
                                   int columnWidth) throws IOException {
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表格列宽度
        sheet.setDefaultColumnWidth(columnWidth);

        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("序号");
        header.createCell(1).setCellValue("小区名");
        header.createCell(2).setCellValue("业主楼栋编号");
        header.createCell(3).setCellValue("业主门牌地址");
        header.createCell(4).setCellValue("目标楼栋编号");
        header.createCell(5).setCellValue("目标门牌地址");

        int rowIndex = 0;
        for (int i = 0; i < houseAssetMatchLogList.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            HouseAssetMatchLog record = houseAssetMatchLogList.get(i);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(record.getOwnerCommunity());
            row.createCell(2).setCellValue(record.getOwnerBuilding());
            row.createCell(3).setCellValue(record.getOwnerNumber());
            row.createCell(4).setCellValue(record.getMatchedBuilding());
            row.createCell(5).setCellValue(record.getMatchedNumber());
        }
        //设置中文文件名与后缀
        String encodedFileName = URLEncoder.encode(fileName + ".xlsx","utf-8").replaceAll("\\+", "%20");
        // 清除buffer缓存
        response.reset();
        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename="+encodedFileName+"");
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
        workbook.write(response.getOutputStream());
        //关闭workbook
        workbook.close();
    }


}
