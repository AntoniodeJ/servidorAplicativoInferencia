package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
public class ServidorSocket extends Thread{

    ServerSocket serverSocket = null;
    Socket socket = null;
    PrintStream saida = null;
    static int port;//porta para comunicacao.
    BufferedReader entrada  = null;


    public ServidorSocket(Socket socket) {
        this.socket = socket;
    }

    public static void main(String args[]) {
        try {
        	port = Integer.parseInt(System.getenv("PORT"));
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("Aguardando conex�o na porta: "+port);
                Socket socket = serverSocket.accept();//aguarda conexao com o cliente.
                System.out.println("Conexão Estabelecida.");
                Thread t = new ServidorSocket(socket);
                t.start();//inicia uma nova thread. O metodo run é executado.
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    public void run() {
            ControlMensagem controlMensagem = new ControlMensagem(socket);
            controlMensagem.iniciar();

            System.out.println("Conexão com cliente encerrada");
    }
}
