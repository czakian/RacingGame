import javax.swing.ImageIcon;

/**
 *  PowerUp - Represents power up in the game.
 *  		  A car touching a power up will be faster.
 * 
 *  
 *  @author Kimberley Paterson (u4857342)
 *  
 */

public class PowerUp extends Item {

	static final ImageIcon powerUp = new ImageIcon(Car.class.getResource("gascan.jpg"));

	static final double radius = (double) powerUp.getIconWidth()/2;


	public PowerUp(Double x, Double y){

		this.x = x;
		this.y = y;
	}

	//draws the object
	@Override
	public void draw(GameState h, GameComponent canvas) {
		canvas.drawImage(powerUp.getImage(), mapx(h, canvas, x-radius), mapy(h, canvas,
				y-radius));	
	}


	// return the diameter of the power up.
	@Override
	public double height() {
		return 2 * radius;
	}
	@Override
	public double width() {
		return 2 * radius;
	}

	@Override
	public void step(GameState bw, GameComponent canvas) {
		// do nothing
	}
}

