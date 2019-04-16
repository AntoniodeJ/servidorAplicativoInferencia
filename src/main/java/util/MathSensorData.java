package util;

import java.util.ArrayList;

import model.SensorData;

public class MathSensorData {

    public static SensorData mediaAccel(ArrayList<SensorData> listaAccel){

        float somaX = 0;
        float somaY = 0;
        float somaZ = 0;
        float mediaX, mediaY, mediaZ;

        for(int i=0;i<listaAccel.size();i++){
            somaX += listaAccel.get(i).getX();
            somaY += listaAccel.get(i).getY();
            somaZ += listaAccel.get(i).getZ();
        }

        mediaX = somaX/listaAccel.size();
        mediaY = somaY/listaAccel.size();
        mediaZ = somaZ/listaAccel.size();

        return new SensorData("TYPE_ACCELEROMETER",mediaX,mediaY,mediaZ);
    }

    public static SensorData rmsAccel(ArrayList<SensorData> listaAccel){

        float somaX = 0;
        float somaY = 0;
        float somaZ = 0;
        float rmsX, rmsY, rmsZ;

        for(int i=0;i<listaAccel.size();i++){
            somaX += Math.pow(listaAccel.get(i).getX(),2);
            somaY += Math.pow(listaAccel.get(i).getY(),2);
            somaZ += Math.pow(listaAccel.get(i).getZ(),2);
        }

        rmsX = (float) Math.sqrt(somaX/listaAccel.size());
        rmsY = (float) Math.sqrt(somaY/listaAccel.size());
        rmsZ = (float) Math.sqrt(somaZ/listaAccel.size());

        return new SensorData("TYPE_ACCELEROMETER",rmsX,rmsY,rmsZ);
    }

    public static SensorData mediaGyro(ArrayList<SensorData> listaGyro){
        ArrayList<Float> listaPicosX = new ArrayList<Float>();
        ArrayList<Float> listaPicosY = new ArrayList<Float>();
        ArrayList<Float> listaPicosZ = new ArrayList<Float>();

        ArrayList<Float> listaGyroX = new ArrayList<Float>();
        ArrayList<Float> listaGyroY = new ArrayList<Float>();
        ArrayList<Float> listaGyroZ = new ArrayList<Float>();

        float somaX = 0;
        float somaY = 0;
        float somaZ = 0;
        float mediaX, mediaY, mediaZ;

        for(int i=0 ; i < listaGyro.size() ; i++){
            listaGyroX.add(listaGyro.get(i).getX());
            listaGyroY.add(listaGyro.get(i).getY());
            listaGyroZ.add(listaGyro.get(i).getZ());
        }
        encontrarPicos(listaGyroX,listaPicosX);
        encontrarPicos(listaGyroY,listaPicosY);
        encontrarPicos(listaGyroZ,listaPicosZ);

        for(int i=0;i<listaPicosX.size();i++){
            somaX += listaPicosX.get(i);
        }
        for(int i=0;i<listaPicosY.size();i++){
            somaY += listaPicosY.get(i);
        }
        for(int i=0;i<listaPicosZ.size();i++){
            somaZ += listaPicosZ.get(i);
        }

        mediaX = somaX/listaPicosX.size();
        mediaY = somaY/listaPicosY.size();
        mediaZ = somaZ/listaPicosZ.size();

        return new SensorData("TYPE_GYROSCOPE",mediaX,mediaY,mediaZ);
    }

    public static SensorData rmsGyro(ArrayList<SensorData> listaGyro){
        ArrayList<Float> listaPicosX = new ArrayList<Float>();
        ArrayList<Float> listaPicosY = new ArrayList<Float>();
        ArrayList<Float> listaPicosZ = new ArrayList<Float>();

        ArrayList<Float> listaGyroX = new ArrayList<Float>();
        ArrayList<Float> listaGyroY = new ArrayList<Float>();
        ArrayList<Float> listaGyroZ = new ArrayList<Float>();

        float somaX = 0;
        float somaY = 0;
        float somaZ = 0;
        float rmsX, rmsY, rmsZ;

        for(int i=0 ; i < listaGyro.size() ; i++){
            listaGyroX.add(listaGyro.get(i).getX());
            listaGyroY.add(listaGyro.get(i).getY());
            listaGyroZ.add(listaGyro.get(i).getZ());
        }

        encontrarPicos(listaGyroX,listaPicosX);
        encontrarPicos(listaGyroY,listaPicosY);
        encontrarPicos(listaGyroZ,listaPicosZ);

        for(int i=0;i<listaPicosX.size();i++){
            somaX += Math.pow(listaPicosX.get(i),2);
        }
        for(int i=0;i<listaPicosY.size();i++){
            somaY += Math.pow(listaPicosY.get(i),2);
        }
        for(int i=0;i<listaPicosZ.size();i++){
            somaZ += Math.pow(listaPicosZ.get(i),2);
        }

        rmsX = (float) Math.sqrt(somaX/listaPicosX.size());
        rmsY = (float) Math.sqrt(somaY/listaPicosY.size());
        rmsZ = (float) Math.sqrt(somaZ/listaPicosZ.size());

        return new SensorData("TYPE_GYROSCOPE",rmsX,rmsY,rmsZ);
    }

    // Não pega extremidades
    public static void encontrarPicos(ArrayList<Float> listaEntrada, ArrayList<Float> listaSaida){

        int atual = 0;
        int prox = atual+1;
        float elementoAtual=0;
        float elementoProx=0;
        Boolean procurandoMax = true;

        while(prox < listaEntrada.size()){
            if(procurandoMax){
                elementoAtual = listaEntrada.get(atual);
                elementoProx = listaEntrada.get(prox);
                if(elementoAtual <= elementoProx){
                    atual++;
                    prox++;
                }else{
                    listaSaida.add(elementoAtual);
                    procurandoMax = false;
                }
            }else if(!procurandoMax){
                elementoAtual = listaEntrada.get(atual);
                elementoProx = listaEntrada.get(prox);
                if(elementoAtual >= elementoProx){
                    atual++;
                    prox++;
                }else{
                    listaSaida.add(elementoAtual);
                    procurandoMax = true;
                }
            }
        }
    }
}
