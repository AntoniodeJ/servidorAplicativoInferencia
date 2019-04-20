package util;

import model.SensorData;

public class ConversaoSensorData {

    public static String gerarStringPorSensorData(SensorData sensorData){
        String mensagem = "";
        if(sensorData!=null){
            mensagem = sensorData.getTipo()+";"+sensorData.getX()+";"+
                    sensorData.getY()+";"+sensorData.getZ();
        }
        return mensagem;
    }

    public static SensorData gerarSensorDataPorVetorString(String[] string){
        SensorData sensorData = null;
        String sensorTipo;
        float x,y,z;
        if (string != null){
			sensorTipo = string[0];
			x = Float.parseFloat(string[1]);
			y = Float.parseFloat(string[2]);
			z = Float.parseFloat(string[3]);
			sensorData = new SensorData(sensorTipo,x,y,z);
        }
        return sensorData;
    }
}
