package Neodatis;

import java.util.ArrayList;
import java.util.List;

import Modelo.ModeloAlquiler;
import Modelo.ModeloComentario;
import ObjectDB.AccesoAlquileres;
import Principal.Principal;
import entrada.Teclado;

public class Comentario {
	public static void escribirMenu() {
		System.out.println("----- Submenu de comentarios -----");
		System.out.println("a. Insertar un comentario.");
		System.out.println("b. Eliminar un comentario.");
		System.out.println("c. Buscar comentario.");
		System.out.println("d. MODIFICACION 2, autoincremental.");
		System.out.println("x. Volver al menu principal.");
		System.out.println("");
	}

	public static void main(String[] args) {
		int ID, idAlquiler;
		String comentario, opcion;
		List<ModeloComentario> lista = new ArrayList<>();
		List<ModeloAlquiler> listaAlquileres = new ArrayList<>();
		ModeloComentario Mcomentario;
		ModeloAlquiler alquiler;

		do {
			escribirMenu();
			opcion = Teclado.leerCadena("Elija una opcion: ");
			switch(opcion) {
			case "a":
				ID = Teclado.leerEntero("ID del comentario? ");
				Mcomentario = AccesoComentarios.consultaComentarioPorID(ID);
				if(Mcomentario == null) {
					listaAlquileres = AccesoAlquileres.consultarAlquileres();
					if(listaAlquileres.isEmpty()) {
						System.out.println("No hay alquileres en la base de datos.");
					}
					else {
						for (ModeloAlquiler al : listaAlquileres) {
							System.out.println(al.toString());
						}
						if(listaAlquileres.size() == 1) {
							System.out.println("Se ha consultado un alquiler de la base de datos");
						}
						else if(listaAlquileres.size() > 1) {
							System.out.println("Se han consultado " + listaAlquileres.size() + 
									" alquileres de la base de datos");
						}
					}
					idAlquiler = Teclado.leerEntero("ID de alquiler? ");
					alquiler = AccesoAlquileres.consultarAlquilerPorID(idAlquiler);
					if(alquiler != null) {
						comentario = Teclado.leerCadena("Comentario? ");
						ModeloComentario c = new ModeloComentario(ID, idAlquiler, comentario);
						if(AccesoComentarios.insertarDepto(c)) {
							System.out.println("Se ha insertado un comentario sobre el alquiler con ID: " + idAlquiler + ".");
						}
						else {
							System.err.println("Error al insertar comentario.");
						}
					}
					else {
						System.out.println("No hay ningun alquiler con ese ID en la base de datos.");
					}
				}
				else {
					System.out.println("Ya hay un comentario con ese ID asociado en la base de datos.");
				}
				break;
			case "b":
				lista = AccesoComentarios.consultarComentarios();
				if(lista.isEmpty()) {
					System.out.println("No hay alquileres en la base de datos.");
				}
				else {
					for (ModeloComentario c : lista) {
						System.out.println(c.toString());
					}
					if(lista.size() == 1) {
						System.out.println("Se ha consultado un alquiler de la base de datos");
					}
					else if(lista.size() > 1) {
						System.out.println("Se han consultado " + lista.size() + 
								" alquileres de la base de datos");
					}
				}
				ID = Teclado.leerEntero("ID de comentario a eliminar? ");
				if(AccesoComentarios.eliminarComentario(ID)) {
					System.out.println("Se ha eliminado el comentario con ID: "+ ID + " de la base de datos.");
				}
				else {
					System.err.println("No hay ningun comentario con el ID: " + ID + " en la base de datos.");
				}

				break;
			case "c":
				listaAlquileres = AccesoAlquileres.consultarAlquileres();
				if(listaAlquileres.isEmpty()) {
					System.out.println("No hay alquileres en la base de datos.");
				}
				else {
					for (ModeloAlquiler al : listaAlquileres) {
						System.out.println(al.toString());
					}
					if(listaAlquileres.size() == 1) {
						System.out.println("Se ha consultado un alquiler de la base de datos");
					}
					else if(listaAlquileres.size() > 1) {
						System.out.println("Se han consultado " + listaAlquileres.size() + 
								" alquileres de la base de datos");
					}
				}
				idAlquiler = Teclado.leerEntero("ID del alquiler del que desee ver sus comentarios? ");
				alquiler = AccesoAlquileres.consultarAlquilerPorID(idAlquiler);
				if(alquiler != null) {
					lista = AccesoComentarios.consultaComentarioPorIDAlquiler(idAlquiler);
					if(!lista.isEmpty()) {
						System.out.println("Comentarios del alquiler: ");
						System.out.println(alquiler.toString());
						for(ModeloComentario c : lista) {
							System.out.println(c.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un comentario.");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado: " + lista.size() + " comentarios.");
						}
					}
					else {
						System.out.println("El alquiler: ");
						System.out.println(alquiler.toString());
						System.out.println("No tiene ningun comentario registrado.");
					}
				}
				else {
					System.out.println("No hay ningin alquiler con el ID: " + idAlquiler + " en la base de datos.");
				}
				break;		
			case "d":
				System.out.println("El ID del coemtario sera autoincremental.");
				ID = AccesoComentarios.idMaximo() + 1;
				idAlquiler = Teclado.leerEntero("ID de alquiler? ");
				alquiler = AccesoAlquileres.consultarAlquilerPorID(idAlquiler);
				if(alquiler != null) {
					comentario = Teclado.leerCadena("Comentario? ");
					ModeloComentario c = new ModeloComentario(ID, idAlquiler, comentario);
					if(AccesoComentarios.insertarDepto(c)) {
						System.out.println("Se ha insertado un comentario sobre el alquiler con ID: " + idAlquiler + ".");
					}
					else {
						System.err.println("Error al insertar comentario.");
					}
				}
				else {
					System.out.println("No hay ningun alquiler con ese ID en la base de datos.");
				}
				break;
			case "x":
				Principal.main(args);
				break;
			}
		}
		while(!opcion.equals("x"));

	}

}
