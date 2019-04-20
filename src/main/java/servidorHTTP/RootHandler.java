package servidorHTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler implements HttpHandler {

	public void handle(HttpExchange exchange) throws IOException {
		String response = "<h1>Página funcionando, mas você não deveria estar aqui</h1>";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
                       
        os.write(response.getBytes());
        os.close();
	}

}
