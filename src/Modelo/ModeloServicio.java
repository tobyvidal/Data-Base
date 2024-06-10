package Modelo;

public class ModeloServicio {
	private int id;
	private String tipoServicio;
	private double costoMensual;
	private int idAlquiler;

	public ModeloServicio(int id, String tipoServicio, double costoMensual, int idAlquiler) {
		this.id = id;
		this.tipoServicio = tipoServicio;
		this.costoMensual = costoMensual;
		this.idAlquiler = idAlquiler;
	}

	public ModeloServicio() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public double getCostoMensual() {
		return costoMensual;
	}

	public void setCostoMensual(double costoMensual) {
		this.costoMensual = costoMensual;
	}

	public int getIdAlquiler() {
		return idAlquiler;
	}

	public void setIdAlquiler(int idAlquiler) {
		this.idAlquiler = idAlquiler;
	}

	@Override
	public String toString() {
		return "ModeloServicio [id=" + id + ", tipoServicio=" + tipoServicio + ", costoMensual=" + costoMensual
				+ ", idAlquiler=" + idAlquiler + "]";
	}





}
