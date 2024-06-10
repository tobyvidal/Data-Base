package ObjectDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import com.objectdb.o._RollbackException;

import Modelo.ModeloAlquiler;


public class CrearOBDB {

	public static void main(String[] args) {
		ModeloAlquiler alquiler01 = new ModeloAlquiler("Tobias", "Zaragoza", 750.00,6);
		ModeloAlquiler alquiler02 = new ModeloAlquiler("Raquel", "Barcelona", 780.70,6);
		ModeloAlquiler alquiler03 = new ModeloAlquiler("Pablo", "Zaragoza", 850.00,6);
		ModeloAlquiler alquiler04 = new ModeloAlquiler("Ypussef", "Zaragoza", 800.00,6);
		ModeloAlquiler alquiler05 = new ModeloAlquiler("Jaime", "Zaragoza", 550.00,6);
		ModeloAlquiler alquiler06 = new ModeloAlquiler("Vadim", "Zaragoza", 950.00,6);
		ModeloAlquiler alquiler07 = new ModeloAlquiler("Lauty", "BS AS", 1050.00,6);
		ModeloAlquiler alquiler08 = new ModeloAlquiler("Ignacio", "BS AS", 350.00,6);
		ModeloAlquiler alquiler09 = new ModeloAlquiler("Manu", "BS AS", 750.00,6);
		ModeloAlquiler alquiler10 = new ModeloAlquiler("Messi", "Miami", 850.00,6);
		
		
		//Creo Factor�a de EntityManager (gestor de base de datos)
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
		EntityManager conexion = null;
		EntityTransaction transaccion = null;
		try {
			//Inicio la conexi�n con la base de datos indicada en la Factor�a
			conexion = emf.createEntityManager();
			
			//Inicio transaccion
			transaccion = conexion.getTransaction();
			transaccion.begin();
			
			//Almaceno los objetos (persist)
			conexion.persist(alquiler01);
			conexion.persist(alquiler02);
			conexion.persist(alquiler03);
			conexion.persist(alquiler04);
			conexion.persist(alquiler05);
			conexion.persist(alquiler06);
			conexion.persist(alquiler07);
			conexion.persist(alquiler08);
			conexion.persist(alquiler09);
			conexion.persist(alquiler10);
			
			//Commit de la transacci�n (guardar operaciones)
			transaccion.commit();
			
			System.out.println("Se ha creado una base de datos Alquiler de pisos con");
			System.out.println("10 alquileres.");
		}
		catch (_RollbackException e) {
			//Rollback autom�tico al intentar insertar objetos con un id ya existente.
			System.err.println("Se ha producido un error en la insercion de los datos. "
					+ "La base de datos ya existe.");
		}
		catch (Exception e) {
			//Si se ha producido un error antes del commit, forzamos un rollback.
			if(transaccion!=null) {
				if (transaccion.isActive()) {
				transaccion.rollback();
				}
			}
			throw e;
		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		emf.close();
	}

}
