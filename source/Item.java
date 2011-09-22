/**
 *  Item - Abstract class for all items in the game.
 * 
 *  
 *  @author Christopher Zakian (u4889729)
 *  
 */

public abstract class Item {

	public double x, y;

	public abstract void draw(GameState h, GameComponent canvas);

	public abstract void step(GameState bw, GameComponent canvas);

	public abstract double width();

	public abstract double height();

	// This calculates the distance between centers.
	public double distance(Item i) {
		double xd = i.x - x;
		double yd = i.y - y;
		return Math.sqrt(xd * xd + yd * yd);
	}


	public static int mapx(GameState h, GameComponent canvas, double x) {
		return (int) Math.round(x);
	}

	public static int mapy(GameState h, GameComponent canvas, double y) {
		return (int) Math.round(y);
	}
}