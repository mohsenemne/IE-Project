package joboonja;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

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

        response.setHeader("Access-Control-Allow-Origin", "http://react-app:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

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
            Algorithm algorithm = Algorithm.HMAC256("joboonja");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            request.setAttribute("username", jwt.getClaim("username").asString());
        } catch (Exception ignored) {
//            System.out.println("here");

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