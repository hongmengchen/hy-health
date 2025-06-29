package cn.rj.hyhealthbackend.filter;

import cn.rj.hyhealthbackend.model.AccountModel;
import cn.rj.hyhealthbackend.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * @author 陈亮
 * <p>
 * JwtFilter继承OncePerRequestFilter
 * 重写doFilterInternal方法，通过请求头发送过来的token查询用户信息并判断用户身份并赋予此次用户访问的权限
 */
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");
        httpServletResponse.setContentType("text/json;charset=utf-8");
        if (StringUtils.hasLength(token)) {
            String uname = null;
            try {
                uname = (String) JwtUtils.getClaims(token).get("uname");
                System.out.println(uname);
            } catch (ExpiredJwtException e) {
                logger.info("失效身份");
            }
            if (uname != null) {
                String role = (String) JwtUtils.getClaims(token).get("role");
                UserDetails userDetails = userDetailService.loadUserByUsername(uname);
                AccountModel model = (AccountModel) userDetails;
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
                UsernamePasswordAuthenticationToken authenticationFilter = new UsernamePasswordAuthenticationToken(model, null, grantedAuthorities);
                authenticationFilter.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
