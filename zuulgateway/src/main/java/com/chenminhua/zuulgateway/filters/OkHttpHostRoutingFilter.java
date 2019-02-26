package com.chenminhua.zuulgateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

public class OkHttpHostRoutingFilter extends ZuulFilter {

    @Autowired
    private ProxyRequestHelper helper;

    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        this.helper.addIgnoredHeaders();

        // 特殊的接口跳过这个RoutingFilter，转到SimpleHostRoutingFilter
        if (context.getRequest().getRequestURI() == "/special") {
            return null;
        }

        // 禁用后面的SimpleHostRoutingFilter
        context.setSendZuulResponse(false);

        Request request = buildRequest(context);

//        try {
//            Response response = forward(request);
//            setResponse(response);
//        } catch (Exception ex) {
//            throw ZuulRuntimeException(handleException(ex));
//        }

        return null;
    }

    private Request buildRequest(RequestContext context) {
        URL host = context.getRouteHost();
        HttpServletRequest httpServletRequest = context.getRequest();
        String uri = getUri();
        String url = uri;  //可能需要进行一些处理
        MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(httpServletRequest);

        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(httpServletRequest.getMethod(), buildRequestBody(httpServletRequest));

//        headers.forEach { builder.header(it.key, it.value.first()) }

        return builder.build();
    }

    private RequestBody buildRequestBody(HttpServletRequest httpServletRequest) {
        if (!HttpMethod.permitsRequestBody(httpServletRequest.getMethod().toUpperCase())) {
            return null;
        }
        MediaType mediaType = getRequestContentType(httpServletRequest);
        int contentLength = httpServletRequest.getContentLength();
        if (contentLength < 0) {
            return null;
        }
//        ByteArray buffer = ByteArray(contentLength)
//        httpServletRequest.inputStream.read(buffer, 0, contentLength)
//        return RequestBody.create(mediaType, buffer)
    }

    private MediaType getRequestContentType(HttpServletRequest httpServletRequest) {
        // todo
    }

    private String getUri() {
        RequestContext context = RequestContext.getCurrentContext();
        return context.getRequest().getRequestURI().substring(context.get("proxy").toString().length() + 1);
    }
}
