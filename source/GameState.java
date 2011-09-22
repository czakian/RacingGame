import java.io.Serializable;
import java.util.Random;

/** 
 *  GameState - Keep track of the game state.
 * 
 * 
 *  @author Kar Wai Lim (u4361554)
 *  @edited Christopher Zakian (u4889729)
 * 
 */

public class GameState implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	static final int laps = 1;
	
	// starting point for cars
	static final double car1StartX = 360.0, car1StartY = 140.0;
	static final double car2StartX = 360.0, car2StartY = 200.0;
	
	// variables when the game is ended
	public boolean finished;
	public int winner;
	public double hiscore;
	
	// core components for the game
	public Items car;
	public Items oil;
	public TrackBackground track;
	public GameTimer timer;
	public TopBar bar;
	
	
	public GameState(double xs, double ys) {
		
		finished = false;
		winner = 0;
		hiscore = 0;
		
		car = new Items();
		oil = new Items();
		track = new TrackBackground();
		timer = new GameTimer();
		bar = new TopBar();
		
		// add cars to the game
		car.add(new Car(car1StartX, car1StartY, 1));
		car.add(new Car(car2StartX, car2StartY, 2));
			
		// add oil slicks to the game
		oil.add(new OilSlick(840.0, 350.0));
		oil.add(new OilSlick(730.0, 650.0));
		oil.add(new OilSlick(450.0, 760.0));
		oil.add(new OilSlick( 75.0, 650.0));
		oil.add(new OilSlick(160.0, 300.0));
		oil.add(new OilSlick(285.0, 140.0));
		oil.add(new OilSlick(275.0, 670.0));
	}
	
	
	// steps the game
	public void gameStep(GameComponent canvas) { 
		
		Car car1 = (Car) car.get(0);
		Car car2 = (Car) car.get(1);
		Car.carTouching = car1.istouching(car2);
		
		// parsing controls
		car1.up = canvas.p1up;
		car1.down = canvas.p1down;
		car1.left = canvas.p1left;
		car1.right = canvas.p1right;
		car2.up = canvas.p2up;
		car2.down = canvas.p2down;
		car2.left = canvas.p2left;
		car2.right = canvas.p2right;
		
		// unstuck the cars
		if(Car.carTouching) {
			car1.x += 0.1*Math.signum(car1.x-car2.x);
			car2.x -= 0.1*Math.signum(car1.x-car2.x);
			car1.y += 0.1*Math.signum(car1.y-car2.y);
			car2.y -= 0.1*Math.signum(car1.y-car2.y);	
		}
		
		// steps the cars
		for (Item i : car) {		
			
			Car c = (Car) i;
			OilSlick closestOil = (OilSlick) oil.closest(c); // nearest oil slick
			
			c.step(this, canvas);

			// car will be out of control upon touching oil slick
			if(c.istouching(closestOil) && closestOil.active) {
				Random r = new Random();
				c.spinLeft = r.nextBoolean();
				c.spinningTime = 100;
				closestOil.deactivate();
			}
		}
		
		// steps the timer and top bar
		timer.step(this, canvas);
		bar.step(this, canvas);
	}
	
	
	// draw the game, components are drawn to order
	public void draw(GameComponent canvas) {
		
		track.draw(this, canvas.background);
		
		for (Item i : oil) {			
			i.draw(this, canvas);
		}
		
		for (Item i : car) {
			i.draw(this, canvas);
		}
		
		timer.draw(this, canvas);
		bar.draw(this, canvas);
	}
	
	public void gamefinish(int winner) {
		finished = true;
		this.winner = winner;
		this.hiscore = (double) timer.timeDiff / 1000;
	}

}
