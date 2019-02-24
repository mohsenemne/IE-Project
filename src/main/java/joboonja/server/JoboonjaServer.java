package joboonja.server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.parser.ParseException;


public class JoboonjaServer {
    public void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/project", new ViewHandler());
        server.createContext("/user", new ViewHandler());
        server.setExecutor(null);
        server.start();
    }

    class ViewHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(httpExchange.getRequestURI().getPath(), "/");
            String context = tokenizer.nextToken();
            String id = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
            String applicantUser = "1";
            Class<IPage> pageClass;
            try {
                String pageClassName = (context.equals("project")) ? ((id == null) ? "ProjectsListPage" : "ProjectInfoPage")
                        : context.equals("user") ? "UserInfoPage" : null;
                pageClass = (Class<IPage>) Class.forName("joboonja.server." + pageClassName);
                IPage newInstance = pageClass.getDeclaredConstructor().newInstance();
                newInstance.HandleRequest(httpExchange, id, applicantUser);
            } catch (ClassNotFoundException |
                    InstantiationException |
                    IllegalAccessException |
                    IllegalArgumentException |
                    InvocationTargetException |
                    NoSuchMethodException |
                    SecurityException |
                    ParseException e) {
                e.printStackTrace();
                String response =
                        "<html>"
                                + "<body>Page not found.</body>"
                                + "</html>";
                httpExchange.sendResponseHeaders(404, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    public static void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        byte[] bytes = response.getBytes();
        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
