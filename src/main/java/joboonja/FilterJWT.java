package joboonja;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
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

        if(permittedAllURLs.contains(path))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
//        System.out.println(token);
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