//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesh;

import java.util.HashMap;
import java.util.Map;

public enum YNEnum {
    Y(Integer.valueOf(0), "启用"),
    N(Integer.valueOf(1), "禁用");

    private Integer code;
    private String name;
    private static final Map<Integer, YNEnum> map = new HashMap();

    static {
        YNEnum[] var3;
        int var2 = (var3 = values()).length;

        for(int var1 = 0; var1 < var2; ++var1) {
            YNEnum t = var3[var1];
            map.put(t.getCode(), t);
        }

    }

    private YNEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static YNEnum get(Integer code) {
        return (YNEnum)map.get(code);
    }

    public String toString() {
        return this.getCode() + ":" + this.getName();
    }
}
