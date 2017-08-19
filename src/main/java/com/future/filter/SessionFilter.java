package com.future.filter;

/**
 * Created by zhengming on 2017/8/19.
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Session过滤器,将所有的请求保存
 *
 */
public class SessionFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        SessionRecorder.setRequest((HttpServletRequest) request);
        SessionRecorder.setResponse((HttpServletResponse) response);
        chain.doFilter(request, response);
    }

    public void destroy() {
        SessionRecorder.destroy();
    }
}
