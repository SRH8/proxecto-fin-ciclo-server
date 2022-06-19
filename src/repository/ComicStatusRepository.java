package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Pool;
import model.entities.ComicStatus;

/**
 * Operaciones contra la base de datos para los estados de cómics
 * 
 * @author Sergio Fraga
 */
public class ComicStatusRepository {

	/**
	 * Obtiene los estados de los cómics
	 * 
	 * @return lista de estados
	 */
	public ArrayList<ComicStatus> list() {
		 ArrayList<ComicStatus> statusList = new ArrayList<>();
		 
		 try (Connection connection = Pool.getConection();){
			
			String query = "SELECT * FROM estados";
				
			PreparedStatement preparedStatement = connection.prepareStatement(query);
				
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				statusList.add(new ComicStatus(resultSet.getInt(1), resultSet.getString(2)));
			}
			 
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Error al listar estados");
			e.printStackTrace();
		}
		 
		 return statusList;
	}
}
