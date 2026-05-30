package com.parameters;

import com.utils.GlobalVars;


public class LoginParams {
    public static void paramsReplace() {

        GlobalVars.vars.put("${username}","test");
        GlobalVars.vars.put("${password}","123456");
    }
}
