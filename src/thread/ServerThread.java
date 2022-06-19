package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import controller.Pool;
import model.entities.Comic;
import model.entities.ComicCollection;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import repository.ComicCollectionRepository;
import repository.ComicRepository;

/**
 * Hilo del servidor
 * 
 * @author Sergio Fraga
 */
public class ServerThread extends Thread{
	
	private Socket clientSocket;
	private static int port = 8080;
	
	public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            outputStream.writeUTF("Se ha conectado el cliente de forma correcta");

        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	
	public static void main(String[] args) {
		ComicCollectionRepository comicCollectionRepository = new ComicCollectionRepository();
		ComicRepository comicRepository = new ComicRepository();
		
		try (ServerSocket skServidor = new ServerSocket(port)) {
			Pool.launchPool();
			System.out.println("Escuchando en el puerto " + port);
			
			while(true) {
				 Socket clientSocket = skServidor.accept();
				 
	             System.out.println("Cliente conectado");
	             
	             Thread responseToClient = new Thread(new Runnable() {	
					@Override
					public void run() {
						try {
							System.out.println("antes del null check");
							if(clientSocket != null) {
								ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
								DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
								
								Object[] command = (Object[]) objectInputStream.readObject();
								switch(command[0].toString().toLowerCase().trim()) {
									case "listarcoleccion":
										outputStream.writeUTF("listarColeccionOK");
										ArrayList<ComicCollection> collectionList = comicCollectionRepository.list();
										ObjectOutputStream objectOStream = new ObjectOutputStream(clientSocket.getOutputStream());
										objectOStream.writeObject(collectionList);
										objectOStream.flush();
										objectOStream.close();
										break;
									case "insertarcoleccion":
										outputStream.writeUTF("insertarColeccionOK");
										ComicCollection comicCollection = (ComicCollection) command[1];
										int rowNumber = comicCollectionRepository.insertCollection(comicCollection);
										objectOStream = new ObjectOutputStream(clientSocket.getOutputStream());
										objectOStream.writeInt(rowNumber);
										objectOStream.flush();
										objectOStream.close();
										break;
									case "eliminarcoleccion":
										outputStream.writeUTF("eliminarColeccionOK");
										ComicCollection comicCollectionDel = (ComicCollection) command[1];
										int rowNumberDel = comicCollectionRepository.deleteCollection(comicCollectionDel);
										objectOStream = new ObjectOutputStream(clientSocket.getOutputStream());
										objectOStream.writeInt(rowNumberDel);
										objectOStream.flush();
										objectOStream.close();
										break;
									case "editarcoleccion":
										outputStream.writeUTF("editarColeccionOK");
										ComicCollection comicCollectionEdit = (ComicCollection) command[1];
										int rowNumberEdit = comicCollectionRepository.editCollection(comicCollectionEdit);
										objectOStream = new ObjectOutputStream(clientSocket.getOutputStream());
										objectOStream.writeInt(rowNumberEdit);
										objectOStream.flush();
										objectOStream.close();
										break;
									case "informecolecciones":
										outputStream.writeUTF("informeColeccionesOK");
										JRResultSetDataSource ds = comicCollectionRepository.showCollectionReport();
										objectOStream = new ObjectOutputStream(clientSocket.getOutputStream());
										objectOStream.writeObject(ds);
										objectOStream.flush();
										objectOStream.close();
										break;
									case "listarcomics":
										outputStream.writeUTF("listarComicsOK");
										ArrayList<Comic> comicList = comicRepository.list();
										objectOStream = new ObjectOutputStream(clientSocket.getOutputStream());
										objectOStream.writeObject(comicList);
										objectOStream.flush();
										objectOStream.close();
										break;
									case "eliminarcomic":
										outputStream.writeUTF("eliminarComicOK");
										Comic comic = (Comic)command[1];
										int rowNumberDelComic = comicRepository.deleteComic(comic);
										objectOStream = new ObjectOutputStream(clientSocket.getOutputStream());
										objectOStream.writeInt(rowNumberDelComic);
										objectOStream.flush();
										objectOStream.close();										
										break;
									default:
										System.out.println("Error al leer la acción a realizar");
								}
								outputStream.flush();
							} else {
								JOptionPane.showMessageDialog(null, "Cliente no conectado", "Servidor", JOptionPane.ERROR_MESSAGE);
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}			
					}
				});
	            responseToClient.start();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			Pool.closeConnection();
		}
	}
	
}
