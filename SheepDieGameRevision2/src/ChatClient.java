

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements ActionListener {

	private JTextArea enteredText = new JTextArea(10, 32);
	private JTextField typedText = new JTextField(32);
	private static final long serialVersionUID = 1L;
	private String userName;
	private Socket socket;
	private In in;
	private Out out;

	public ChatClient(String userName, String server, int port) {
		this.userName = userName;
		try {
			socket = new Socket(server, port);
			out = new Out(socket);
			in = new In(socket);

			out.println("/register " + userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unused")
			public void WindowClosing(final WindowEvent e) {
				out.close();
			}
		});

		// create GUI Stuff
		enteredText.setEditable(false);
		enteredText.setBackground(Color.LIGHT_GRAY);
		typedText.addActionListener(this);

		Container container = getContentPane();
		container.add(new JScrollPane(enteredText), BorderLayout.CENTER);
		container.add(typedText, BorderLayout.SOUTH);
		setTitle("Chat Client v 0.0.1: [" + userName + "]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		typedText.requestFocusInWindow();
		setVisible(true);
	}

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java ChatClient <username> <server> <port>");
			System.exit(-1);
		}

		ChatClient client = new ChatClient(args[0], args[1],
				Integer.parseInt(args[2]));
		client.listen();
	}

	private void listen() {
		String buffer;
		while ((buffer = in.readLine()) != null) {
			enteredText.insert(buffer + "\n", enteredText.getText().length());
			enteredText.setCaretPosition(enteredText.getText().length());
		}
		// should never gets here unless the server dies...
		out.close();
		in.close();
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("closed client socket");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = typedText.getText();
		System.out.println(message);
		String outMessage = userName + ": " + message;

		if (message.startsWith("/"))
			outMessage = message;

		out.println(outMessage);
		typedText.setText("");
		typedText.requestFocusInWindow();
	}

}
