package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetComplaintDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetComplaintRecord;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseEquityDemandSupplyMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.utility.FileUtility;
import com.jfeat.am.module.kafkaEmail.util.TemplateUtil;
import com.jfeat.am.module.task.services.crud.service.SnpoolService;
import com.jfeat.am.module.task.services.crud.service.WorkTaskService;
import com.jfeat.am.module.task.services.domain.model.WorkTaskModel;
import com.jfeat.am.module.task.services.persistence.dao.WorkTaskMapper;
import com.jfeat.am.module.task.services.persistence.model.WorkTask;
import com.jfeat.am.module.taskQueue.services.domain.service.TaskQueueMassageService;
import com.jfeat.am.module.taskQueue.services.domain.service.TaskQueueService;
import com.jfeat.am.module.taskQueue.services.gen.persistence.model.TaskQueue;
import com.jfeat.crud.plus.CRUD;
import com.jfeat.module.kafka.services.domain.model.KafkaEmailProperties;
import com.jfeat.module.kafka.services.domain.service.KafkaEmailProductService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class HouseEmailServiceImpl implements HouseEmailService {

    private static final Logger log = LoggerFactory.getLogger(HouseEmailService.class);

    private static final String EMAIL_LIST_PATH = "./email/email-list.txt";

    private static final String QUEUE_HOUSE_CHANCE = "QUEUE-HOUSE-CHANCE";

    private static final String QUEUE_HOUSE_RENT = "QUEUE-HOUSE-RENT";

    private static final String QUEUE_HOUSE_EQUITY = "QUEUE-HOUSE-EQUITY";

    private static final String QUEUE_HOUSE_COMPLAINT = "QUEUE_HOUSE_COMPLAINT";

    private static final String QUEUE_HOUSE_ASSET_NUMBER = "QUEUE_HOUSE_ASSET_NUMBER";

    private static final String HOUSE_MATCH_USER_TEMPLATE = "【通知】{targetBuilding}栋{targetAsset}房的户主希望与您的{building}栋{asset}房交换";

    private static final String HOUSE_MATCH_SALES_TEMPLATE="【通知】{targetBuilding}栋{targetAsset}房的户主与{building}栋{asset}房的户主有交换意向。{targetBuilding}栋{targetAsset}房的户主信息为:名字：{targetUserName}，电话：{targetUserPhone}。{building}栋{asset}房的户主信息为：姓名：{username},电话:{userPhone}";

    private static final String HOUSE_COMPLAINT_TEMPLATE = "【通知】用户：【{userName}】对【{buildingCode}-{houseNumber}】发起产权申诉，请处理待办事项： 【{taskName}-{taskNumber}】。用户信息：昵称：【{userName}】，电话号码： 【{userPhone}】，真实姓名： 【{realName}】";


    private static final String HOUSE_RENT_TEMPLATE = "【通知】用户：{username} 出租了{communityName}小区{buildCode}栋{houseNumber}，此房产不在系统中，请处理待办事项： 【{taskName}-{taskNumber}】，把出租房产信息加入到系统中。用户真实姓名：{realName},用户联系电话：{phone}";

    private static final String HOUSE_EQUITY_TEMPLATE = "【通知】用户：{username} {demand}面积{area}平方。真实姓名：{realName},电话：{phone}";

    private static final String HOUSE_ASSET_LIMIT = "【通知】{communityName}小区{buildCode}栋{houseNumber}房在{date}切换了产权，切换房东用户信息：昵称：【{userName}】，电话号码： 【{userPhone}】，真实姓名： 【{realName}】，被切换房东用户信息：昵称：【{oldUserName}】，电话号码： 【{oldUserPhone}】，真实姓名： 【{oldRealName}】，请确认产权";

    private static final int DEADLINE_DAY = 7;

    @Resource
    TaskQueueService taskQueueService;

    @Resource
    KafkaEmailProductService kafkaEmailProductService;


    @Resource
    SnpoolService snpoolService;

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    WorkTaskService workTaskService;

    @Resource
    TaskQueueMassageService taskQueueMassageService;

    @Resource
    QueryHouseAssetComplaintDao queryHouseAssetComplaintDao;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    @Resource
    HouseEquityDemandSupplyMapper houseEquityDemandSupplyMapper;

    @Resource
    WorkTaskMapper workTaskMapper;


    /**
     * 交换成功发送消息
     * @param matchLogList
     * @param exchangeRequests
     */
    @Override
    public void sendAssetMatchLog(List<HouseAssetMatchLog> matchLogList, List<HouseAssetExchangeRequestRecord> exchangeRequests) {

        Long userId = JWTKit.getUserId();
        new Thread(() -> {
//做处理
            try {
                for (HouseAssetMatchLog houseAssetMatchLog:matchLogList){
                    Map<String,String> tempalteMap = new HashMap<>();
                    tempalteMap.put("targetBuilding",houseAssetMatchLog.getMatchedBuilding()==null?"":houseAssetMatchLog.getMatchedBuilding());
                    tempalteMap.put("targetAsset",houseAssetMatchLog.getMatchedNumber()==null?"":houseAssetMatchLog.getMatchedNumber());
                    tempalteMap.put("building",houseAssetMatchLog.getOwnerBuilding()==null?"":houseAssetMatchLog.getOwnerBuilding());
                    tempalteMap.put("asset",houseAssetMatchLog.getOwnerNumber()==null?"":houseAssetMatchLog.getOwnerNumber());
                    String mas = TemplateUtil.getContent(HOUSE_MATCH_USER_TEMPLATE,tempalteMap);

                    if (houseAssetMatchLog.getOwnerEmail()!=null && !houseAssetMatchLog.getOwnerEmail().equals("") && verifyEmail(houseAssetMatchLog.getOwnerEmail())){
                        KafkaEmailProperties kafkaEmailProperties = new KafkaEmailProperties();
                        kafkaEmailProperties.setSubject("换房意向");
                        kafkaEmailProperties.setContent(mas);
                        kafkaEmailProperties.setToAddress(houseAssetMatchLog.getOwnerEmail());
                        kafkaEmailProductService.sendTextEmail(kafkaEmailProperties);
                    }
                }

                List<String> emailList = FileUtility.readFileToList(EMAIL_LIST_PATH);
                if (emailList==null||emailList.size()<=0){
                    return;
                }

//        发送给销售

                for (HouseAssetExchangeRequestRecord houseAssetExchangeRequestRecord:exchangeRequests){
                    Map<String,String> tempalteMap = new HashMap<>();
                    tempalteMap.put("targetBuilding",houseAssetExchangeRequestRecord.getTargetBuildingCode()==null?"":houseAssetExchangeRequestRecord.getTargetBuildingCode());
                    tempalteMap.put("targetAsset",houseAssetExchangeRequestRecord.getTargetHouseNumber()==null?"":houseAssetExchangeRequestRecord.getTargetHouseNumber());
                    tempalteMap.put("building",houseAssetExchangeRequestRecord.getBuildingCode()==null?"":houseAssetExchangeRequestRecord.getBuildingCode());
                    tempalteMap.put("asset",houseAssetExchangeRequestRecord.getNumber()==null?"":houseAssetExchangeRequestRecord.getNumber());

                    tempalteMap.put("targetUserName",houseAssetExchangeRequestRecord.getTargetRealName()==null?"":houseAssetExchangeRequestRecord.getTargetRealName());
                    tempalteMap.put("targetUserPhone",houseAssetExchangeRequestRecord.getTargetPhone()==null?"":houseAssetExchangeRequestRecord.getTargetPhone());
                    tempalteMap.put("username",houseAssetExchangeRequestRecord.getRealName()==null?"":houseAssetExchangeRequestRecord.getRealName());
                    tempalteMap.put("userPhone",houseAssetExchangeRequestRecord.getUserPhone()==null?"":houseAssetExchangeRequestRecord.getUserPhone());

                    String mas = TemplateUtil.getContent(HOUSE_MATCH_SALES_TEMPLATE,tempalteMap);

                    WorkTaskModel workTaskModel =  this.createWorkTask(QUEUE_HOUSE_CHANCE,"换房匹配记录",mas,userId,emailList);
                    this.houseQueueTaskMessage(workTaskModel,true);
                    log.info("sendAssetMatchLog:",mas);
                }
            }catch (Exception e){
                log.info("sendAssetMatchLog 异常",e.toString());
            }
        }).start();
    }

    @Override
    public void sendComplaintAssetInfo(HouseAssetComplaint complaint) {

        Long userId = JWTKit.getUserId();
        new Thread(() -> {
//做处理

            try {
                List<String> emailList = FileUtility.readFileToList(EMAIL_LIST_PATH);
                if (emailList==null||emailList.size()<=0){
                    return;
                }

                HouseAssetComplaintRecord houseAssetComplaintRecord =  queryHouseAssetComplaintDao.queryMastRecord(complaint.getId());
                if (houseAssetComplaintRecord==null){
                    return;
                }
                WorkTaskModel workTaskModel =  this.createWorkTask(QUEUE_HOUSE_COMPLAINT,"产权申述","",userId,emailList);
                Map<String,String> tempalteMap = new HashMap<>();
                tempalteMap.put("userName",houseAssetComplaintRecord.getUserName()==null?"":houseAssetComplaintRecord.getUserName());
                tempalteMap.put("buildingCode",houseAssetComplaintRecord.getBuildingCode()==null?"":houseAssetComplaintRecord.getBuildingCode());
                tempalteMap.put("houseNumber",houseAssetComplaintRecord.getHouseNumber()==null?"":houseAssetComplaintRecord.getHouseNumber());
                tempalteMap.put("userPhone",houseAssetComplaintRecord.getUserPhone()==null?"":houseAssetComplaintRecord.getUserPhone());
                tempalteMap.put("realName",houseAssetComplaintRecord.getRealName()==null?"":houseAssetComplaintRecord.getRealName());
                tempalteMap.put("taskName",workTaskModel.getTaskName()==null?"":workTaskModel.getTaskName());
                tempalteMap.put("taskNumber",workTaskModel.getTaskNumber()==null?"":workTaskModel.getTaskNumber());

                String mas = TemplateUtil.getContent(HOUSE_COMPLAINT_TEMPLATE,tempalteMap);

                WorkTask workTask = workTaskMapper.selectById(workTaskModel.getId());
                workTask.setDesc(mas);
                workTaskMapper.updateById(workTask);

                workTaskModel.setDesc(mas);
                this.houseQueueTaskMessage(workTaskModel,true);
                log.info("sendComplaintAssetInfo:",mas);
            }catch (Exception e){
                log.info("sendComplaintAssetInfo 异常",e.toString());
            }
        }).start();
    }


    @Override
    public void sendRentAssetInfo(HouseRentAsset entity) {
        Long userId = JWTKit.getUserId();
        new Thread(() -> {
//做处理
            try {
                List<String> emailList = FileUtility.readFileToList(EMAIL_LIST_PATH);
                if (emailList==null||emailList.size()<=0){
                    return;
                }

                HouseRentAsset houseRentAsset  =  houseRentAssetMapper.selectById(entity.getId());
                if (houseRentAsset==null){
                    return;
                }

                UserAccount userAccount =  userAccountMapper.selectById(houseRentAsset.getLandlordId());
                if (userAccount==null){
                    return;
                }

                WorkTaskModel workTaskModel =  this.createWorkTask(QUEUE_HOUSE_RENT,"出租房屋","",userId,emailList);

                Map<String,String> templateMap = new HashMap<>();


                templateMap.put("username",userAccount.getName()==null?"":userAccount.getName());
                templateMap.put("realName",userAccount.getRealName()==null?"":userAccount.getRealName());
                templateMap.put("phone",userAccount.getPhone()==null?"":userAccount.getPhone());

                templateMap.put("communityName",houseRentAsset.getCommunityName()==null?"":houseRentAsset.getCommunityName());
                templateMap.put("buildCode",houseRentAsset.getBuildingCode()==null?"":houseRentAsset.getBuildingCode());
                templateMap.put("houseNumber",houseRentAsset.getHouseNumber()==null?"":houseRentAsset.getHouseNumber());
                templateMap.put("taskName",workTaskModel.getTaskName()==null?"":workTaskModel.getTaskName());
                templateMap.put("taskNumber",workTaskModel.getTaskNumber()==null?"":workTaskModel.getTaskNumber());
                String mas = TemplateUtil.getContent(HOUSE_RENT_TEMPLATE,templateMap);

                WorkTask workTask = workTaskMapper.selectById(workTaskModel.getId());
                workTask.setDesc(mas);
                workTaskMapper.updateById(workTask);

                workTaskModel.setDesc(mas);

                this.houseQueueTaskMessage(workTaskModel,true);
                log.info("sendRentAssetInfo:",mas);
            }catch (Exception e){
                e.printStackTrace();
                log.info("sendRentAssetInfo 异常",e.toString());
            }
        }).start();


    }

    @Override
    public void sendEquityDemand(HouseEquityDemandSupply entity) {

        Long userId = JWTKit.getUserId();
        new Thread(() -> {
//做处理
            try {
                List<String> emailList = FileUtility.readFileToList(EMAIL_LIST_PATH);
                if (emailList==null||emailList.size()<=0){
                    return;
                }

                HouseEquityDemandSupply houseEquityDemandSupply  =  houseEquityDemandSupplyMapper.selectById(entity.getId());
                if (houseEquityDemandSupply==null){
                    return;
                }

                UserAccount userAccount =  userAccountMapper.selectById(houseEquityDemandSupply.getUserId());
                if (userAccount==null){
                    return;
                }
                Map<String,String> templateMap = new HashMap<>();
                templateMap.put("username",userAccount.getName()==null?"":userAccount.getName());
                templateMap.put("realName",userAccount.getRealName()==null?"":userAccount.getRealName());
                templateMap.put("phone",userAccount.getPhone()==null?"":userAccount.getPhone());

                if (houseEquityDemandSupply.getEquityOption().equals(HouseEquityDemandSupply.EQUITY_OPTION_DEMAND)){
                    templateMap.put("demand","需求");
                }else if (houseEquityDemandSupply.getEquityOption().equals(HouseEquityDemandSupply.EQUITY_OPTION_SUPPLY)){
                    templateMap.put("demand","供给");
                }else {
                    templateMap.put("demand","");
                }

                templateMap.put("area",houseEquityDemandSupply.getArea().toString()==null?"":houseEquityDemandSupply.getArea().toString());
                String mas = TemplateUtil.getContent(HOUSE_EQUITY_TEMPLATE,templateMap);


                WorkTaskModel workTaskModel =  this.createWorkTask(QUEUE_HOUSE_EQUITY,"方数买卖",mas,userId,emailList);
                this.houseQueueTaskMessage(workTaskModel,true);
                log.info("sendEquityDemand:",mas);
            }catch (Exception e){
                e.printStackTrace();
                log.info("sendEquityDemand 异常",e.toString());
            }
        }).start();

    }

    @Override
    public void sendMoreThanAssetLimit(Long oldUser,Long newUser,HouseAssetModel houseAssetModel) {
        Long userId = JWTKit.getUserId();
        new Thread(() -> {
//做处理
            try {
                List<String> emailList = FileUtility.readFileToList(EMAIL_LIST_PATH);
                if (emailList==null||emailList.size()<=0){
                    return;
                }


                Map<String,String> templateMap = new HashMap<>();
                UserAccount userAccount =  userAccountMapper.selectById(newUser);

                UserAccount oldUserAccount = userAccountMapper.selectById(oldUser);

                if (userAccount==null||oldUserAccount==null){
                    return;
                }

                templateMap.put("communityName",houseAssetModel.getCommunityName()==null?"":houseAssetModel.getCommunityName());
                templateMap.put("buildCode",houseAssetModel.getBuildingCode()==null?"":houseAssetModel.getBuildingCode());
                templateMap.put("houseNumber",houseAssetModel.getHouseNumber()==null?"":houseAssetModel.getHouseNumber());
                templateMap.put("userName",userAccount.getName()==null?"":userAccount.getName());
                templateMap.put("realName",userAccount.getRealName()==null?"":userAccount.getRealName());
                templateMap.put("userPhone",userAccount.getPhone()==null?"":userAccount.getPhone());
                templateMap.put("oldUserName",oldUserAccount.getName()==null?"":oldUserAccount.getName());
                templateMap.put("oldRealName",oldUserAccount.getRealName()==null?"":oldUserAccount.getRealName());
                templateMap.put("oldUserPhone",oldUserAccount.getPhone()==null?"":oldUserAccount.getPhone());
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = format.format(date);
                templateMap.put("date",dateStr);

                String mas = TemplateUtil.getContent(HOUSE_ASSET_LIMIT,templateMap);

                WorkTaskModel workTaskModel =  this.createWorkTask(QUEUE_HOUSE_ASSET_NUMBER,"房屋产权",mas,userId,emailList);
                this.houseQueueTaskMessage(workTaskModel,true);
                log.info("sendMoreThanAssetLimit:",mas);
            }catch (Exception e){
                e.printStackTrace();
                log.info("sendMoreThanAssetLimit 异常",e.toString());
            }
        }).start();
    }


    @Override
    public boolean verifyEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();

    }


//    添加天数
    public static Date dateAddOne(Date date,int day) {
        Calendar   calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,day);
        date=calendar.getTime();
        return date;

    }


//    消息队列控制
    private void houseQueueTaskMessage(WorkTaskModel workTaskModel,boolean isKafka){
        if (workTaskModel!=null){
            taskQueueMassageService.taskQueueMessageControl(workTaskModel,isKafka);
        }
    }

    private WorkTaskModel createWorkTask(String queueName,String taskName,String msg,Long userId,List<String> emailList) {
        TaskQueue taskQueue = taskQueueService.getTaskQueueByName(queueName);

        WorkTaskModel workTaskModel = new WorkTaskModel();
        workTaskModel.setQueueId(taskQueue.getId());
        workTaskModel.setTaskName(taskName);
        workTaskModel.setTaskNumber(snpoolService.getSerial("TD"));
        workTaskModel.setToEmailAddressList(emailList);
        workTaskModel.setDesc(msg);
        workTaskModel.setTaskType("TODO");
        workTaskModel.setStartTime(new Date());
        workTaskModel.setDeadline(dateAddOne(new Date(), DEADLINE_DAY));
        workTaskModel.setCloseTime(dateAddOne(new Date(), DEADLINE_DAY));

        UserAccount userAccount = userAccountMapper.selectById(userId);
        workTaskService.createMasterByStaff(workTaskModel, userAccount, true);

        return workTaskModel;

    }
}
