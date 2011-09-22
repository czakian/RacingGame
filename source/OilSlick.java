import javax.swing.ImageIcon;

/**
 *  OilSlick - Represents the racing car in the game.
 *  		   A car touching an oil slick will be out of control.
 * 
 *  
 *  @author Kar Wai Lim (u4361554)
 *  
 */

public class OilSlick extends Item {

	static final ImageIcon oil = new ImageIcon(Car.class.getResource("oil.png"));
	
	static final double radius = (double) oil.getIconWidth()/2;
	
	// variable if the oil slick is active (hasn't been touched)
	public boolean active;
	
	public OilSlick(double x, double y){
		
		this.x = x;
		this.y = y;
		active = true;
	}
	
	//draws the object
	@Override
	public void draw(GameState h, GameComponent canvas) {
		if (active) {
		canvas.drawImage(oil.getImage(), mapx(h, canvas, x-radius), mapy(h, canvas,
				y-radius));	
		}
	}
	
	// deactivate the oil slick upon touched
	public void deactivate() {
		active = false;
	}
	
	// return the diameter of the oil slick.
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