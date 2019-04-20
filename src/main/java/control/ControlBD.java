package control;

import model.BD;
import model.SensorData;

public class ControlBD {

	public BD banco;
	
	public ControlBD() {
		banco = new BD();
	}
	
	public void connect() {
		banco.connect();
	}
	
	public void close() {
		banco.close();
	}
	
	public void saveTreinamento(String nome, String atividade,
		SensorData mediaAccel, SensorData rmsAccel,
		SensorData mediaGyro, SensorData rmsGyro) {
		
		if(mediaAccel != null && rmsAccel != null && mediaGyro != null && rmsGyro != null) {
			banco.saveTreinamento(nome, atividade,
					mediaAccel.getX(), mediaAccel.getY(), mediaAccel.getZ(),
					rmsAccel.getX(), rmsAccel.getY(), rmsAccel.getZ(),
					mediaGyro.getX(), mediaGyro.getY(), mediaGyro.getZ(),
					rmsGyro.getX(), rmsGyro.getY(), rmsGyro.getZ());
		}else if(mediaAccel != null && rmsAccel != null) {
			banco.saveTreinamento(nome, atividade,
					mediaAccel.getX(), mediaAccel.getY(), mediaAccel.getZ(),
					rmsAccel.getX(), rmsAccel.getY(), rmsAccel.getZ(),
					0, 0, 0, 0, 0, 0);
		}else if(mediaGyro != null && rmsGyro != null ) {
			banco.saveTreinamento(nome, atividade,
					0,0,0,0,0,0,
					mediaGyro.getX(), mediaGyro.getY(), mediaGyro.getZ(),
					rmsGyro.getX(), rmsGyro.getY(), rmsGyro.getZ());
		}else {
			System.out.println("Não há dados!");
		}
	}
}
