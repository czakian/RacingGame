import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 *  TrackBackground - Represents the track in the game.
 *  		  
 *  
 *  @author Kimberley Paterson (u4857342)
 *  
 */


public class TrackBackground extends Item  {

	static final ImageIcon track = new ImageIcon(TrackBackground.class.getResource("Background.png"));

	public static final Integer width = track.getIconWidth();
	public static final Integer height = track.getIconHeight();


	public TrackBackground() {

	}
	@Override
	public void draw(GameState h, GameComponent canvas) {
		canvas.drawImage(track.getImage(), 0, 0);

	}

	public void draw(GameState h, BufferedImage bufferedImage) {
		(bufferedImage.getGraphics()).drawImage(track.getImage(), 0, 0, null);

	}
	
	//this method works much like getColor but requires x and y coordinates. 
	//a usage of this would be to get the color that the track is from the image by calling 
	//the method with the x and y coordinates located over a track section.
	public static Color getStaticColor(GameState h, GameComponent canvas, int x, int y) {

		BufferedImage g = canvas.background;

		return new Color(g.getRGB(x, y)); 
	}

	public static Color getColor(GameState h, GameComponent canvas, Car c) {

		BufferedImage g = canvas.background;

		return new Color(g.getRGB((int) Math.round(c.x), (int) Math.round(c.y)));
	}

	@Override
	public double width() {
		return width;
	}

	@Override
	public double height() {
		return height;
	}

	@Override
	public void step(GameState bw, GameComponent canvas) {
		// do nothing
	}
}
