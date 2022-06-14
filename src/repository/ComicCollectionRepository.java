package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controller.Pool;
import model.entities.ComicCollection;

/**
 * Operaciones contra la base de datos para las colecciones de cómics
 * 
 * @author Sergio Fraga
 */
public class ComicCollectionRepository {

	/**
	 * Obtiene las colecciones de cómics de la base de datos
	 * @return
	 */
	public ArrayList<ComicCollection> list() {
		ArrayList<ComicCollection> collectionList = new ArrayList<>();
		
		try (Connection connection = Pool.getConection();) {
			
			String query = "SELECT * FROM colecciones";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				collectionList.add(new ComicCollection(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBytes(4), resultSet.getString(5)));
			}
			
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Error al listar colleciones");
			e.printStackTrace();
		}
		
		return collectionList;
	}
	
	public int insertCollection(ComicCollection comicCollection) {
		int insertResult = 0;
		
		try (Connection connection = Pool.getConection();) {
			String command = "INSERT INTO colecciones(nombre, descripcion, imagen, anho_estreno) VALUES(?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setString(1, comicCollection.getName());
			preparedStatement.setString(2, comicCollection.getDescription());
			preparedStatement.setBytes(3, comicCollection.getImage());
			preparedStatement.setString(4, comicCollection.getReleaseYear());
			
			insertResult = preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (Exception e) {
			System.out.println("Error al insertar colección");
			e.printStackTrace();
		}		
		return insertResult;
	}
	
}
