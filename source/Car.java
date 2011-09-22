import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *  Car - Represents the racing car in the game.
 * 
 *  
 *  @author Christopher Zakian (u4889729)
 *  @edited Kar Wai Lim (u4361554)
 *  
 */

public class Car extends Item implements Serializable {


	private static final long serialVersionUID = 1L;

	// coordinate correspond to track colour
	static final int onTrackX = 80;
	static final int onTrackY = 400;

	static final int onLineX = 450;
	static final int onLineY = 200;

	// Image resources for the Car
	private Icon car = new ImageIcon(Car.class.getResource("car.png"));
	public final int width = car.getIconWidth();
	public final int height = car.getIconHeight();
	public final double hypotenuse = Math.sqrt(((double) (width*width+height*height))/4);

	// rate of car turning
	static final double delta = 0.005 * Math.PI;

	// rate of change of speed
	static final double acceleration = 0.005;

	// half width and half height correspond to the rotated car image
	public Double halfw, halfh;

	// player 1 or 2
	public int player;

	// correspond to the key pressed
	public boolean up, down, left, right;

	// lap counter and invisible check point counter
	public int lapCheck, lapCount;

	// variables that deal with car movement
	public double speed; // speed of car
	public double dir; // car direction (0 points down, increases anti-clockwise)
	public double damage; // percentage of the car damaged
	public boolean spinLeft; // uncontrollable left turning
	public int spinningTime; // time out of control in number of steps

	// coordinates of the car edges
	public double[] Xpoint, Ypoint;

	// true if the cars are touching
	public static boolean carTouching;

	// variable if the car is on track
	public boolean isOnTrack;

	// saves the state of the car when it hasn't collide
	public Double lastX, lastY, lastDir;



	public Car(Double x, Double y, int player){
		
		if (player == 2) {
			car = new ImageIcon(Car.class.getResource("car2.png"));
		}

		halfw = ((double) width) / 2;
		halfh = ((double) height) / 2;

		this.x = x;
		this.y = y;
		this.player = player;

		up = false;
		down = false;
		left = false;
		right = false;

		lapCheck = 0;
		lapCount = 0;

		speed = 0.0;
		dir = 0.5 * Math.PI;
		damage = 0.0;
		spinLeft = false;
		spinningTime = 0;

		Xpoint = new double[8];
		Ypoint = new double[8];
		updatePoints();

		carTouching = false;
		isOnTrack = true;

		lastX = x;
		lastY = y;
		lastDir = dir;
	}


	//draws the car
	@Override
	public void draw(GameState h, GameComponent canvas) {

		// rotate the car image
		ImageIcon c = createRotatedImage(canvas, car, 540 - Math.abs(dir*180/Math.PI)); //convert radians to degrees

		// update halfw and halfh based on the rotated image
		halfw =  ((double) c.getIconWidth()) / 2;
		halfh =  ((double) c.getIconHeight()) / 2;

		canvas.drawImage(c.getImage(), mapx(h, canvas, x-halfw), mapy(h, canvas,
				y-halfh));	
	}


	@Override
	public double height() {
		return (double) height;
	}

	@Override
	public double width() {
		return (double) width;
	}


	// check if the car is on track
	public boolean isOnTrack(GameState h, GameComponent canvas, Car c){

		Color trackColor = TrackBackground.getStaticColor(h, canvas, onTrackX, onTrackY);
		Color lineColor = TrackBackground.getStaticColor(h, canvas, onLineX, onLineY);
		Color pointColor;

		for (int i=0; i<8; i++) {
			pointColor = TrackBackground.getStaticColor(h, canvas, (int) Xpoint[i], (int) Ypoint[i]);
			if (!(pointColor.equals(trackColor) || pointColor.equals(lineColor))) {
				return false;
			}
		}
		return true;
	}


	// steps the car
	@Override
	public void step(GameState bw, GameComponent canvas) {

		//run these methods at each step to update the fields
		isOnTrack = isOnTrack(bw, canvas, this);


		if (isOnTrack && !carTouching) {
			y += speed * Math.cos(dir) * (1-damage); 
			x += speed * Math.sin(dir) * (1-damage);
			speed -= speed * 0.001 ; //decrement that is akin to rolling resistance
			lastX = x;
			lastY = y;
			lastDir = dir;


			if (spinningTime > 0) {

				dir += 0.1 * (spinLeft? 1:-1) * delta * (speed * spinningTime/10);
				spinningTime--;
			}
		} else {
			x = lastX;
			y = lastY;
			dir = lastDir;
			y -= speed * Math.cos(dir); 
			x -= speed * Math.sin(dir);
			damage+=(1-damage)*Math.abs(speed)/20;
			speed = - speed/3;
			unstuck(bw, canvas);
		}

		if (isOnTrack && !carTouching) {

			if (up) {
				speed += acceleration;
			}
			if (down) {
				if (speed > 0) {
					speed -= 5 * acceleration;
				} else if (speed > -0.3) {
					speed -= acceleration;	
				}
			}
			if (left) {
				dir += delta * (0.8*speed);
			}
			if (right) {
				dir -= delta * (0.8*speed);
			}

			// set dir to be within 0 and 2*pi
			dir += 2*Math.PI;
			dir = dir % (2*Math.PI);			
		}

		updatePoints();
		lapCount(bw);
	}

	// update the coordinate of the edges of the car
	private void updatePoints() {
		double topX = (height) * Math.sin(dir) / 2;
		double topY = (height) * Math.cos(dir) / 2;
		double leftX = (width) * Math.cos(dir) / 2;
		double leftY = -(width) * Math.sin(dir) / 2;

		Xpoint[0] = x + topX + leftX;
		Xpoint[1] = x + leftX;
		Xpoint[2] = x - topX + leftX;
		Xpoint[3] = x - topX;
		Xpoint[4] = x - topX - leftX;
		Xpoint[5] = x - leftX;
		Xpoint[6] = x + topX - leftX;
		Xpoint[7] = x + topX;


		Ypoint[0] = y + topY + leftY;
		Ypoint[1] = y + leftY;
		Ypoint[2] = y - topY + leftY;
		Ypoint[3] = y - topY;
		Ypoint[4] = y - topY - leftY;
		Ypoint[5] = y - leftY;
		Ypoint[6] = y + topY - leftY;
		Ypoint[7] = y + topY;
	}

	// unstuck the car upon getting stuck to the wall
	public void unstuck(GameState h, GameComponent canvas) {
		Color trackColor = TrackBackground.getStaticColor(h, canvas, onTrackX, onTrackY);
		Color leftColor = TrackBackground.getStaticColor(h, canvas, (int)(x - 30), (int) (y));
		Color upColor = TrackBackground.getStaticColor(h, canvas, (int)(x), (int) (y - 30));
		Color downColor = TrackBackground.getStaticColor(h, canvas, (int)(x), (int) (y + 30));
		Color rightColor = TrackBackground.getStaticColor(h, canvas, (int)(x + 30), (int) (y));
		if (!(trackColor.equals(rightColor))) {
			x = x - 0.2;
		}
		if (!(trackColor.equals(leftColor))) {
			x = x + 0.2;
		}
		if (!(trackColor.equals(upColor))) {
			y = y + 0.2;
		}
		if (!(trackColor.equals(downColor))) {
			y = y - 0.2;
		}
	}


	// update lap counter and lap check point
	public void lapCount(GameState bw) {

		if (lapCount == GameState.laps) {
			bw.gamefinish(player);
		} else if (lapCheck == 0 && x > 442 && y < 300) {
			lapCheck = 1;
		} else if (lapCheck == 1 && x > 700 && y > 480) {
			lapCheck = 2;
		} else if (lapCheck == 2 && x < 442 && y > 600) {
			lapCheck = 3;
		} else if (lapCheck == 3 && x < 200 && y < 480) {
			lapCheck = 4;
		} else if (lapCheck == 4 && x > 442 && y < 300) {
			lapCheck = 1;
			lapCount++;
		}

		// if move in reverse direction
		if (lapCheck == 4 && x < 200 && y > 480) {
			lapCheck = 0;
		}

	}


	//determines if the car is touching another OilSlick
	public boolean istouching(OilSlick i) {

		// optimised calculation
		if (halfw + (i.width() / 2) < Math.abs(x - i.x)) {
			return false;
		} else if (halfh + (i.height() / 2) < Math.abs(y - i.y)) {
			return false;
		}


		// check if the corner of the car is in oil slick
		for (int j=0; j<8; j++) {
			Double xd = i.x - Xpoint[j];
			Double yd = i.y - Ypoint[j];
			if (Math.sqrt(xd * xd + yd * yd) <= (i.width() / 2)) {
				return true;
			}
		}

		return false;



	}

	//determines if the car is touching another Car
	public boolean istouching(Car c) {

		// optimised calculation
		Double distance = this.distance(c);
		if (distance > 2*hypotenuse) {
			return false;
		}  else if (distance < width) {
			return true;
		}

		// when distance is in between width and 2*hypotenuse		
		for (int j=0; j<7; j=j+2) {
			for (int k=0; k<7; k=k+2) {
				if (Line2D.linesIntersect(Xpoint[j], Ypoint[j], Xpoint[(j+2)%8], Ypoint[(j+2)%8], c.Xpoint[k], c.Ypoint[k], c.Xpoint[(k+2)%8] , c.Ypoint[(k+2)%8])) {
					return true;
				}
			}
		}
		return false;

	}



	// rotation method by David Qiao. used with permission.
	final static double DEGREE_90 = 90.0 * Math.PI / 180.0;

	/**
	 * Creates a rotated version of the input image.
	 *
	 * @param c            The component to get properties useful for painting, e.g. the foreground
	 *                     or background color.
	 * @param icon         the image to be rotated.
	 * @param rotatedAngle the rotated angle, in degree, clockwise. It could be any double but we
	 *                     will mod it with 360 before using it.
	 *
	 * @return the image after rotating.
	 */
	public static ImageIcon createRotatedImage(Component c, Icon icon, double rotatedAngle) {
		//        convert rotatedAngle to a value from 0 to 360
		double originalAngle = rotatedAngle % 360;
		if (rotatedAngle != 0 && originalAngle == 0) {
			originalAngle = 360.0;
		}

		//        convert originalAngle to a value from 0 to 90
		double angle = originalAngle % 90;
		if (originalAngle != 0.0 && angle == 0.0) {
			angle = 90.0;
		}

		double radian = Math.toRadians(angle);

		int iw = icon.getIconWidth();
		int ih = icon.getIconHeight();
		int w;
		int h;

		if ((originalAngle >= 0 && originalAngle <= 90) || (originalAngle > 180 && originalAngle <= 270)) {
			w = (int) (iw * Math.sin(DEGREE_90 - radian) + ih * Math.sin(radian));
			h = (int) (iw * Math.sin(radian) + ih * Math.sin(DEGREE_90 - radian));
		}
		else {
			w = (int) (ih * Math.sin(DEGREE_90 - radian) + iw * Math.sin(radian));
			h = (int) (ih * Math.sin(radian) + iw * Math.sin(DEGREE_90 - radian));
		}
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		Graphics2D g2d = (Graphics2D) g.create();

		// calculate the center of the icon.
		int cx = iw / 2;
		int cy = ih / 2;

		// move the graphics center point to the center of the icon.
		g2d.translate(w/2, h/2);

		// rotate the graphics about the center point of the icon
		g2d.rotate(Math.toRadians(originalAngle));

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		icon.paintIcon(c, g2d, -cx, -cy);
		g2d.dispose();
		return new ImageIcon(image);
	}

}
