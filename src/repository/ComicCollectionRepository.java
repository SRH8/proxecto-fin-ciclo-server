package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import controller.Pool;
import model.entities.ComicCollection;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Operaciones contra la base de datos para las colecciones de cómics
 * 
 * @author Sergio Fraga
 */
public class ComicCollectionRepository {

	/**
	 * Obtiene las colecciones de cómics de la base de datos
	 * 
	 * @return lista de colecciones
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
	
	/**
	 * Inserta una colección en la base de datos
	 * 
	 * @param comicCollection colección
	 * @return resultado de la operación
	 */
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

	/**
	 * Elimina una colección de la base de datos
	 * 
	 * @param comicCollection colección
	 * @return resultado de la operación
	 */
	public int deleteCollection(ComicCollection comicCollection) {
		int deleteResult = 0;
		
		try (Connection connection = Pool.getConection();) {
			String command = "DELETE FROM colecciones WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setInt(1, comicCollection.getId());
			
			deleteResult = preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (Exception e) {
			System.out.println("Error al eliminar colección");
			e.printStackTrace();
		}		
		return deleteResult;
	}

	/**
	 * Modifica una colección de la base de datos
	 * 
	 * @param comicCollection colección
	 * @return resultado de la operación
	 */
	public int editCollection(ComicCollection comicCollection) {
		int updateResult = 0;
		
		try (Connection connection = Pool.getConection();) {
			String command = "UPDATE colecciones SET nombre = ?, descripcion = ?, imagen = ? WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(command);
			preparedStatement.setString(1, comicCollection.getName());
			preparedStatement.setString(2, comicCollection.getDescription());
			preparedStatement.setBytes(3, comicCollection.getImage());
			preparedStatement.setInt(4, comicCollection.getId());
			
			updateResult = preparedStatement.executeUpdate();
			
			connection.commit();
			
		} catch (Exception e) {
			System.out.println("Error al actualizar colección");
			e.printStackTrace();
		}		
		
		return updateResult;
	}
}
