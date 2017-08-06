//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hesheng.auth.shiro;


import java.util.Map;

import com.hesheng.auth.token.ITokenProcessor;
import com.hesheng.auth.token.TokenParameter;
import org.apache.shiro.authc.AuthenticationToken;

public class StatelessToken implements AuthenticationToken {
    private static final long serialVersionUID = -7316737568510866049L;
    private String username;
    private Map<String, ?> params;
    private String clientDigest;
    ITokenProcessor tokenProcessor;
    TokenParameter tp;

    public StatelessToken(String username, ITokenProcessor tokenProcessor, TokenParameter tp, Map<String, ?> params, String clientDigest) {
        this.username = username;
        this.params = params;
        this.tp = tp;
        this.tokenProcessor = tokenProcessor;
        this.clientDigest = clientDigest;
    }

    public TokenParameter getTp() {
        return this.tp;
    }

    public void setTp(TokenParameter tp) {
        this.tp = tp;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, ?> getParams() {
        return this.params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }

    public String getClientDigest() {
        return this.clientDigest;
    }

    public void setClientDigest(String clientDigest) {
        this.clientDigest = clientDigest;
    }

    public Object getPrincipal() {
        return this.username;
    }

    public Object getCredentials() {
        return this.clientDigest;
    }

    public ITokenProcessor getTokenProcessor() {
        return this.tokenProcessor;
    }

    public void setTokenProcessor(ITokenProcessor tokenProcessor) {
        this.tokenProcessor = tokenProcessor;
    }
}
