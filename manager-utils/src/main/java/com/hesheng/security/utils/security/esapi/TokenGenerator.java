//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.security.utils.security.esapi;


import org.owasp.esapi.ESAPI;

public class TokenGenerator {
    public TokenGenerator() {
    }

    public static String genToken(String uname, long ts, String seed) throws EncryptException {
        return IUAPESAPI.encryptor().hash(uname + ts, seed);
    }

    public static String genSeed() throws EncryptException {
        return IUAPESAPI.encryptor().hash(new String(ESAPI.securityConfiguration().getMasterKey()), new String(ESAPI.securityConfiguration().getMasterSalt()));
    }
}
