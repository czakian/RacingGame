import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 *  GameJComponent - This class enables double buffering for a simple game. To use
 *  this class construct the GameJComponent of your desired size. Draw the required fixed
 *  background using the Graphics object obtained from getBackgroundGraphics.
 *  Then in the simulation loop: + clearOffscreen + either add images to the
 *  offscreen with drawImage or draw using the Graphics object obtained via
 *  getOffscreenGraphic() + drawOffscreen
 * 
 *  This class also listens for key presses.  
 * 
 * 
 *  @author Eric McCreath
 * 
 *  Copyright 2005
 * 
 *  @edited Kimberley Paterson and Christopher Zakian 
 * 
 */

@SuppressWarnings("serial")
public class GameComponent extends JComponent implements KeyListener, ActionListener {


	Integer xdim, ydim; // the size of the JComponent

	public BufferedImage background;

	public BufferedImage offscreen;

	//controls for player1 and player2
	public boolean p1up, p1down, p1left, p1right;
	public boolean p2up, p2down, p2left, p2right;
	public boolean qPressed;

	public GameComponent(int xd, int yd) {
		xdim = xd;
		ydim = yd;
		this.setSize(xdim, ydim);
		this.setPreferredSize(new Dimension(xdim,ydim));
		background = new BufferedImage(xdim, ydim, BufferedImage.TYPE_INT_RGB);
		offscreen = new BufferedImage(xdim, ydim, BufferedImage.TYPE_INT_RGB);
		addKeyListener(this);
		this.setFocusable(true);


	}

	public void clearOffscreen() {
		Graphics g = offscreen.getGraphics();
		g.drawImage(background, 0, 0, null);

	}

	public Graphics getBackgroundGraphics() {
		return background.getGraphics();
	}

	public Graphics getOffscreenGraphics() {
		return offscreen.getGraphics();
	}

	public void drawImage(Image i, int x, int y) {
		Graphics g = offscreen.getGraphics();
		g.drawImage(i, x, y, null);
	}

	public void drawOffscreen() {
		Graphics g;
		g = this.getGraphics();
		g.drawImage(offscreen, 0, 0, null);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(offscreen, 0, 0, null);
	}

	public void keyTyped(KeyEvent e) {

	}

	//this only works on a qwerty keyboard
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			p1up = true;
		} else if (e.getKeyCode() ==  KeyEvent.VK_DOWN) {
			p1down = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			p1left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			p1right = true;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			qPressed = true;
		} else if(e.getKeyCode() == KeyEvent.VK_W) {
			p2up = true;
		} else if ( e.getKeyCode() == KeyEvent.VK_S) {
			p2down = true;
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			p2left = true;
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			p2right = true;
		}
	}

	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			p1up = false;
		} else if (e.getKeyCode() ==  KeyEvent.VK_DOWN) {
			p1down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			p1left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			p1right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			qPressed = false;
		} else if(e.getKeyCode() == KeyEvent.VK_W) {
			p2up = false;
		} else if ( e.getKeyCode() == KeyEvent.VK_S) {
			p2down = false;
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			p2left = false;
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			p2right = false;
		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Press");
	}
}