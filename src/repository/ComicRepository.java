package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controller.Pool;
import model.entities.Comic;
import model.entities.ComicCollection;
import model.entities.ComicStatus;
import model.entities.CoverType;

/**
 * Operaciones contra la base de datos para los cómics
 * 
 * @author Sergio Fraga
 */
public class ComicRepository {

	/**
	 * Obtiene los cómics de la base de datos
	 * 
	 * @return lista de cómics
	 */
	public ArrayList<Comic> list() {
		ArrayList<Comic> comicList = new ArrayList<>();
		
		try (Connection connection = Pool.getConection();) {
			
			String query = "SELECT * FROM comics";
			
			CoverType coverType = null;
			
			ComicCollection comicCollection = null;
			
			ComicStatus comicStatus = null;
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				comicCollection = getComicCollection(resultSet.getInt(7), connection);
				comicStatus = getComicStatus(resultSet.getInt(8), connection);
				switch(resultSet.getString(5)) {
				case "dura":
					coverType = CoverType.DURA;
					break;
				case "blanda":
					coverType = CoverType.BLANDA;
					break;
					
				}
				comicList.add(new Comic(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), coverType, resultSet.getBytes(6), comicCollection, comicStatus));
			}
			
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Error al listar cómics");
			e.printStackTrace();
		}
		
		return comicList;
	}	
	
	/**
	 * Obtiene la colección de un cómic
	 * 
	 * @param id identificadord de la colección
	 * @param connection conexión a la base de datos
	 * @return colección de cómics
	 * @throws SQLException 
	 */
	private ComicCollection getComicCollection(int id, Connection connection) throws SQLException {
		ComicCollection comicCollection = null;
		
		String query = "SELECT * FROM colecciones WHERE id = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			comicCollection = new ComicCollection(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBytes(4), resultSet.getString(5));
		}
		return comicCollection;
	}
	
/**
 * Obtiene el estado de un cómic en particular
 * 
 * @param id identificador del estado
 * @param connection conexión a la base de datos
 * @return estado del cómic
 * @throws SQLException 
 */
	private ComicStatus getComicStatus(int id, Connection connection) throws SQLException {
		ComicStatus comicStatus = null;
		
		String query = "SELECT * FROM estados WHERE id = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()) {
			comicStatus = new ComicStatus(resultSet.getInt(1), resultSet.getString(2));
		}
		
		return comicStatus;
	}
	
	/**
	 * Elimina un cómic de la base de datos
	 * 
	 * @param comic comic
	 * @return resultado de la operación
	 */
	public int deleteComic(Comic comic) {
		int deleteResult = 0;
		
		try (Connection connection = Pool.getConection();) {
			String command = "DELETE FROM comics WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setInt(1, comic.getId());
			
			deleteResult = preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (Exception e) {
			System.out.println("Error al eliminar cómic");
			e.printStackTrace();
		}		
		return deleteResult;
		
	}
	
	/**
	 * Busca un cómic por su nombre
	 * 
	 * @param name nombre del cómic
	 * @return lista de cómics
	 */
	public ArrayList<Comic> searchComicByName(String name) {
		ArrayList<Comic> comicList = new ArrayList<>();
		
		try (Connection connection = Pool.getConection();) {
			
			String query = "SELECT * FROM comics WHERE titulo = ?";
			
			CoverType coverType = null;
			
			ComicCollection comicCollection = null;
			
			ComicStatus comicStatus = null;
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				comicCollection = getComicCollection(resultSet.getInt(7), connection);
				comicStatus = getComicStatus(resultSet.getInt(8), connection);
				switch(resultSet.getString(5)) {
				case "dura":
					coverType = CoverType.DURA;
					break;
				case "blanda":
					coverType = CoverType.BLANDA;
					break;
					
				}
				comicList.add(new Comic(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), coverType, resultSet.getBytes(6), comicCollection, comicStatus));
			}
			
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Error al buscar cómic");
			e.printStackTrace();
		}
		
		return comicList;
	}
	
	/**
	 * Busca los cómics de una colección
	 * 
	 * @param collectionName nombre de la colección
	 * @return lista de cómics
	 */
	public ArrayList<Comic> searchComicByCollection(String collectionName) {
		ArrayList<Comic> comicList = new ArrayList<>();
		
		try (Connection connection = Pool.getConection();) {
			
			String query = "SELECT comics.id, comics.titulo, comics.descripcion, comics.fecha_adquisicion, comics.tapa, comics.portada, comics.id_coleccion, comics.id_estado FROM libreria.comics INNER JOIN libreria.colecciones"
					+ " ON comics.id_coleccion = colecciones.id"
					+ " WHERE colecciones.nombre = ?";
			
			CoverType coverType = null;
			
			ComicCollection comicCollection = null;
			
			ComicStatus comicStatus = null;
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, collectionName);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				comicCollection = getComicCollection(resultSet.getInt(7), connection);
				comicStatus = getComicStatus(resultSet.getInt(8), connection);
				switch(resultSet.getString(5)) {
				case "dura":
					coverType = CoverType.DURA;
					break;
				case "blanda":
					coverType = CoverType.BLANDA;
					break;					
				}
				
				comicList.add(new Comic(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), coverType, resultSet.getBytes(6), comicCollection, comicStatus));
			}
			
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Error al buscar cómic");
			e.printStackTrace();
		}
		
		
		return comicList;
	}
	
	
	/**
	 * Inserta un cómic en la base de datos
	 * 
	 * @param title título del cómic
	 * @param description descripción del cómic
	 * @param releaseDate fecha de estreno del cómic
	 * @param coverType tipo de tapa del cómic
	 * @param image imagen de portada
	 * @param collection colección a la que pertenece
	 * @param status estado del cómic
	 * @return resultado de la operación
	 */
	public int insert(String title, String description, String releaseDate, String coverType, byte[] image, String collection, String status) {
		int resultInsert = 0;
		CoverType coverTypeInsert = null;
		
		try (Connection connection = Pool.getConection();){
			String command = "INSERT INTO comics(titulo, descripcion, fecha_adquisicion, tapa, portada, id_coleccion, id_estado) VALUES(?,?,?,?,?,?,?)";
			
			ComicCollection comicCollection = getCollectionByName(connection, collection);
			ComicStatus comicStatus = getComicStatusByName(connection, status);
			
			switch(coverType.toLowerCase().trim()) {
			case "dura":
				coverTypeInsert = CoverType.DURA;
				break;
			case "blanda":
				coverTypeInsert = CoverType.BLANDA;
				break;					
			}
			
			Comic comic = new Comic(title, description, releaseDate, coverTypeInsert, image, comicCollection, comicStatus);
			
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setString(1, comic.getTitle());
			preparedStatement.setString(2, comic.getDescription());
			preparedStatement.setString(3, comic.getReleaseDate());
			preparedStatement.setString(4, comic.getCover().toString());
			preparedStatement.setBytes(5, image);
			preparedStatement.setInt(6, comicCollection.getId());
			preparedStatement.setInt(7, comicStatus.getId());
			
			resultInsert = preparedStatement.executeUpdate();
			System.out.println("resultado insertar en repo: " + resultInsert);
			connection.commit();
			
		} catch (SQLException e) {
			System.out.println("Error al insertar cómic");
			e.printStackTrace();
		}		
		return resultInsert;
	}
	
	/**
	 * Obtiene una colección por nombre
	 * 
	 * @param connection conexión a la base de datos
	 * @param name nombre
	 * @return colección de cómics
	 * @throws SQLException
	 */
	private ComicCollection getCollectionByName(Connection connection, String name) throws SQLException {
		ComicCollection comicCollection = null;
		
		String query = "SELECT * FROM colecciones WHERE nombre = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, name);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			comicCollection = new ComicCollection(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBytes(4), resultSet.getString(5));
		}
		return comicCollection;
	}
	
	/**
	 * Obtiene el estado de un cómic por nombre
	 * 
	 * @param connection conexión a la base de taos
	 * @param statusName descripción del estado
	 * @return estado del cómic
	 * @throws SQLException
	 */
	private ComicStatus getComicStatusByName(Connection connection, String statusName) throws SQLException {
		ComicStatus comicStatus = null;
		
		String query = "SELECT * FROM estados WHERE descripcion = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, statusName);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next()) {
			comicStatus = new ComicStatus(resultSet.getInt(1), resultSet.getString(2));
		}
		
		return comicStatus;
	}

	/**
	 * Edita un cómic
	 * 
	 * @param comicEdit comic a editar
	 * @param descripcionEdit descripción del cómic
	 * @param tapaEdit tipo de tapa
	 * @param imagenEdit imagen de portada
	 * @param estadoEdit estado del cómic
	 * @return resultado de la operación
	 */
	public int edit(Comic comicEdit, String descripcionEdit, String tapaEdit, byte[] imagenEdit, String estadoEdit) {
		int resultEdit = 0;
		CoverType coverTypeEdit = null;
		
		String command = "UPDATE comics SET descripcion = ?, tapa = ?, portada = ?, id_estado = ? WHERE id =?";
		
		try(Connection connection = Pool.getConection()) {
			ComicStatus comicStatus = getComicStatusByName(connection, estadoEdit);
			
			switch(tapaEdit.toLowerCase().trim()) {
			case "dura":
				coverTypeEdit = CoverType.DURA;
				break;
			case "blanda":
				coverTypeEdit = CoverType.BLANDA;
				break;					
			}
			
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setString(1, descripcionEdit);
			preparedStatement.setString(2, coverTypeEdit.toString());
			preparedStatement.setBytes(3, imagenEdit);
			preparedStatement.setInt(4, comicStatus.getId());
			preparedStatement.setInt(5, comicEdit.getId());
			
			resultEdit = preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
			System.out.println("Error al editar cómic");
			e.printStackTrace();
		}
		return resultEdit;
	}
}
