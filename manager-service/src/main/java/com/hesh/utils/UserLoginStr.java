package com.hesh.utils;

import java.util.HashMap;

/**
 * Created by gaods on 2017/8/1.
 */
public class UserLoginStr {

    private static HashMap<String,String> h_state=new  HashMap<String,String>();

    static {
        h_state.put("0","帐户处于禁止使用状态");
        h_state.put("-1","调用接又失败");
        h_state.put("","");
        h_state.put("","");
        h_state.put("","");
    }

}
