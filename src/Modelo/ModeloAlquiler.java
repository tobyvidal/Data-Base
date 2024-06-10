package Modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ModeloAlquiler {
	//GeneratedValue -> ID autoincremental
	//Id -> Indico que es la clave primaria
	@Id @GeneratedValue
	private int ID;
	private String NombreInquilino;
	private String DireccionPiso;
	private double ImporteAlquiler;
	private int DuracionContrato;
	
	public ModeloAlquiler(String nombreInquilino, String direccionPiso, double importeAlquiler, int duracionContrato) {
		NombreInquilino = nombreInquilino;
		DireccionPiso = direccionPiso;
		ImporteAlquiler = importeAlquiler;
		DuracionContrato = duracionContrato;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNombreInquilino() {
		return NombreInquilino;
	}

	public void setNombreInquilino(String nombreInquilino) {
		NombreInquilino = nombreInquilino;
	}

	public String getDireccionPiso() {
		return DireccionPiso;
	}

	public void setDireccionPiso(String direccionPiso) {
		DireccionPiso = direccionPiso;
	}

	public double getImporteAlquiler() {
		return ImporteAlquiler;
	}

	public void setImporteAlquiler(double importeAlquiler) {
		ImporteAlquiler = importeAlquiler;
	}

	public int getDuracionContrato() {
		return DuracionContrato;
	}

	public void setDuracionContrato(int duracionContrato) {
		DuracionContrato = duracionContrato;
	}

	@Override
	public String toString() {
		return "Alquiler [ID=" + ID + ", NombreInquilino=" + NombreInquilino + ", DireccionPiso=" + DireccionPiso
				+ ", ImporteAlquiler=" + ImporteAlquiler + ", DuracionContrato=" + DuracionContrato + "]";
	}
	
	
	
	
	
	

}
