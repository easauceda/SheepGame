

import java.util.Vector;

public class ConnectionListener extends Thread {
	private Vector<Connection> connections;

	public ConnectionListener(Vector<Connection> connections) {
		this.connections = connections;
	}

	// check for incoming messages and broadcast
	public void run() {
		while (true) {
			for (int i = 0; i < connections.size(); i++) {
				Connection ith = connections.get(i);

				// if connection terminated, remove from list of active
				// connections
				if (!ith.isAlive())
					connections.remove(i);

				// broadcast to everyone
				String message = ith.getMessage();

				if (message != null)
					if (message.startsWith("/users")) {
						String newMessage = "";
						for (Connection jth : connections) {
							newMessage += "[" + jth.getUserName() + " ]\n";
							ith.println(newMessage);
						}
					} else if (message.startsWith("/query")) {
						String toUser = "";
						String newMessage = "";
						String tmp = "";
						tmp = message.replaceFirst("\\s", "|");
						tmp = tmp.replaceFirst("\\s", "|");
						String a[] = tmp.split("\\|");
						toUser = a[1];
						newMessage = a[2];
						Connection jth = search(toUser);
						if (jth != null) {
							jth.println(newMessage);
						}
					} else {
						for (Connection jth : connections) {
							System.out.println(message);
							jth.println(message);
						}
					}
			}

			// don't monopolize processor
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Connection search(String toUser) {
		for (Connection jth : connections) {
			if (jth.getUserName().equals(toUser))
				return jth;
		}
		return null;
	}
}
