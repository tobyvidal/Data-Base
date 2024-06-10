package Neodatis;

import java.util.ArrayList;
import java.util.List;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import Modelo.ModeloComentario;

public class AccesoComentarios {

	public static boolean insertarDepto(ModeloComentario c) {
		ODB odb = null;
		try {
			odb = ODBFactory.open("neodatis.com");

			if (odb.store(c) != null) {
				odb.commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception appropriately (logging, etc.)
		} finally {
			if (odb != null && !odb.isClosed()) {
				odb.close();
			}
		}
		return false;
	}

	public static ModeloComentario consultaComentarioPorID(int ID) {

		ODB odb = ODBFactory.open("neodatis.com");

		IQuery query = new CriteriaQuery(ModeloComentario.class);

		Objects<ModeloComentario> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			ModeloComentario c = objects.next();
			if(c.getID() == ID) {
				return c;
			}
		}
		odb.close();
		return null;

	}

	public static List<ModeloComentario> consultaComentarioPorIDAlquiler(int ID) {
		List<ModeloComentario> lista = new ArrayList<>();;
		ODB odb = ODBFactory.open("neodatis.com");

		IQuery query = new CriteriaQuery(ModeloComentario.class);

		Objects<ModeloComentario> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			ModeloComentario c = objects.next();
			if(c.getIdAlquiler() == ID) {
				lista.add(c);
			}
		}
		odb.close();
		return lista;

	}

	public static List<ModeloComentario> consultaComentarioPorIDdeAlquiler(int ID) {
		List<ModeloComentario> lista = new ArrayList<>();
		ODB odb = ODBFactory.open("neodatis.com");

		IQuery query = new CriteriaQuery(ModeloComentario.class);

		Objects<ModeloComentario> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			ModeloComentario c = objects.next();
			if(c.getIdAlquiler() == ID) {
				lista.add(c);
			}
		}
		odb.close();
		return lista;

	}

	public static boolean eliminarComentario(int ID) {
		ODB odb = ODBFactory.open("neodatis.com");// Abrir BD

		//recuperamos todos los objetos
		Objects<ModeloComentario> c = odb.getObjects(ModeloComentario.class);
		if(c.size() == 0) {
			return false;
		}
		else {
			while(c.hasNext()) {
				ModeloComentario cm = c.next();
				if(cm.getID() == ID) {
					odb.delete(cm);
				}
			}
		}

		odb.close(); // Cerrar BDS
		return true;
	}

	public static boolean eliminarComentarioPorIDdeAlquiler(int ID) {
		ODB odb = ODBFactory.open("neodatis.com");// Abrir BD

		//recuperamos todos los objetos
		Objects<ModeloComentario> c = odb.getObjects(ModeloComentario.class);
		if(c.size() == 0) {
			return false;
		}
		else {
			while(c.hasNext()) {
				ModeloComentario cm = c.next();
				if(cm.getIdAlquiler() == ID) {
					odb.delete(cm);
				}
			}
		}

		odb.close(); // Cerrar BDS
		return true;
	}

	public static List <ModeloComentario> consultarComentarios() {
		List<ModeloComentario> lista = new ArrayList<>();
		ODB odb = ODBFactory.open("neodatis.com");

		IQuery query = new CriteriaQuery(ModeloComentario.class);

		Objects<ModeloComentario> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			ModeloComentario c = objects.next();
			lista.add(c);
		}

		odb.close();
		return lista;

	}
	
	public static int idMaximo() {
		int idMax = 0;
		ODB odb = ODBFactory.open("neodatis.com");

		IQuery query = new CriteriaQuery(ModeloComentario.class);

		Objects<ModeloComentario> objects = odb.getObjects(query);

		while (objects.hasNext()) {
			ModeloComentario c = objects.next();
			if(idMax < c.getID()) {
				idMax = c.getID();
			}
		}
		odb.close();
		return idMax;
		
	}


}
