package com.itcast.demo.util;

import com.auth0.jwt.interfaces.Claim;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JwtFilter extends AuthenticatingFilter {

    /**
     * 创建令牌
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 获取前端所传来的token
        String jwt = request.getHeader(JwtUtils.TOKEN_HEADER);
        if (StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt.split(JwtUtils.TOKEN_PREFIX)[1]);
    }

    /**
     * 判定该请求是否允许访问，true: 则拦截器直接放行该请求; false: 将继续调用onAccessDenied方法
     * 1. 在url拦截规则中, 对于无需认证的url直接设置为anon(可匿名访问),故不会被该拦截器所拦截,
     * 2. 这里该方法可直接返回false,将是否需要进行认证放在 onAccessDenied 方法中去实现
     */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return false;
//    }

    /**
     * 未通过处理
     * 如果请求头中不含 token, 则返回false,拦截该请求;
     * 否则调用 executeLogin 方法 以进行基于token的自定义令牌的认证
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (StringUtils.isEmpty(jwt)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(401);
            httpServletResponse.getWriter().write("Error: No Token Message");
            return false;
        } else {
            // 校验jwt
            final String token = jwt.split(JwtUtils.TOKEN_PREFIX)[1];
            Map<String, Claim> claimMap = JwtUtils.verifyJwtToken(token);
            System.out.println("aaa");
            if (claimMap == null) {
                throw new ExpiredCredentialsException("token已失效，请重新登录");
            }
            // 执行登录
            return executeLogin(servletRequest, servletResponse);
        }
    }

    /**
     * 登陆失败执行方法，用于向浏览器返回认证失败时的响应信息
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(401);
        try {
            httpServletResponse.getWriter().write(e.getMessage());
        } catch (IOException ex) {
        }
        return false;
    }

    /**
     * 预先处理
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
