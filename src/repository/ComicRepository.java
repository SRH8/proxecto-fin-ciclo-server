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
 * Operaciones contra la base de datos para los c�mics
 * 
 * @author Sergio Fraga
 */
public class ComicRepository {

	/**
	 * Obtiene los c�mics de la base de datos
	 * 
	 * @return lista de c�mics
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
			System.out.println("Error al listar colleciones");
			e.printStackTrace();
		}
		
		return comicList;
	}	
	
	/**
	 * Obtiene la colecci�n de un c�mic
	 * 
	 * @param id identificadord de la colecci�n
	 * @param connection conexi�n a la base de datos
	 * @return colecci�n de c�mics
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
 * Obtiene el estado de un c�mic en particular
 * 
 * @param id identificador del estado
 * @param connection conexi�n a la base de datos
 * @return estado del c�mic
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
}
