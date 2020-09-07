package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Conexion {
	public static Connection conn = null;
	
	//--Cramos un Método para Abrir la Conexión--//
	public static Connection getConexion() {
		if(conn == null) {
			try {
				Properties props = loadProps();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
				System.out.println("Conexión Exitosa...");
			} 
			catch (SQLException e) {
				throw new Dbexception(e.getMessage());
			}
		}
		return conn;
	}
	
	//--Método cerrarConexion()--//
	public static void cerrarSt(Statement st) {
		try {
			st.close();
		} catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
	}
	
	//--Método cerrarRs()--//
	public static void cerrarRs(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
	}
	
	//--Método para Cerrar la Conexión--//
	public static void cerrarConexion() {
		try {
			conn.close();
			System.out.println("Conexión Carrada...");

		} 
		catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
	}
	
	//--Creamos un Metodo para Cargar--//
	//--Las Propiedades del Archivo  --//
	private static Properties loadProps() {
		try(FileInputStream fs = new FileInputStream("db.properties")) {
            //--Creamos un Obj. Properties--//
			Properties props = new Properties();
			//--Cargamos las Propiedades del Archvo-//
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new Dbexception(e.getMessage());
		}
	}
	
}
