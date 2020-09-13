package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {
	//--Metodos a Implementar--//
	void insert(Departamento dep);
	void update(Departamento dep);
	void deleteById(Integer id);
	Departamento findById(Integer id);
	List<Departamento> findAll();
	Integer goToReg();
}
