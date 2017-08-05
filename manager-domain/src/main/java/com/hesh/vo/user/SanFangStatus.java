package com.hesh.vo.user;

/**
 * Created with IntelliJ IDEA.
 * User: guojinquan1
 * Date: 17-8-3
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
public enum SanFangStatus {
     zcId("287",1),epaId("2319",2);
    // 项目编码
    private String code;
    private int index;

    private SanFangStatus(String code ,int index){
             this.code=code;
        this.index=index;
    }

    public static String getName(int index) {
        for (SanFangStatus c : SanFangStatus.values()) {
            if (c.getIndex() == index) {
                return c.code;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
