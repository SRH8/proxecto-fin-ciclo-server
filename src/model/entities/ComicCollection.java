package model.entities;

import java.io.Serializable;

/**
 * Modelo para las colecciones de comics
 * 
 * @author Sergio Fraga
 */
public class ComicCollection implements Serializable{
	
	/**
	 * Identificador de la colecci�n
	 */
	private int id;
	
	/**
	 * Nombre del a colecci�n
	 */
	private String name;
	
	/**
	 * Descripci�n de la colecci�n
	 */
	private String description;
	
	/**
	 * Imagen de la colecci�n
	 */
	private byte[] image;
	
	/**
	 * Constructor para crear un objeto colecci�n vac�o
	 */
	public ComicCollection() {}

	/**
	 * Constructor para crear un objeto colecci�n
	 * 
	 * @param id identificador de la colecci�n
	 * @param name nombre de la colecci�n
	 * @param description descripci�n de la colecci�n
	 * @param image imagen de la colecci�n
	 */
	public ComicCollection(int id, String name, String description, byte[] image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
	}

	/**
	 * Obtiene el id de la colecci�n
	 * 
	 * @return id de la colecci�n
	 */
	public int getId() {
		return id;
	}

	/**
	 * Define el id de la colecci�n
	 * 
	 * @param id id de la colecci�n
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de la colecci�n
	 * 
	 * @return nombre de la colecci�n
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define el nombre de la colecci�n
	 * 
	 * @param name nombre de la colecci�n
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene la descripci�n de la colecci�n
	 * 
	 * @return descripci�n de la colecci�n
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Define la descripci�n de la colecci�n
	 * 
	 * @param description descripci�n de la colecci�n
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Obtiene la imagen de la colecci�n
	 * 
	 * @return imagen de la colecci�n
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Define la imagen de la colecci�n
	 * 
	 * @param image imagen de la colecci�n
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
}