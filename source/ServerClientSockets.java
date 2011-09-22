/*
 * 
 * Eric McCreath 2008
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ServerClientSockets extends ArrayList<ServerClientSocket>
implements Runnable {
static final int SOCKETNUMBER = 5012;

ServerSocket server;
boolean acceptingplayers;
RGame rg;

public ServerClientSockets(RGame rg) {
 this.rg = rg;
try {
	server = new ServerSocket(SOCKETNUMBER);
	acceptingplayers = true;

	server.setSoTimeout(1000);
} catch (SocketException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	JOptionPane.showMessageDialog(null, "Problem creating socket",
			"Problem", JOptionPane.ERROR_MESSAGE);
}

}

public void run() {
while (acceptingplayers) {
	try {
		ServerClientSocket scs;

		scs = new ServerClientSocket(server);
		System.out.println("Accepted" + scs.client.getInetAddress());
		if (!has(scs.client.getInetAddress())  || carrace.multicar.getState()) add(scs);
		RGame.showConnected(this);
	} catch (SocketTimeoutException ste) {
		System.out.println("SocketTimeout");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("IO Exception " + e);
	}
}
}

private boolean has(InetAddress inetAddress) {
for (ServerClientSocket s : this) {
	if (s.client.getInetAddress().equals(inetAddress)) {
		return true;
	}
}
return false;
}

public void close() {
try {
	server.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

}
