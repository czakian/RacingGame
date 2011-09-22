import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerClientSocket {
	 
	 ServerSocket server;
	 Socket client;
	 ObjectInputStream ois;
	 ObjectOutputStream oos;
	 
	 public ServerClientSocket(ServerSocket server) throws IOException, ClassNotFoundException {

	 this.server = server;
	 server.setSoTimeout(1000);
	 client = server.accept();

	 }

}
