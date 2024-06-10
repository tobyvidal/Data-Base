package Principal;

import Neodatis.Comentario;
import ObjectDB.Alquiler;
import Xstream.ListaAlquileres;
import eXistDB.Servicios;
import entrada.Teclado;

public class Principal {

	public static void escribirMenu() {
		System.out.println("----- Menu Principal -----");
		System.out.println("1. Registro de alquileres (con ObjectDB)");
		System.out.println("2. Registro de comentarios (con Neodatis)");
		System.out.println("3. Registro de servicios asociados (con eXistDB).");
		System.out.println("4. Gestion de informacion XML (con Xtream).");
		System.out.println("5. Salir.");
		System.out.println("");
	}

	public static void main(String[] args) {
		int opcion;

		do {
			escribirMenu();
			opcion = Teclado.leerEntero("Elija una opcion: (1-5)");
			switch(opcion) {
			case 1:
				Alquiler.main(args);
				break;
			case 2:
				Comentario.main(args);
				break;
			case 3:
				Servicios.main(args);
				break;
			case 4:
				ListaAlquileres.main(args);
				break;
			case 5:
				//salir del programa.
				break;			
			default:
				System.out.println("Opcion no valida.");
				break;
			}

		}
		while(opcion != 5);
	}

}
