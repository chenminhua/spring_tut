package com.chenminhua.zuulgateway.filters;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

enum HeaderType {
    HTTP_HEADER_X_AUTH ("X-AUTH");

    private final String type;

    HeaderType(String type) {
        this.type = type;
    }

    String type() {
        return type;
    }


}

public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        String url = context.getRequest().getRequestURI();
        String token = getToken(context);
        if (token == null) {
            setAuthFail(context);
        } else {
            // 鉴权，获取用户信息，修改header
            // getUserInfo
            // context.addZuulRequestHeader();
        }
        return null;
    }

    private void setAuthFail(RequestContext context) {
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(401);
        return;
    }

    private String getToken(RequestContext context) {
        String token = context.getRequest().getHeader(HeaderType.HTTP_HEADER_X_AUTH.type());
        //...
        return token;
    }

}
