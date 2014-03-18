
public class RancherGameClients {

	public static void main(String[] args) {
		//This pretty much starts two rancher games one with sheep and another with wolfs;
		serverThread();
		chatClientThread();
		//moved server over here to make sure only one is running
		wolfClientThread();
		sheepClientThread();
	}
	private static void wolfClientThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					String args[] = { "WolfServer", "localhost", "4444","wolf" };
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
					String args[] = { "SheepClient", "localhost", "4444","sheep" };
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
	
	private static void chatClientThread() {
		class MyThread extends Thread {
			public void run() {
				try {
					String args[] = { "Narf", "localhost", "4444" };
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
