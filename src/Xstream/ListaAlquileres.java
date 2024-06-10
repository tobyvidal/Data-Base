package Xstream;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import Modelo.ModeloAlquiler;
import Principal.Principal;
import entrada.Teclado;

public class ListaAlquileres {

	private List<ModeloAlquiler> listaAlquiler;

	public ListaAlquileres() {
		this.listaAlquiler = new ArrayList<ModeloAlquiler>();
	}

	public void add(ModeloAlquiler alquiler) {
		listaAlquiler.add(alquiler);
	}

	public List<ModeloAlquiler> getListaAlquileres() {
		return listaAlquiler;
	}

	public void setListaAlquileres(List<ModeloAlquiler> lista) {
		this.listaAlquiler = lista;
	}

	public static void escribirMenu() {
		System.out.println("----- Submenu Gestion de Informacion XML -----");
		System.out.println("a. Importar.");
		System.out.println("b. Exportar.");
		System.out.println("x. Volver al menu principal.");
		System.out.println("");
	}

	public static void main(String[] args) {
		String opcion = "";
		List<ModeloAlquiler> lista; 
		do {
			try {
				escribirMenu();	
				opcion = Teclado.leerCadena("Elija una opcion: ");
				switch(opcion) {
				case "a":
					lista = accesoXML.importar();
					if(lista.size() > 0) {
						System.out.println("Numero de Alquileres: " + lista.size());					
					}
					else {
						System.out.println("No se ha importado ningun alquiler a la base de datos.");
					}

					break;
				case "b":
					lista = accesoXML.exportar();
					if(lista.size() > 0) {
						System.out.println("Numero de Alquileres exportados: " + lista.size());
					}
					else {
						System.out.println("No se ha exportado ningun alquiler al fichero XML.");
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
			catch (FileNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}		
		}
		while(!opcion.equals("x"));



	}

}
