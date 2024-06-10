package Modelo;

public class ModeloComentario {
	private int ID;
	private int idAlquiler;
	private String comentario;
	
	public ModeloComentario(int iD, int idAlquiler, String comentario) {
		ID = iD;
		this.idAlquiler = idAlquiler;
		this.comentario = comentario;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(int idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public String toString() {
		return "Comentario [ID=" + ID + ", idAlquiler=" + idAlquiler + ", comentario=" + comentario + "]";
	}
	
	
	
	
}
