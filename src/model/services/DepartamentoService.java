package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Departamento;

public class DepartamentoService {
	
	public List<Departamento> findAll(){
		List<Departamento> lista = new ArrayList<>();
		lista.add(new Departamento(1, "Libros"));
		lista.add(new Departamento(2, "Electrónicos"));
		lista.add(new Departamento(2, "Auto Piezas"));
		return lista;
	}
}
