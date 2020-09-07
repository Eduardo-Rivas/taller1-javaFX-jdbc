package model.dao;

import db.Conexion;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class FabricaDao {
	
	//--Método Statico Para Instancia el Vendedor--//
	public static VendedorDao  crearVendedorDao() {
		//--Instanciamos la Implementacion VendedorDaoJDBC()--//
		//--la  cual  implementa  la  Interface VendedorDao --//
		return new VendedorDaoJDBC(Conexion.getConexion());
			
	}

	//--Método Statico Para Instancia Un Departamento--//
	public static DepartamentoDao crearDepartamentoDao() {
		return new DepartamentoDaoJDBC(Conexion.getConexion()); 
	}
}
