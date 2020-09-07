package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Conexion;
import db.Dbexception;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {
	private Connection conn;
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Departamento dep) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
				 "INSERT INTO departamento (Nombre) " 
				+"VALUES(?)",
				Statement.RETURN_GENERATED_KEYS);
			
			//--Configuramos los Campos--//
			st.setString(1, dep.getNombre());
			
			int filas = st.executeUpdate();
			if(filas > 0){
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next() == true) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
				Conexion.cerrarRs(rs);
			}
			else {
				throw new Dbexception("Error Inesperado, No Hay Registros Insertados");
			}
		} catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarSt(st);
		}
	}//--Fin del Método insert()--//

	@Override
	public void update(Departamento dep) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
				"UPDATE departamento SET "
			   +"Nombre = ?"
			   +"WHERE Id = ?");  
			
			//--Configuramos los Campos--//
			st.setString(1, dep.getNombre());
			st.setInt(2, dep.getId());
			
			//--Ejecutamos la Actualización--//
			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarSt(st); 
		}
	}//--Fin del Método update()--//

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
				"DELETE FROM departamento WHERE  Id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new Dbexception(e.getMessage());	
		}
	}//--Fin del Método deleteById()--//

	@Override
	public Departamento findById(Integer id) {
		PreparedStatement st = null;
		ResultSet         rs = null; 
		
		try {
			st = conn.prepareStatement(
				 "SELECT * FROM departamento "
				+"WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next() == true){
				Departamento dep1 = instanDep(rs);
				return dep1;
			}
			else {
				return null;
			}
		} catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
	}//--Fin del Método findById()--//

	@Override
	public List<Departamento> findAll() {
		PreparedStatement st = null;
		ResultSet         rs = null;
		
		try {
			st = conn.prepareStatement(
				"SELECT Id, Nombre FROM departamento "
			   +"ORDER BY Id");
			rs = st.executeQuery();
			List<Departamento> lista = new ArrayList<>();
			while(rs.next()){
				Departamento dep1 = instanDep(rs);
				lista.add(dep1);
			} 
			return lista; 
		} 
		catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarRs(rs);
			Conexion.cerrarSt(st);
		}
	}//--Fin del Método findAll()--//
	
	//--Metodo Privado para Instanciar Departamento--//
	private Departamento instanDep(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Id"));
		dep.setNombre(rs.getString("Nombre"));		
		return dep;
	}//--Fin del Método Interno instanDep()--//

}//--Fin de la Clase DepartamentoDaoJDBC--//
