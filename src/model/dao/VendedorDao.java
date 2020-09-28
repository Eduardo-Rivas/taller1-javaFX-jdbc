package model.dao;

import java.util.List;

import model.entities.Departamento;
import model.entities.Vendedor;

public interface VendedorDao {
	//--Metodos a Implementar--//
	void insert(Vendedor ven);
	void update(Vendedor ven);
	void deleteById(Vendedor ven);

	//--Vendedor por Código--//
	Vendedor findById(Integer id);
	
	//--Vendedor por Departamento pasando el departamento--//
	List<Vendedor> findByDepartamento(Departamento departamento);
	
	//--Todos los Vendedores--//
	List<Vendedor> findAll();
}
