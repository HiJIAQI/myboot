package com.itcast.demo.util;

import cn.util.enums.ResultEnum;
import cn.util.exception.BDException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itcast.demo.entity.SysUser;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/8/7 - 13:55
 */
public class JwtUtils {

    /*
        一般是在请求头里加入Authorization，并加上 Bearer 标注：
        fetch('api/user/1', {
          headers: {
            'Authorization': 'Bearer ' + token
          }
     */

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj
     */
    public static final String SECRET = "JKKLJOoasdlfj";
    /**
     * token 过期时间: 1分钟
     */
    public static final int calendarField = Calendar.MINUTE;
    public static final int calendarInterval = 1;

    /**
     * 创建JWT token
     * 由三部分组成：头部 荷载 签名
     * 格式：xxxxx.yyyyy.zzzzz
     *
     * @return token令牌
     */
    public static String createJwtToken(SysUser sysUser) {

        // 生成签名时间
        Date nowDate = new Date();

        // 过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // 构建头部信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // 构建密钥信息
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        // 创建token
        String JwtToken = JWT.create()
                // 设置头部信息
                .withHeader(map)
                // 设置 Token 签发者
                .withIssuer("MyBootAPP")
                // JWT的主体，即它的所有人 一般是用户id
                .withSubject(sysUser.getUserId().toString())
                // 签名生成的时间
                .withIssuedAt(nowDate)
                // 签名过期的时间
                .withExpiresAt(expiresDate)
                // 存储在JWT里面的信息 一般放些用户的权限/角色信息
                .withClaim("loginName", "zhuoqianmingyue")
                .withClaim("userName", "张三")
                .withClaim("deptName", "技术部")
                // 签名
                .sign(algorithm);
        return JwtToken;
    }

    /**
     * 验证 JWT Token
     *
     * @param token Token 字符串
     */
    public static Map<String, Claim> verifyJwtToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (TokenExpiredException e) {
            throw new BDException(ResultEnum.FAILURE, "TOKEN不合法,或已经过期！");
        } catch (Exception e) {
            throw new BDException(ResultEnum.FAILURE, "TOKEN不合法！");
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的id
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            throw new BDException(ResultEnum.FAILURE, "无效的token，请重新登录");
        }
    }

    /**
     * 判断token是否已经过期
     *
     * @return token中包含的id
     */
    public static boolean isExpiration(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().before(new Date());
        } catch (JWTDecodeException e) {
            throw new BDException(ResultEnum.FAILURE, "无效的token，请重新登录");
        }
    }

    public static void main(String[] args) {
        SysUser user = new SysUser();
        user.setUserId(1L);
        user.setUserName("张三");
        String jwtToken = JwtUtils.createJwtToken(user);
        System.out.println(jwtToken);
        System.out.println(JwtUtils.verifyJwtToken(jwtToken));
    }
}
