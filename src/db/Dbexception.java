package db;

public class Dbexception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	//--Definimos el Constructor--//
	public Dbexception(String msg) {
		super(msg);
	}

}
