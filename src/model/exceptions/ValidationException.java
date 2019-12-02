package model.exceptions;

import java.util.HashMap;
import java.util.Map;



public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	//atributo
	
	//Map e uma coleção de pares chave valor
	//primeiro string e chave , o segundo string e o vlaor
	private Map<String, String> erros = new HashMap<>();
	
	//Exceção para validar um formulário
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String, String> getErros(){
		return erros;
	}	
		
	public void addErro(String fieldName, String erroMensagem) {
		erros.put(fieldName, erroMensagem);
	}

}
