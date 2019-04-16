package servidor;

import java.util.ArrayList;

import model.SensorData;
import util.MathSensorData;

public class ControlSensorData {

    private ArrayList<SensorData> listaSensorDataAccel;
    private ArrayList<SensorData> listaSensorDataGyro;

    private SensorData mediaAccel;
    private SensorData mediaGyro;
    private SensorData rmsAccel;
    private SensorData rmsGyro;

    private String nomeUsuario;
    private String atividade;
    private Boolean inferencia;

    public ControlSensorData(String nomeUsuario, Boolean inferencia, String atividade){

        this.nomeUsuario = nomeUsuario;
        this.inferencia = inferencia;
        this.atividade = atividade;

        listaSensorDataAccel = new ArrayList<SensorData>();
        listaSensorDataGyro = new ArrayList<SensorData>();
    }

    public void addSensorData(SensorData sensorData){
        if(sensorData.getTipo().equals("TYPE_ACCELEROMETER")){
            listaSensorDataAccel.add(sensorData);
        }else if(sensorData.getTipo().equals("TYPE_GYROSCOPE")) {
            listaSensorDataGyro.add(sensorData);
        }
    }

    public void finalizar(){
        if(!listaSensorDataAccel.isEmpty()){
            mediaAccel = MathSensorData.mediaAccel(listaSensorDataAccel);
            rmsAccel = MathSensorData.rmsAccel(listaSensorDataAccel);

            System.out.println("MediaAccel:\n" +
                    mediaAccel.getX()+"\n" +
                    mediaAccel.getY()+"\n" +
                    mediaAccel.getZ());
            System.out.println("RMSAccel:\n" +
                    rmsAccel.getX()+"\n" +
                    rmsAccel.getY()+"\n" +
                    rmsAccel.getZ());

        }
        if(!listaSensorDataGyro.isEmpty()){
            mediaGyro = MathSensorData.mediaGyro(listaSensorDataGyro);
            rmsGyro = MathSensorData.rmsGyro(listaSensorDataGyro);

            System.out.println("MediaGyro:\n" +
                    mediaGyro.getX()+"\n" +
                    mediaGyro.getY()+"\n" +
                    mediaGyro.getZ());
            System.out.println("RMSGyro:\n" +
                    rmsGyro.getX()+"\n" +
                    rmsGyro.getY()+"\n" +
                    rmsGyro.getZ());
        }

        if(!inferencia){ //Alterar Muito!
            ControlBD controlDB = new ControlBD();
            controlDB.connect();
            controlDB.saveTreinamento
            (nomeUsuario, atividade, mediaAccel, rmsAccel, mediaGyro, rmsGyro);
            controlDB.close();
            
        }else{
            // Realiza inferência e salva no BD de resultados
        }
    }
}
