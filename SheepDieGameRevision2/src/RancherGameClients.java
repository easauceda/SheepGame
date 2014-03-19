import java.io.FileNotFoundException;

public class RancherGameClients implements GameSettings {
	
	public static void main(String[] args) {
		readTheFile();

		// This pretty much starts two rancher games one with sheep and another
		// with wolfs;
		if (input.getNumberOfWolfs() > 0) {
			serverThread();

			chatClientThread();
			// moved server over here to make sure only one is running
			wolfClientThread();
		}
		if (input.getNumberOfSheep() > 0) {
			sheepClientThread();
		}
		if (input.getPack()) {
			wolfPackThread();
		}
	}

	private static void readTheFile() {
		try {
			new input();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void wolfClientThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					String args[] = { "WolfServer", "localhost",
							input.getPort(), "wolf" };
					RancherGame.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		MyThread wolfThread = new MyThread();
		wolfThread.start();
	}

	private static void sheepClientThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					String args[] = { input.getNick(), input.getHost(),
							input.getPort(), "sheep" };
					RancherGame.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		MyThread sheepThread = new MyThread();
		sheepThread.start();
	}

	private static void wolfPackThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					String args[] = { "WolfServer", input.getHost(),
							input.getPort() };
					WolfPack.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		MyThread serverThread = new MyThread();
		serverThread.start();
	}

	private static void chatClientThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					String args[] = { "Check", input.getHost(), input.getPort() };
					ChatClient.main(args);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		MyThread serverThread = new MyThread();
		serverThread.start();
	}

	private static void serverThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					ChatServer.main(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		MyThread serverThread = new MyThread();
		serverThread.start();
	}
}
