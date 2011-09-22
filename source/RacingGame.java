import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.JFrame;

/**
 *  RacingGame - Main class.
 *  		 
 *    
 *  @author Kimberley Paterson (u4857342)
 *  @edited Christopher Zakian (u4889729) (networking)
 *  
 */


@SuppressWarnings("serial")
public class RacingGame extends JComponent implements ActionListener {
	
	GameServer server;
	Client client;
	private JFrame jframe;	
	JMenuBar menu;
	SplashScreen splashscreen;
	private JPanel panel;
	static RGame rg;
	Timer timer;
	
	HighScoreGui hsg;
	
	public RacingGame() throws Exception {
		
		
		Image img = (new ImageIcon(RacingGame.class.getResource("checkeredflag.jpg")).getImage());
		jframe = new JFrame("Car Racing Test");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setPreferredSize(new Dimension(800, 693));
		splashscreen = new SplashScreen(img);
		menu = this.createMenuBar();
		jframe.setJMenuBar(menu);
	
		panel = new JPanel();
		 JButton button1 = new JButton("New Game");
	       button1.setAlignmentX(Component.RIGHT_ALIGNMENT);
	       button1.add(Box.createRigidArea(new Dimension(100,40)));
	       button1.setActionCommand("new game");
	       button1.addActionListener(this);
	       //makes the position of the button on the screen.
	       panel.add(Box.createRigidArea(new Dimension(60,30)));
	       panel.add(button1);
	       
	      

	       JButton button2 = new JButton("Instructions");
	       panel.add(button2);
	       button2.setAlignmentX(Component.RIGHT_ALIGNMENT);
	       button2.add(Box.createRigidArea(new Dimension(100,40)));
	       panel.add(Box.createRigidArea(new Dimension(60,30)));
	       panel.add(button2);

	       JButton button3 = new JButton("Settings");
	       panel.add(button3);
	       button3.setAlignmentX(Component.RIGHT_ALIGNMENT);
	       button3.add(Box.createRigidArea(new Dimension(100,40)));
	       panel.add(Box.createRigidArea(new Dimension(60,30)));
	       panel.add(button3);

	       JButton button4 = new JButton("High Scores");
	       panel.add(button4);
	       button4.setAlignmentX(Component.RIGHT_ALIGNMENT);
	       button4.add(Box.createRigidArea(new Dimension(100,40)));
	       button4.setActionCommand("hiscore");
	       button4.addActionListener(this);
	       panel.add(Box.createRigidArea(new Dimension(60,30)));
	       panel.add(button4);

	       
	      //focus issue!!! all buttons don't show.
	       jframe.add(splashscreen).transferFocus();
	       
	       panel.setBackground(Color.blue);
	       jframe.getContentPane().add(panel);
	    
	     //sets a default button
	       JRootPane rootPane = button1.getRootPane();
	       rootPane.setDefaultButton(button1);
	       
	      
		jframe.pack();
		jframe.setVisible(true);
		
		
		
	}

	public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the file menu.
        JMenu menu = new JMenu("file");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);
        
        
        //Start a new Game.
        JMenuItem menuItem = new JMenuItem("new game");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new game");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
 
        //Quit the Game.
        JMenuItem quit = new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        quit.setActionCommand("quit");
        quit.addActionListener(this);
        menu.add(quit);
        
        //Set up the Networked Game menu.
        JMenu network = new JMenu("Networked Game");
        menuBar.add(network);
        
        JMenuItem ipaddy = new JMenuItem("show IP address");
        ipaddy.setActionCommand("ip");
        ipaddy.addActionListener(this);
        network.add(ipaddy);
        
        //Start the server.
        JMenuItem startServer = new JMenuItem("start server");
        startServer.setActionCommand("start server");
        startServer.addActionListener(this);
        network.add(startServer);

 
        //Join the network.
        JMenuItem joinNetwork = new JMenuItem("join network");
        joinNetwork.setActionCommand("join network");
        joinNetwork.addActionListener(this);
        network.add(joinNetwork);
        
        //run the server and start the game
        JMenuItem startGame = new JMenuItem("start networked game");
        startGame.setActionCommand("run server");
        startGame.addActionListener(this);
        network.add(startGame);

        return menuBar;
    }

	 //Create a new internal frame.
    protected void createFrame() {
       rg = new RGame();
        
       jframe.add(rg);
        
        try {
            rg.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
        
        //start the game
       rg.run();
    }
	
    protected void createHScore() {
    
	hsg = new HighScoreGui();
        jframe.add(hsg);
         
         try {
             hsg.setSelected(true);
         } catch (java.beans.PropertyVetoException e) {}
         
        
     }
	
	public void actionPerformed(ActionEvent event) {
		

		if("new game".equals(event.getActionCommand())) {
			this.createFrame();
		}
		
		if("start server".equals(event.getActionCommand())){
		
			try {
				rg = new RGame();
				server = new GameServer(rg.game);
				//server.run();
				JOptionPane.showMessageDialog(jframe, "server started");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(jframe, "Server Error!");
				e.printStackTrace();
			}
		     			        
		}
		
		if ("join network".equals(event.getActionCommand())){
			
			String i = JOptionPane.showInputDialog("input IP");
			client = new Client(i);
			server.accepting = false;
			jframe.add(rg);
			rg.run();
			JOptionPane.showMessageDialog(jframe, "player connected");
		}
		
		if ("run server".equals(event.getActionCommand())) {
			
			JOptionPane.showMessageDialog(jframe, "server running!");
//		        client.oos.writeObject(client.)
		}
		
		
		 if ("ip".equals(event.getActionCommand())) {
				InetAddress inetAddress;
				try {
					inetAddress = InetAddress.getLocalHost();
					String ipAddress = inetAddress.getHostAddress();
					JOptionPane.showMessageDialog(jframe, ipAddress);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
		 
		 if("quit".equals(event.getActionCommand())) {
			 server.stop();
		 }
		 if ("hiscore".equals(event.getActionCommand())) {
			// System.out.println("1234");
			 this.createHScore();
			 
		 }
	}		
	
	

	
	
	
	public void getIP(){
		InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getLocalHost();
			String ip = inetAddress.getHostAddress();
			 
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	


	
	
	public static void main(String[] args) throws Exception {
		RacingGame racinggame;
		racinggame = new RacingGame();


	
	}	
	
}

