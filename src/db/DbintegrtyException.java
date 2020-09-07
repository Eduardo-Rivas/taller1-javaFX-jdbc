package db;

public class DbintegrtyException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	//--Definimos el Constructor--//
	public DbintegrtyException(String msg) {
		super(msg);
	}

}
