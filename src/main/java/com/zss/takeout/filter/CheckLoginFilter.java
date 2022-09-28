package com.zss.takeout.filter;

import com.alibaba.fastjson.JSON;
import com.zss.takeout.common.BaseContext;
import com.zss.takeout.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经登录
 */
@WebFilter(filterName = "checkLoginFilter",urlPatterns = "/*")
@Slf4j
public class CheckLoginFilter implements Filter {

    //路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        log.info("拦截到路径:{}",requestURI);

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        boolean check = check(urls, requestURI);

        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //1-判断登录状态，如果已登录，放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录:{}",requestURI);

            Long empId = (Long) request.getSession().getAttribute("employee");
            //放入ThreadLocal中
            BaseContext.setCurrentId(empId);

            log.info("线程id为:{}",Thread.currentThread().getId());

            filterChain.doFilter(request,response);
            return;
        }

        //2-判断登录状态，如果已登录，放行
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录:{}",requestURI);

            Long userId = (Long) request.getSession().getAttribute("user");
            //放入ThreadLocal中
            BaseContext.setCurrentId(userId);

            log.info("线程id为:{}",Thread.currentThread().getId());

            filterChain.doFilter(request,response);
            return;
        }

        //未登录，通过输出流方式向客户端页面响应数据
        log.info("用户未登录:{}",requestURI);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
