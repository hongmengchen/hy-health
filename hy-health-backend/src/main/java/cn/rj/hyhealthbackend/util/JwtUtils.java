package cn.rj.hyhealthbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 陈亮
 * <p>
 * JwtUtils 工具类
 * 在属性中定义过期时间以及密钥，该工具类实现了获取 token，判断 token 是否有效，获取 Claims，根据 token 获取 id
 */
public class JwtUtils {
    // 添加常量定义
    private static final long EXPIRE = 1000 * 60 * 60 * 24; // 24小时有效期
    private static final String APP_SECRET = "medical-user-secret"; // 签名密钥

    /**
     * 生成 token 的方法
     */
    public static String getJwtToken(Long id, String uname, String role) {
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")  //jwet头信息
                .setHeaderParam("alg", "HS256")
                .setSubject("medical-user")  //分类（自定义）
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)  //token载体（用户id和用户名）
                .claim("uname", uname)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        return JwtToken;
    }

    /**
     * 判断 token 是否有效的方法
     */
    public static boolean checkToken(String jwtToken) {
        if (!StringUtils.hasLength(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if (!StringUtils.hasLength(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取 Claims 的方法
     */
    public static Claims getClaims(String jwtToken) {
        if (!StringUtils.hasLength(jwtToken))
            return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return claims;
    }

    /**
     * 根据 token 获取用户 id 的方法
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (!StringUtils.hasLength(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }
}
