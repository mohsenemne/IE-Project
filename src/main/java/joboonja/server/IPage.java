package joboonja.server;

import com.sun.net.httpserver.HttpExchange;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IPage {
	public void HandleRequest(HttpExchange httpExchange, String id, String applicantUser) throws IOException, ParseException;
}
