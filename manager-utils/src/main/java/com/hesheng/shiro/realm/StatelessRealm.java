package com.hesheng.shiro.realm;

import com.hesheng.auth.session.ISessionManager;
import com.hesheng.auth.shiro.StatelessToken;
import com.hesheng.auth.token.ITokenProcessor;
import com.hesheng.auth.token.TokenFactory;
import com.hesheng.auth.token.TokenParameter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class StatelessRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(StatelessRealm.class);
    @Autowired
    TokenFactory tokenFactory;
    @Autowired
    private ISessionManager sessionManager;

    public StatelessRealm() {
    }

    public boolean supports(AuthenticationToken token) {
        return token instanceof StatelessToken;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ArrayList roles = new ArrayList();
        info.addRoles(roles);
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken atoken) throws AuthenticationException {
        StatelessToken token = (StatelessToken)atoken;
        TokenParameter tp = token.getTp();
        String uname = (String)token.getPrincipal();
        ITokenProcessor tokenProcessor = token.getTokenProcessor();
        String tokenStr = tokenProcessor.generateToken(tp);
        if(tokenStr != null && this.sessionManager.validateOnlineSession(uname, tokenStr)) {
            return new SimpleAuthenticationInfo(uname, tokenStr, this.getName());
        } else {
            logger.error("User [{}] authenticate fail in System, maybe session timeout!", uname);
            throw new AuthenticationException("User " + uname + " authenticate fail in System");
        }
    }

    private boolean validateOnlineSession(String uname, String tokenStr) {
        String type = "";
        if (type.equals("cluster")) {
//            SessionClusterManager sessionClusterManager = (SessionClusterManager)this.sessionManager;
//            return sessionClusterManager.validateOnlineSession(uname, tokenStr);
            return true;
        } else {
            return this.sessionManager.validateOnlineSession(uname, tokenStr);
        }
    }

}