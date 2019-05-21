package joboonja;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;


class FilterJWT implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        this.permittedAllURLs = Arrays.asList("/login", "/register");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = ((HttpServletRequest) servletRequest).getServletPath();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");

        if(permittedAllURLs.contains(path))
        {
            filterChain.doFilter(request, response);
            return;
        }

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return ;
        }

        String token = request.getHeader("Authorization");
        try {
            DecodedJWT jwt = JWT.decode(token);
            request.setAttribute("username", jwt.getClaim("username"));
        } catch (Exception ignored) {
            response.setStatus(401);
            return;
        }


        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private List<String> permittedAllURLs;
}