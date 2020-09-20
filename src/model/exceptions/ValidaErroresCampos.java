package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidaErroresCampos extends RuntimeException {
	private static final long serialVersionUID = 1L;

	//--Definios una Coleccion Map para Agrgar Errores--//
	private Map<String, String> errores = new HashMap<>();
	
	//--Costructor recibienfo Argumento--//
	public ValidaErroresCampos(String msg) {
		super(msg);
	}

	//--Método para Tomar los Errores--//
	public Map<String, String> getErrores(){
		return errores;
	}

	//--Método para Adicionar Errores al Map--//
	public void addErrores(String nomCampo, String nomMsg) {
		errores.put(nomCampo, nomMsg);
	}
	
}
