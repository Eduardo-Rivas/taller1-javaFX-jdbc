package model.services;

import java.util.List;
import model.dao.DepartamentoDao;
import model.dao.FabricaDao;
import model.entities.Departamento;

public class DepartamentoService {
	//--Caragamos la Interface DepartamentoDao--//
	//--Vamos a la FabricaDao para Implementar--//
	//--la Clase DepartamentoDaoJDBC.java     --//
	private DepartamentoDao depDao = FabricaDao.crearDepartamentoDao();
	
	//--Método para Implementar el Método findAll()--//
	//--de la Clase   DepartamentoDaoJDBC.java     --//
	public List<Departamento> findAll(){
		return depDao.findAll();
	}
}
