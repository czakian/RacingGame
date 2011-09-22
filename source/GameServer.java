import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sql.rowset.serial.SerialJavaObject;


public class GameServer extends Thread  implements Runnable{
	
	 private ServerSocket gameServer;
	 GameState gs;
	 public boolean accepting;
	  private Socket client = null;
	   private ObjectInputStream ois = null;
	   private ObjectOutputStream oos = null;
	  
	   
	   public GameServer(GameState gs) throws Exception {
		   this.gs = gs;
		   gameServer = new ServerSocket(4000);
	     System.out.println("Server listening on port 4000.");
	     accepting = true;
	     this.start();
	   } 

	  
	  
	   public void run() {
	     while(accepting) {
	       try {
	        System.out.println("Waiting for connections.");
	        Socket client = gameServer.accept();
	        System.out.println("Accepted a connection from: "+
	client.getInetAddress());
	        Connect c = new Connect(client, gs);
	       } catch(Exception e) {}
	     }
	   }

	   class Connect extends Thread implements Runnable, {
		   private Socket client = null;
		   private ObjectInputStream ois = null;
		   private ObjectOutputStream oos = null;
		   private SerializedInt player;
		    
		   public Connect() {}

		  
		   public Connect(Socket clientSocket, GameState gs, int player) {
		     client = clientSocket;
		     this.player = new SerializedInt(player);
		     
		     try {
		      ois = new ObjectInputStream(client.getInputStream());
		      oos = new ObjectOutputStream(client.getOutputStream());
		     } catch(Exception e1) {
		         try {
		            client.close();
		         }catch(Exception e) {
		           System.out.println(e.getMessage());
		         }
		         return;
		     }
		     this.start();
		   }

		   public void run() {
		     
		      SerializedObject car = new SerializedObject(); 
		      car.setCar(new Car(gs.xstart, gs.ystart, player));
		      
		      try {
		         player = (SerializedObject) ois.readObject();
		         car = (SerializedObject) ois.readObject();
		      
		         if (player.e) {
		        	 gs.car.set(0, car.getCar());
		      	 } else { 
		      		 gs.car.set(1, car.getCar());
		      	 }	
		     
		        
		         // ship the object to the client
		         SerializedGS output = new SerializedGS(gs);
		         oos.writeObject(output);
		         oos.flush();
		         System.out.println(output);
		         // close connections
		         ois.close();
		         oos.close();
		         client.close(); 
		      } catch(Exception e) {}       
		   }
		}}