package com.future.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zhengming on 2017/8/19.
 */
public class SessionRecorder {
    private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

    public static HttpServletRequest getRequest() {
        return requestLocal.get();
    }

    public static void setRequest(HttpServletRequest request) {
        requestLocal.set(request);
    }

    public static HttpServletResponse getResponse() {
        return responseLocal.get();
    }

    public static void setResponse(HttpServletResponse response) {
        responseLocal.set(response);
    }

    public static HttpSession getSession() {
        return requestLocal.get().getSession();
    }

    public static void destroy() {
        requestLocal.remove();
        responseLocal.remove();
    }
}
