package org.dennis.proxy.controller;

import com.google.gson.annotations.Expose;

public class OAuthToken {

    public String access_token;

    @Expose(deserialize = false)
    public int expires_in;
    @Expose(deserialize = false)
    public int referesh_expires_in;
    @Expose(deserialize = false)
    public String refresh_token;
    @Expose(deserialize = false)
    public String token_type;
    @Expose(deserialize = false)
    public int not_before_policy;
    @Expose(deserialize = false)
    public String session_state;
    @Expose(deserialize = false)
    public String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getReferesh_expires_in() {
        return referesh_expires_in;
    }

    public void setReferesh_expires_in(int referesh_expires_in) {
        this.referesh_expires_in = referesh_expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getNot_before_policy() {
        return not_before_policy;
    }

    public void setNot_before_policy(int not_before_policy) {
        this.not_before_policy = not_before_policy;
    }

    public String getSession_state() {
        return session_state;
    }

    public void setSession_state(String session_state) {
        this.session_state = session_state;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
