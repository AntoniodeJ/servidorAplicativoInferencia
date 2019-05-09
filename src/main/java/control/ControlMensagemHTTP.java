package control;

import java.util.Map;

public class ControlMensagemHTTP {
	
	private Map<String, Object> parametros;
	
	public ControlMensagemHTTP(Map<String, Object> parametros) {
		this.parametros = parametros;
	}
	
	public Boolean executar() {
		String nome = (String) parametros.get("Nome");
		String tipo = (String) parametros.get("Tipo");
		String atividade;
		String mensagemSensorsData;
		ControlSensorData controlSensorData;
		
		if(tipo.equals("Treinamento")) {
			atividade = (String) parametros.get("Atividade");
			controlSensorData = new ControlSensorData(nome, false, atividade);
		}else if(tipo.equals("Inferencia")) {
			atividade = "?";
			controlSensorData = new ControlSensorData(nome, true, atividade);
		}else {
			System.out.println("O tipo n�o foi definido");
			return false;
		}		
		
		
		mensagemSensorsData = (String) parametros.get("SensorsData");
        String[] sensorsData = mensagemSensorsData.split("<SD>");
        for (String sensorData : sensorsData) {
        	//System.out.println(sensorData);
        	controlSensorData.addSensorData
        	(util.ConversaoSensorData.gerarSensorDataPorVetorString
        			(sensorData.split(";")));
        }
        controlSensorData.finalizar();
        return true;
	}
	
}
