package servidorHTTP;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;

public class MainServidor {
	
	public static void main(String args[]) {
		int port = Integer.parseInt(System.getenv("PORT"));
		//int port = 9000;
		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			System.out.println("server started at " + port);
			server.createContext("/", new RootHandler());
			server.createContext("/echoHeader", new EchoHeaderHandler());
			server.createContext("/echoGet", new EchoGetHandler());
			server.createContext("/echoPost", new EchoPostHandler());
			server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
