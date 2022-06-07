package model.entities;

/**
 * Modelo para el estado del comic
 * 
 * @author Sergio Fraga
 */
public class ComicStatus {
	
	/**
	 * Identificador del estado
	 */
	private int id;
	
	/**
	 * Descripci�n del estado
	 */
	private String description;
	
	/**
	 * Constructor para crear un objeto estado vac�o
	 */
	public ComicStatus() {}

	/**
	 * Constructor para crear un objeto estado
	 * 
	 * @param id identificador del estado
	 * @param description descripci�n del estado
	 */
	public ComicStatus(int id, String description) {
		this.id = id;
		this.description = description;
	}

	/**
	 * Obtiene el id del estado
	 * 
	 * @return identificador del estado
	 */
	public int getId() {
		return id;
	}

	/**
	 * Define el id del estado
	 * 
	 * @param id identificador del estado
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene la descripci�n del estado
	 * 
	 * @return descripci�n del estado
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Define la descripci�n del estado
	 * 
	 * @param description descripci�n del estado
	 */
	public void setDescription(String description) {
		this.description = description;
	}	
	
}