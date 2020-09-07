package model.services;

import java.util.List;
import model.dao.DepartamentoDao;
import model.dao.FabricaDao;
import model.entities.Departamento;

public class DepartamentoService {
	
	private DepartamentoDao depDao = FabricaDao.crearDepartamentoDao();
	
	public List<Departamento> findAll(){
		return depDao.findAll();
	}
}
