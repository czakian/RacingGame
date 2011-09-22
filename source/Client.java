import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {
		
		ObjectInputStream ois;

		ObjectOutputStream oos;
		GameState gs;
		Car c;
		
		public Client(String ipAddress) {
			
		      try {
		        // open a socket connection

		    	  Socket socket = new Socket(ipAddress, 4000);
		        // open I/O streams for objects
		        oos = new ObjectOutputStream(socket.getOutputStream());
		        ois = new ObjectInputStream(socket.getInputStream());
		        
		        //player 1
		        SerializedObject c = new SerializedObject();
		        SerializedObject d = new SerializedObject();
		        c.setCar(new Car(30.0, 300.0, 1));
		        d.setCar((Car)(gs.car.get(1)));
		      
		        // write the objects to the server
		        oos.writeObject(c);
		        
		        oos.flush();
		        // read an object from the server
		        gs = (GameState) ois.readObject();
		        
		      if (gs.finished){
		        oos.close();
		        ois.close();
		      }
		      } catch(Exception e) {
		        System.out.println(e.getMessage());
		      }
		   }
}