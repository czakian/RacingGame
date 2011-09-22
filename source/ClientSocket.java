/*
 * ClientSocket
 * Eric McCreath 2008
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class ClientSocket {

	
	

	Socket client;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	Car c;

	public boolean connect(String ipaddress, Car c) {
		this.c = c;
		
		try {
			client = new Socket(ipaddress,ServerClientSockets.SOCKETNUMBER);
			
			   ois = new ObjectInputStream(client.getInputStream());
			   oos = new ObjectOutputStream(client.getOutputStream());;
	        return true;
//	        
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Cannt find the server!!! : " + ipaddress, "Problem", JOptionPane.ERROR_MESSAGE);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO Problem !!!  " , "???", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return false;
	}

	public void run() {
	
		 try {
			oos.writeObject(c);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
			
		}
	}
	
	
	
