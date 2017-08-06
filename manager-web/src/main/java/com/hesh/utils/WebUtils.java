package com.hesh.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Created by gaods on 2017/8/6.
 */
public class WebUtils {


    /**
     * Username=111&password=111
     * 转换为 map
     * @param param
     * @return
     */
    public static HashMap<String,Object> covertToMap(String param){

        HashMap<String,Object> result=new HashMap<String,Object>();

        if( StringUtils.isEmpty(param))
            return result;


        String[] paramtemps=param.split("&");

        for(String temp:paramtemps){
            String[] temps=temp.split("=");
            if(temps.length<=1)continue;
            if( StringUtils.isEmpty(temps[0]))continue;

            result.put(temps[0],temps[1]);
        }
        return result;

    }


    public static void  main(String[] args){
        String cs1="Username=111";
       HashMap result= covertToMap(cs1);
        System.out.print(result.get("Username"));
    }


}
