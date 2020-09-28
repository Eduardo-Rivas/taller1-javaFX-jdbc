package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import db.Conexion;
import db.Dbexception;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDao{
	private Connection conn;
	  
	//--Constructor--//
	public VendedorDaoJDBC(Connection conn){
		this.conn = conn;
	}
	
	@Override
	public void insert(Vendedor ven) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
			    "INSERT INTO vendedor "
			   +"(Nombre,Email,Fecha,SalarioBase,DepartamentoId) "
			   +"VALUES (?,?,?,?,?)", 
			   Statement.RETURN_GENERATED_KEYS);
			
			//--Configuramos los Campos--//
			st.setString(1, ven.getNombre());
			st.setString(2, ven.getEmail());
			st.setDate(3, new java.sql.Date(ven.getFecha().getTime()));
			st.setDouble(4, ven.getSalarioBase());
			st.setInt(5, ven.getDepartamento().getId());
			
			int fila = st.executeUpdate();
			if(fila > 0) {
				//--Tomamos el Id Generado--//
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next() == true) {
					//--Asignamos el Id--//
					int id = rs.getInt(1);
					//--Lo Agregamos al Obj. Vendedor--//
					ven.setId(id);
				}
				Conexion.cerrarRs(rs);
			}
			else {
				throw new Dbexception("Error Inesperado, No Hay Registros Insertados");
			}
		} 
		catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarSt(st);
		}
	}//--Fin del Método insert()--//

	@Override
	public void update(Vendedor ven) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
				"UPDATE vendedor SET "
			   +"Nombre = ?, "
			   +"Email  = ?, "
			   +"Fecha  = ?, "
			   +"SalarioBase = ?, "
			   +"DepartamentoId = ? "
			   +"WHERE Id = ?");
			
			//--Configuramos los Campos--//
			st.setString(1, ven.getNombre());
			st.setString(2, ven.getEmail());
			st.setDate(3, new java.sql.Date(ven.getFecha().getTime()));
			st.setDouble(4, ven.getSalarioBase());
			st.setInt(5, ven.getDepartamento().getId());
			st.setInt(6, ven.getId());
			
			//--Ejecutamos la Actualización--//
			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarSt(st); 
		}
	}

	@Override
	public void deleteById(Vendedor ven) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
				"DELETE FROM vendedor WHERE  Id = ?");
			st.setInt(1, ven.getId());
			st.executeUpdate();
		} 
		catch (SQLException e) {
			throw new Dbexception(e.getMessage());	
		}
	}

	@Override
	public Vendedor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet         rs = null;
		
		try {
			st = conn.prepareStatement(
				"SELECT vendedor.*, departamento.Nombre AS Nomdep " 
			   +"FROM vendedor INNER JOIN departamento " 
			   +"ON vendedor.departamentoId = departamento.Id "
			   +"WHERE vendedor.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()){
				//--Instanciamos un Departamento y lo Asignamos--//
				Departamento dep = instandep(rs);
				  
				//--Instanciamos Vendedor y Asignamos--//
			    Vendedor ven = instanven(rs, dep);
			    return ven;
			}
			else {
				return null;
			}
				
		} catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarRs(rs);
			Conexion.cerrarSt(st);
		}
	}  

	@Override
	public List<Vendedor> findByDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet         rs = null;
		
		try {
			st = conn.prepareStatement(
				"SELECT vendedor.*, departamento.Nombre AS Nomdep " 
			   +"FROM vendedor INNER JOIN departamento " 
			   +"ON vendedor.departamentoId = departamento.Id "
			   +"WHERE departamentoId = ? "
			   +"ORDER BY vendedor.Nombre");
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			//--Instanciamos una Lista para Vendedores--//
			List<Vendedor> lista = new ArrayList<>();
			//--Instanciamos un Map para Instac. una sola Vez el Dep--//
			Map<Integer, Departamento> map = new HashMap<>();
			while(rs.next()){
				//--Asignamos el Id del Dpto. que está en el map--//
				Departamento dep1 = map.get(rs.getInt("departamentoId"));
				//--Id NO Está en el map Llamamos al Método--// 
				if(dep1 == null) {
					//--Método para Instanciar de Departamentos--//
					dep1 = instandep(rs);
					//--Guardamos en el Map--//
					map.put(rs.getInt("departamentoId"), dep1);
				}
				//--Método para Instanciar Vendedores--//
			    Vendedor ven = instanven(rs, dep1);
			    //--Guardamos en la Lista los Vendedores--//
			    lista.add(ven);
			}
			return lista;
		} catch (SQLException e) {
			throw new Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarRs(rs);
			Conexion.cerrarSt(st);
		}
	}

	@Override
	public List<Vendedor> findAll() {
		PreparedStatement st = null;
		ResultSet         rs = null;
		
		try {
			st = conn.prepareStatement(
			    "SELECT vendedor.*, departamento.Nombre AS Nomdep "
			   +"FROM vendedor INNER JOIN departamento "
			   +"ON vendedor.departamentoId = departamento.Id "
			   +"ORDER BY vendedor.Nombre");

			rs = st.executeQuery();
			List<Vendedor> lista = new ArrayList<>();
			HashMap<Integer, Departamento> map = new HashMap<>();
			
			while(rs.next() == true) {
				//--Tomo el Valor del Depart. segun el Id--//
				Departamento dep1 = map.get(rs.getInt("departamentoId"));
				 
				//--Id del Departamento no existe--//
				if(dep1 == null){
					//--Método para Instanciar Departamento--//
					dep1 = instandep(rs);
					
					//--Agregamos al map--//
					map.put(dep1.getId(), dep1);
				}
				//--Método para Instanciar Vendedor--//
				Vendedor ven = instanven(rs, dep1);
				//--Agregamos Vendedor a la Lista--//
				lista.add(ven);
			}
			return lista;
		} 
		catch (SQLException e) {
			throw new  Dbexception(e.getMessage());
		}
		finally {
			Conexion.cerrarRs(rs);
			Conexion.cerrarSt(st);
		}
	}

	
	//--Método Interno para Instanciar Vendedor--//
	private Vendedor instanven(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor obj = new Vendedor();
		obj.setId(rs.getInt("Id"));
		obj.setNombre(rs.getString("Nombre"));
		obj.setEmail(rs.getString("Email"));
		obj.setFecha(rs.getDate("Fecha"));
		obj.setSalarioBase(rs.getDouble("salarioBase"));
		obj.setDepartamento(dep);
		return obj;
	}

	//--Método Interno para Instanciar Departamento--//
	private Departamento instandep(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartamentoId"));
		dep.setNombre(rs.getString("Nomdep"));
		return dep;
	}





}
