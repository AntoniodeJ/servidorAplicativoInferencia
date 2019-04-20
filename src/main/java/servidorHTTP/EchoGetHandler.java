package servidorHTTP;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EchoGetHandler implements HttpHandler {

	public void handle(HttpExchange exchange) throws IOException {
		
		// parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = exchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        ParseQuery.execute(query, parameters);

        // send response
        String response = "";
        for (String key : parameters.keySet())
                 response += key + " = " + parameters.get(key) + "\n";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes());

        os.close();
	}

}
