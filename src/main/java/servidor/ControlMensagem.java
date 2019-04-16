package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import model.SensorData;
import util.ConversaoSensorData;

public class ControlMensagem {

    Socket socket;
    PrintStream saida;
    BufferedReader entrada;

    ControlSensorData controlSensorData;
    Boolean recebimentoIniciado;

    public ControlMensagem(Socket socket){
        this.socket = socket;
        try {
            saida = new PrintStream(this.socket.getOutputStream());
            entrada = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        recebimentoIniciado = false;
    }

    public void iniciar(){
        try {
            while (!socket.isClosed()) {
                String requisicao = entrada.readLine();//le as strings do cliente

                if (requisicao == null) {  //ATENÇÃO, TÁ BUGADO!
                    break;
                }

                String[] mensagem = requisicao.split(";");

                //INTERPRETAR MENSAGEM
                switch (mensagem[0]){
                    case "INICIAR_TREINAMENTO":{
                        if(!recebimentoIniciado){
                            recebimentoIniciado = true;
                            controlSensorData = new ControlSensorData(mensagem[1], false, mensagem[2]);
                            System.out.println("Iniciou Treinamento! " + mensagem[2]);
                        }
                        break;
                    }
                    case "INICIAR_INFERENCIA":{
                        if(!recebimentoIniciado){
                            recebimentoIniciado = true;
                            controlSensorData = new ControlSensorData(mensagem[1], true, "?");
                            System.out.println("Iniciou Inferência!");
                        }
                        break;
                    }
                    case "DADOS":{
                        if(recebimentoIniciado){
                            SensorData sensorData = ConversaoSensorData.gerarSensorDataPorVetorString(mensagem);
                            controlSensorData.addSensorData(sensorData);
                            System.out.println("Recebendo!");
                        }
                        break;
                    }
                    case "FINALIZAR":{
                        if(recebimentoIniciado){
                            recebimentoIniciado = false;
                            controlSensorData.finalizar();
                            socket.close();
                            System.out.println("Finalizou envio!");
                        }
                        break;
                    }
                }
            }

        }catch (IOException e) {
            System.out.println("ERRO:" + e);
        }
    }

}
