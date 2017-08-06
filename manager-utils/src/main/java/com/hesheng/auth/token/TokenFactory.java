//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.token;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TokenFactory {
    private Map<String, ITokenProcessor> processors = new HashMap();

    public TokenFactory() {
    }

    public void setProcessors(List<ITokenProcessor> processors) {
        Iterator i$ = processors.iterator();

        while(i$.hasNext()) {
            ITokenProcessor processor = (ITokenProcessor)i$.next();
            this.processors.put(processor.getId(), processor);
        }

    }

    public ITokenProcessor getTokenProcessor(String token) {
        TokenInfo ti = getTokenInfo(token);
        return this.getTokenProcessorById(ti.getProcessor());
    }

    public ITokenProcessor getTokenProcessorById(String id) {
        return (ITokenProcessor)this.processors.get(id);
    }

    public static TokenInfo getTokenInfo(String token) {
        String ntoken = StringUtils.newStringUtf8(Base64.decodeBase64(StringUtils.getBytesUtf8(token)));
        String[] tokenInfo = ntoken.split(",");
        TokenInfo ti = new TokenInfo();
        ti.setProcessor("web");
        ti.setExpr("3600");
        ti.setToken(tokenInfo[0]);
        return ti;
    }
}
