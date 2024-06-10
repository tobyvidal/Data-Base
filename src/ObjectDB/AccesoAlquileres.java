package ObjectDB;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import Modelo.ModeloAlquiler;


public class AccesoAlquileres {

	public static boolean insertar(ModeloAlquiler alquiler) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
		EntityManager conexion = null;
		EntityTransaction transaccion = null;

		try {
			//Inicio la conexión con la base de datos indicada en la Factoría
			conexion = emf.createEntityManager();

			//Inicio transacción
			transaccion = conexion.getTransaction();
			transaccion.begin();

			//Almaceno los objetos (persist)
			conexion.persist(alquiler);

			//Commit de la transacción (guardar operaciones)
			transaccion.commit();

			// Si no hubo excepciones, la inserción se realizó correctamente
			return true;
		} catch (Exception e) {
			// Si hubo alguna excepción, se imprime el mensaje de error y se hace rollback de la transacción
			if (transaccion != null && transaccion.isActive()) {
				transaccion.rollback();
			}
			e.printStackTrace();
			// Indicamos que la inserción no se realizó correctamente
			return false;
		} finally {
			// Cerramos la conexión al EntityManagerFactory
			if (conexion != null) {
				conexion.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
	}

	public static boolean actualizarAlquiler(int ID, ModeloAlquiler alquilerAct) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
		EntityManager conexion = null;
		try {
			conexion = emf.createEntityManager();
			conexion.getTransaction().begin();

			ModeloAlquiler al = conexion.find(ModeloAlquiler.class, ID);

			if (al != null) {
				al.setNombreInquilino(alquilerAct.getNombreInquilino());
				al.setDireccionPiso(alquilerAct.getDireccionPiso());
				al.setImporteAlquiler(alquilerAct.getImporteAlquiler());
				al.setDuracionContrato(alquilerAct.getDuracionContrato());

				conexion.getTransaction().commit();
				return true;
			}
			else {
				return false;
			}
		} catch (PersistenceException e) {
			// Manejar excepción de persistencia
			e.printStackTrace();
			return false;
		} finally {
			if (conexion != null) {
				conexion.close();
			}
			emf.close();
		}

	}

	public static boolean eliminarAlquiler(int ID) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
		EntityManager conexion = null;
		try {
			conexion = emf.createEntityManager();
			conexion.getTransaction().begin();

			ModeloAlquiler al = conexion.find(ModeloAlquiler.class, ID);

			if (al != null) {
				conexion.remove(al);
				conexion.getTransaction().commit();
				return true;
			} 
			else {
				return false;
			}
		} catch (PersistenceException e) {
			// Manejar excepción de persistencia
			e.printStackTrace();
			return false;
		} finally {
			if (conexion != null) {
				conexion.close();
			}
			emf.close();
		}
	}

	public static ModeloAlquiler consultarAlquilerPorID(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
		EntityManager conexion = null;
		try {
			conexion = emf.createEntityManager();
			ModeloAlquiler alq = conexion.find(ModeloAlquiler.class, id);
			return alq;
		}
		catch (PersistenceException e) {

		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return null;
	}

	public static List<ModeloAlquiler> consultarAlquilerPorNombreInquilino(String nombre) {
		EntityManagerFactory fabricaConexiones = null;
		EntityManager conexion = null;
		try {
			fabricaConexiones = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
			conexion = fabricaConexiones.createEntityManager();

			String sentenciaJPQL = "SELECT p FROM ModeloAlquiler p WHERE p.NombreInquilino = :nombre";
			TypedQuery<ModeloAlquiler> consulta = conexion.createQuery(sentenciaJPQL, ModeloAlquiler.class);
			consulta.setParameter("nombre", nombre);

			List<ModeloAlquiler> lista = consulta.getResultList();

			return lista;

		} catch (Exception e) {
			throw e;
		} finally {
			if (conexion != null) {
				conexion.close();
			}
			if (fabricaConexiones != null) {
				fabricaConexiones.close();
			}
		}
	}

	public static List<ModeloAlquiler> consultarAlquileres(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
		EntityManager conexion = null;
		try {
			conexion = emf.createEntityManager();
			TypedQuery<ModeloAlquiler> consulta = conexion.createQuery("SELECT e FROM ModeloAlquiler e", 
					ModeloAlquiler.class);
			List<ModeloAlquiler> alquileres = consulta.getResultList();
			return alquileres;
		}
		catch (PersistenceException e) {

		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return null;
	}
	
	public static List<ModeloAlquiler> Modificacion1(String letra){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("data/AlquileresPisos.odb");
		EntityManager conexion = null;
		List<ModeloAlquiler> lista = consultarAlquileres();
		List<ModeloAlquiler> listaNueva = new ArrayList<>();
		try {
			for(ModeloAlquiler al : lista) {
				if(al.getNombreInquilino().contains(letra) || al.getDireccionPiso().contains(letra)) {
					listaNueva.add(al);
				}
			}
			return listaNueva;
		}
		catch (PersistenceException e) {

		}
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return listaNueva;
	}



}
