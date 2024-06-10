package ObjectDB;

import java.util.ArrayList;
import java.util.List;

import Modelo.ModeloAlquiler;
import Modelo.ModeloComentario;
import Modelo.ModeloServicio;
import Neodatis.AccesoComentarios;
import Principal.Principal;
import eXistDB.AccesoServicios;
import entrada.Teclado;

public class Alquiler {

	public static void escribirMenu() {
		System.out.println("----- Submenu de alquileres -----");
		System.out.println("a. Insertar un alquiler.");
		System.out.println("b. Actualizar alquiler.");
		System.out.println("c. Eliminar alquiler(INCLUYE LA MODIFICACION 3).");
		System.out.println("d. Listar alquileres.");
		System.out.println("e. Buscar alquiler por nombre de inquilino.");
		System.out.println("f. MODIFICACION 1, mostrar alquileres que contengan letra.");
		System.out.println("x. Volver al menu principal.");
		System.out.println("");
	}

	public static void main(String[] args) {
		int duracionContrato, ID;
		String nombreInquilino, direccionPiso, opcion, eleccion, valor;
		double importeAlquiler;
		List<ModeloAlquiler> lista = new ArrayList<>();
		ModeloAlquiler alquiler;
		List<ModeloComentario> listaComentarios = new ArrayList<>();
		List<ModeloServicio> listaServicios = new ArrayList<>();
		List<String> listaString = new ArrayList<>();
		do {
			escribirMenu();
			opcion = Teclado.leerCadena("Elija una opcion: ");
			try {
				switch(opcion) {
				case "a":
					nombreInquilino = Teclado.leerCadena("Nombre del inquilino? ");
					direccionPiso = Teclado.leerCadena("Direccion del piso? ");
					importeAlquiler = Teclado.leerReal("Importe del alquiler? ");
					duracionContrato = Teclado.leerEntero("Duracion del contrato? ");
					alquiler = new ModeloAlquiler(nombreInquilino, direccionPiso, importeAlquiler, duracionContrato);
					if(AccesoAlquileres.insertar(alquiler)) {
						System.out.println("Se ha insertado correctamente un departamento en la base de datos.");
					}
					else {
						System.err.println("Error al insertar departamento en la base de datos.");
					}
					break;
				case "b":
					lista = AccesoAlquileres.consultarAlquileres();
					if(lista.isEmpty()) {
						System.out.println("No hay alquileres en la base de datos.");
					}
					else {
						for (ModeloAlquiler al : lista) {
							System.out.println(al.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un alquiler de la base de datos");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado " + lista.size() + 
									" alquileres de la base de datos");
						}
					}
					ID = Teclado.leerEntero("ID de alquiler a actualizar? ");
					alquiler = AccesoAlquileres.consultarAlquilerPorID(ID);
					if(alquiler != null) {
						nombreInquilino = Teclado.leerCadena("Nuevo nombre del inquilino? ");
						direccionPiso = Teclado.leerCadena("Nueva direccion del alquiler? ");
						importeAlquiler = Teclado.leerReal("Nuevo importe del alquiler? ");
						duracionContrato = Teclado.leerEntero("Nueva duracion del alquiler? ");
						ModeloAlquiler alq = new ModeloAlquiler(nombreInquilino, direccionPiso, importeAlquiler, duracionContrato);
						if(AccesoAlquileres.actualizarAlquiler(ID, alq)) {
							System.out.println("Se ha actualizado un alquiler en la base de datos.");
						}
						else {
							System.err.println("Error al actualizar alquiler.");
						}
					}
					else {
						System.out.println("No hay ningun alquiler con ese ID en la base de datos.");
					}
					break;
				case "c":
					lista = AccesoAlquileres.consultarAlquileres();
					if(lista.isEmpty()) {
						System.out.println("No hay alquileres en la base de datos.");
					}
					else {
						for (ModeloAlquiler al : lista) {
							System.out.println(al.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un alquiler de la base de datos");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado " + lista.size() + 
									" alquileres de la base de datos");
						}
					}
					ID = Teclado.leerEntero("ID de alquiler a eliminar? ");
					alquiler = AccesoAlquileres.consultarAlquilerPorID(ID);
					if(alquiler != null) {
						listaComentarios = AccesoComentarios.consultaComentarioPorIDdeAlquiler(ID);
						listaString = AccesoServicios.consultarServiciosPorIdAlquiler(ID);
						if(listaComentarios.isEmpty() && listaString.isEmpty()) {
							if(AccesoAlquileres.eliminarAlquiler(ID)) {
								System.out.println("Se ha eliminado un alquiler en la base de datos.");
							}
							else {
								System.err.println("Error al eliminar alquiler.");
							}
						}
						else {
							if(listaComentarios.size() == 1 && listaString.size() == 1) {
								System.out.println("Ese alquiler tiene " + listaComentarios.size() + " comentario asociado.");
								System.out.println("Ese alquiler tiene " + listaString.size() + "servicio asociado.");
							}
							else if(listaComentarios.size() > 1 && listaString.size() > 1) {
								System.out.println("Ese alquiler tiene " + listaComentarios.size() + " comentarios asociados.");
								System.out.println("Ese alquiler tiene " + listaString.size() + "servicios asociados.");
							}
							eleccion = Teclado.leerCadena("Desea borrar los comentarios/servicios asociado y el alquiler? (Si/No)");
							if(eleccion.equalsIgnoreCase("Si")) {
								if(AccesoComentarios.eliminarComentarioPorIDdeAlquiler(ID)) {
									System.out.println("Se han eliminado los comentarios asociados al alquiler.");
									if(AccesoServicios.eliminarServicioPorIdAlquiler(ID)) {
										System.out.println("Se han eliminado los servicios asociados al alquiler.");
										if(AccesoAlquileres.eliminarAlquiler(ID)) {
											System.out.println("Se ha eliminado un alquiler en la base de datos.");
										}
										else {
											System.err.println("Error al eliminar alquiler.");
										}
									}
									else {
										System.out.println("Error al eliminar servicios.");
									}

								}
								else {
									System.err.println("Error al eliminar comentarios.");
								}
							}
							else {
								System.out.println("Operacion cancelada.");
							}
						}
					}
					else {
						System.out.println("No hay ningun alquiler con ese ID en la base de datos.");
					}
					break;
				case "d":
					lista = AccesoAlquileres.consultarAlquileres();
					if(lista.isEmpty()) {
						System.out.println("No hay alquileres en la base de datos.");
					}
					else {
						for (ModeloAlquiler al : lista) {
							System.out.println(al.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un alquiler de la base de datos");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado " + lista.size() + 
									" alquileres de la base de datos");
						}
					}
					break;
				case "e":
					nombreInquilino = Teclado.leerCadena("Nombre del inquilino del alquiler a buscar? ");
					lista = AccesoAlquileres.consultarAlquilerPorNombreInquilino(nombreInquilino);
					if(!lista.isEmpty()) {
						for (ModeloAlquiler al : lista) {
							System.out.println(al.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un alquiler de la base de datos");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado " + lista.size() + 
									" alquileres de la base de datos");
						}
					}
					else {
						System.out.println("No hay ningun alquiler a nombre del inquilino " + nombreInquilino + " en la bd.");
					}
					break;

				case "f":
					valor = Teclado.leerCadena("Inserta valor a buscar: ");
					lista = AccesoAlquileres.Modificacion1(valor);
					if(!lista.isEmpty()) {
						for (ModeloAlquiler al : lista) {
							System.out.println(al.toString());
						}
						if(lista.size() == 1) {
							System.out.println("Se ha consultado un alquiler de la base de datos");
						}
						else if(lista.size() > 1) {
							System.out.println("Se han consultado " + lista.size() + 
									" alquileres de la base de datos");
						}
					}
					else {
						System.out.println("No hay ningun alquiler que contenga el valor: " + valor + " en la bd.");
					}
					break;
				case "x":
					Principal.main(args);
					break;

				default:
					System.out.println("Dato introducido no valido.");
					break;

				}
			}
			catch (Exception i) {
				System.out.println("Error: " + i.getMessage());
			}
		}
		while(!opcion.equals("x"));


	}

}
