//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.security.utils.security.esapi;

public class IUAPESAPI {
    private static final IUAPEncryptor encryptUtils = new IUAPEncryptor();
    private static final IUAPEncoder encoderUtils = new IUAPEncoder();

    public IUAPESAPI() {
    }

    public static IUAPEncryptor encryptor() {
        return encryptUtils;
    }

    public static IUAPEncoder encoder() {
        return encoderUtils;
    }
}
