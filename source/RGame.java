import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *  RGame - Associates with the main game.
 *  		 
 *    
 *  @author Christopher Zakian (u4889729)
 *  @edited Kar Wai Lim (u4361554)
 *    
 */

@SuppressWarnings("serial")
public class RGame extends JInternalFrame implements ActionListener, Serializable {

	static final int xOffset = 30, yOffset = 30;


	//set the canvas size to the dimensions of the background image
	final static Integer xcanvas = TrackBackground.width, ycanvas = TrackBackground.height;


	final static Integer delay = 10; //is in milliseconds

	public GameComponent canvas;
	public GameState game;
	private Timer timer;
	private JFrame jframe;


	public RGame(){

		super("Race Game", 
				false, //resizable
				true, //closable
				false, //maximizable
				true);//iconifiable

		//...Create the GUI and put it in the window...

		//...Then set the window size or call pack...
		setSize(xcanvas,ycanvas);

		//Set the window's location.
		setLocation(xOffset, yOffset);

		jframe = new JFrame("Car Racing Test");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new GameComponent(xcanvas, ycanvas);
		(jframe.getContentPane()).add(canvas);
		timer = new Timer(delay, this);
		jframe.pack();
		jframe.setVisible(true);

	}

	private void setBackGround(){

		Graphics g = canvas.getBackgroundGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, xcanvas, ycanvas);
	}

	public void run(){

		setBackGround();
		game = new GameState(xcanvas, ycanvas);
		timer.start();	
	}

	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == timer) {
			if (game.finished){
				Graphics g = canvas.getOffscreenGraphics();
				g.setColor(Color.GREEN);

				g.setFont(new Font("Monospaced", Font.BOLD, 32));
				g.drawString("Congratulation, " + game.winner + "!", xcanvas / 3 - 80 , ycanvas / 2);
				canvas.drawOffscreen();

				setHiScore("You beat the game in " + game.hiscore + " seconds!\n");
				timer.stop();

			} else {
				canvas.clearOffscreen();
				game.draw(canvas);
				canvas.drawOffscreen();
				game.gameStep(canvas);
			}

		}
	}

	// add new high score to the high score table
	public void setHiScore(String str) {
		HiScores hs = new HiScores();
		hs.loadScore();
		if(hs.isHiScore(game.hiscore)) {
			String name = JOptionPane.showInputDialog(str + "Please enter your name.");
			if (name == null) {
				System.out.println("High Score is not saved.");
				return;
			}
			if (name.length() > 20) {
				setHiScore("The name you input is too long.\n");
				return;
			}
			if (name.contains(";")) {
				setHiScore("The character ';' is not allowed.\n");
				return;
			}
			hs.newHiScore(name, game.hiscore);
			try {
				hs.saveScore();
			} catch (IOException e) {
				System.out.println("Fail to save high score!");
			}							
		}
	}
}
