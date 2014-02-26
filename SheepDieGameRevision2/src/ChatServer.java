

import java.net.Socket;
import java.net.ServerSocket;
import java.util.Vector;

public class ChatServer {

	public static void main(String[] args) throws Exception {
		Vector<Connection> connections = new Vector<Connection>();
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(4444);
		
		// create a new connectionlistener thread
		ConnectionListener connectionListener = new ConnectionListener(
				connections);

		// start the thread
		connectionListener.start();

		// print to stderr the message
		System.err.println("ChatServer started");

		while (true) {

			// wait for next client connection request
			Socket clientSocket = serverSocket.accept();
			System.err.println("Created socket with client");

			// listen to client in a separate thread
			Connection connection = new Connection(clientSocket);
			connections.add(connection);
			connection.start();
		}
	}
}
