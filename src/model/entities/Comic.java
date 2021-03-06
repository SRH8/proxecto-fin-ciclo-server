package model.entities;

import java.io.Serializable;

/**
 * Modelo de los comics de la librer?a
 * 
 * @author Sergio Fraga
 */
public class Comic implements Serializable{
	
	/**
	 * Identificador ?nico de la clase
	 */
	private static final long serialVersionUID = 6529685098267757691L;

	/**
	 * Identificador del c?mic
	 */
	private int id;
	
	/**
	 * T?tulo del c?mic
	 */
	private String title;
	
	/**
	 * Descripci?n del c?mic
	 */
	private String description;
	
	/**
	 * Fecha de estreno del c?mic
	 */
	private String releaseDate;
	
	/**
	 * Tipo de tapa del c?mic
	 */
	private CoverType cover;
	
	/**
	 * Imagen de portada del c?mic
	 */
	private byte[] coverImage;
	
	/**
	 * Colecci?n a la que pertenece el c?mic
	 */
	private ComicCollection collection;
	
	/**
	 * Estado del c?mic
	 */
	private ComicStatus status;
	
	/**
	 * Constructor para crear un objeto c?mic vac?o
	 */
	public Comic() {}

	/**
	 * Constructor para crear un objeto c?mic
	 * 
	 * @param id identificador del c?mic
	 * @param title t?tulo del c?mic
	 * @param description descripci?n del c?mic
	 * @param releaseDate fecha de estreno del c?mic
	 * @param cover tipo de tapa del c?mic
	 * @param coverImage imagen de portada del c?mic
	 * @param collection colecci?n a la que pertenece el c?mic
	 * @param status estado en el que se encuentra el c?mic
	 */
	public Comic(int id, String title, String description, String releaseDate, CoverType cover, byte[] coverImage,
			ComicCollection collection, ComicStatus status) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.cover = cover;
		this.coverImage = coverImage;
		this.collection = collection;
		this.status = status;
	}
	
	
	
	/**
	 * Constructor para crear un objeto c?mic
	 * 
	 * @param title t?tulo del c?mic
	 * @param description descripci?n del c?mic
	 * @param releaseDate fecha de estreno del c?mic
	 * @param cover tipo de tapa del c?mic
	 * @param coverImage imagen de portada del c?mic
	 * @param collection colecci?n a la que pertenece el c?mic
	 * @param status estado en el que se encuentra el c?mic
	 */
	public Comic(String title, String description, String releaseDate, CoverType cover, byte[] coverImage,
			ComicCollection collection, ComicStatus status) {
		this.title = title;
		this.description = description;
		this.releaseDate = releaseDate;
		this.cover = cover;
		this.coverImage = coverImage;
		this.collection = collection;
		this.status = status;
	}

	/**
	 * Obtiene el id del c?mic
	 * 
	 * @return id del c?mic
	 */
	public int getId() {
		return id;
	}

	/**
	 * Define el identificador del c?mic
	 * 
	 * @param id identificador del c?mic
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el t?tulo del c?mic
	 * 
	 * @return t?tulo del c?mic
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Define el t?tulo del c?mic
	 * 
	 * @param title t?tulo del c?mic
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Obtiene la descripci?n del c?mic
	 * 
	 * @return descripci?n del c?mic
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Define la descripci?n del c?mic
	 * 
	 * @param description descripci?n del c?mic
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Obtiene la fecha de estreno del c?mic
	 * 
	 * @return fecha de estreno del c?mic
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Define la fecha de estreno del c?mic
	 * 
	 * @param releaseDate
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * Obtiene el tipo de portada del c?mic
	 * 
	 * @return tipo de portada del c?mic
	 */
	public CoverType getCover() {
		return cover;
	}

	/**
	 * Define el tipo de la tapa del c?mic
	 * 
	 * @param cover tipo de la tapa
	 */
	public void setCover(CoverType cover) {
		this.cover = cover;
	}

	/**
	 * Obtiene la imagen de la portada del c?mic
	 * 
	 * @return imagen de la portada
	 */
	public byte[] getCoverImage() {
		return coverImage;
	}

	/**
	 * Define la imagen de la portada del c?mic
	 * 
	 * @param coverImage imagen
	 */
	public void setCoverImage(byte[] coverImage) {
		this.coverImage = coverImage;
	}

	/**
	 * Obtiene la colecci?n a la que pertenece el c?mic
	 * 
	 * @return colecci?n a la que pertenece el c?mic
	 */
	public ComicCollection getCollection() {
		return collection;
	}

	/**
	 * Define la colecci?n a la que pertenece el c?mic
	 * 
	 * @param collection colecci?n
	 */
	public void setCollection(ComicCollection collection) {
		this.collection = collection;
	}

	/**
	 * Obtiene el estado en el que se encuentra el c?mic
	 * 
	 * @return estado en el que se encuentra el c?mic
	 */
	public ComicStatus getStatus() {
		return status;
	}

	/**
	 * Define el estado en el que se encuentra el c?mic
	 * 
	 * @param status estado del c?mic
	 */
	public void setStatus(ComicStatus status) {
		this.status = status;
	}
	
}