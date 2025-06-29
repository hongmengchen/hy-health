package cn.rj.hyhealthbackend.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * @author 陈亮
 * <p>
 * JwtUtils工具类
 * 在属性中定义过期时间以及密钥，该工具类实现了获取token，判断token是否有效，获取Claims，根据token获取id
 */
public class JwtUtils {
    /**
     * 生成token的方法
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
     * 判断token是否有效的方法
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
     * 获取Claims的方法
     */
    public static Claims getClaims(String jwtToken) {
        if (!StringUtils.hasLength(jwtToken))
            return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return claims;
    }

    /**
     * 根据token获取用户id的方法
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (!StringUtils.hasLength(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }
}
