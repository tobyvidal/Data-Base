package Neodatis;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import Modelo.ModeloComentario;

public class CrearBDneodatis {

	public static void crearObjetos(ODB odb) {
		// Crear instancias para almacenar en BD
		ModeloComentario c1 = new ModeloComentario(1, 1, "Muy bueno");
		ModeloComentario c2 = new ModeloComentario(2, 2, "Muy bueno");
		ModeloComentario c3 = new ModeloComentario(3, 3, "Muy bueno");



		// Almacenamos objetos
		odb.store(c1);
		odb.store(c2);
		odb.store(c3);


	}

	public static void main(String[] args) {
		ODB odb = ODBFactory.open("data/neodatis.com");// Abrir BD

		//recuperamos todos los objetos
		Objects<ModeloComentario> comentario = odb.getObjects(ModeloComentario.class);
		if(comentario.size() == 0) {
			crearObjetos(odb);
			System.out.println("No hay objetos en la base de datos, insertando...");
			comentario = odb.getObjects(ModeloComentario.class);
		}
		System.out.println("");
		System.out.printf("%d Comentarios:", comentario.size());

		while (comentario.hasNext()) {
			ModeloComentario c = comentario.next();
			System.out.println(c.toString()); 
		}
		odb.close(); // Cerrar BDS
	}
}
