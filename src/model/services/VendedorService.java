package model.services;

import java.util.List;
import model.dao.FabricaDao;
import model.dao.VendedorDao;
import model.entities.Vendedor;
 
public class VendedorService {
	//--Caragamos la Interface VendedorDao--//
	//--Vamos a la FabricaDao para Implementar--//
	//--la Clase VendedorDaoJDBC.java     --//
	private VendedorDao venDao = FabricaDao.crearVendedorDao();
	
	//--Método para Implementar el Método findAll()--//
	//--de la Clase   VendedorDaoJDBC.java     --//
	public List<Vendedor> findAll(){
		return venDao.findAll();
	}
	
	//--Método para Incluir o Actualizar Dpto.--//
	public void insertOrUpdate(Vendedor obj) {
		if(obj.getId() == null) {
			venDao.insert(obj);
		}
		else {
			venDao.update(obj);
		}
		
	} 
	
	//--Método para Eliminar Un Vendedor--//
	public void remove(Vendedor obj) {
		venDao.deleteById(obj);
	}
	
}
