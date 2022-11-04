package com.jfeat.am.module.house.services.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {

//    读取全部
    public static String readFile(String path){
        File file = new File(path);
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//构造一个BufferedReader类来读取文件

            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append( System.lineSeparator() + s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result.toString();
    }


//    按行读取返回列表
    public static List<String> readFileToList(String path){
        File file = new File(path);
        List<String> result = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.add(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
