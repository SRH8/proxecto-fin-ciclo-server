package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import controller.Pool;
import model.entities.ComicCollection;
import repository.ComicCollectionRepository;

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
								DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
								DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
								
								String command = inputStream.readUTF();
								System.out.println("llega antes de switch");
								System.out.println("COMANDO QUE LLEGA AL SERVER " + command.toLowerCase().trim());
								switch(command.toLowerCase().trim()) {
									case "listarcoleccion":
										outputStream.writeUTF("listarColeccionOK");
										ArrayList<ComicCollection> collectionList = comicCollectionRepository.list();
										ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
										System.out.println("llega antes de devolver lista");
										objectOutputStream.writeObject(collectionList);
										System.out.println("llega despues de devolver lista");
										objectOutputStream.flush();
									break;
									default:
								}
								outputStream.flush();
							} else {
								JOptionPane.showMessageDialog(null, "Cliente no conectado", "Servidor", JOptionPane.ERROR_MESSAGE);
							}
						} catch (IOException e) {
							e.printStackTrace();
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
