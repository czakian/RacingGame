import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** 
 *  TopBar - Display the health of the cars and the laps done.
 * 
 * 
 *  @author Kar Wai Lim (u4361554)
 * 
 */

public class TopBar extends Item {
	
	double car1health, car2health;
	Color color1 = Color.GREEN;
	Color color2 = Color.GREEN;
	int lap1, lap2;

	
	@Override
	// draw the health of the cars and the laps
	public void draw(GameState h, GameComponent canvas) {
		
		Graphics g = canvas.getOffscreenGraphics();
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Monospaced", Font.BOLD, 32));
		g.drawString("Player 1", 20, 35);
		g.drawString("Player 2", 350, 35);
		
		// Lap Count
		g.setColor(Color.WHITE);
		g.drawString(lap1 + "/" + GameState.laps, 170 , 70);
		g.drawString(lap2 + "/" + GameState.laps, 500 , 70);
		
		// health bar
		g.drawRect(20, 50, 101, 20);
		g.drawRect(350, 50, 101, 20);
		g.setColor(color1);
		g.fillRect(21, 51, (int) car1health, 19);
		g.setColor(color2);
		g.fillRect(351, 51, (int) car2health, 19);
	}
		

	@Override
	// update the health of the cars and the laps
	public void step(GameState bw, GameComponent canvas) {
		Car car1 = (Car) bw.car.get(0);
		Car car2 = (Car) bw.car.get(1);
		car1health = 100* (1-car1.damage);
		car2health = 100* (1-car2.damage);
		if (car1health < 25) {
			color1 = Color.RED;
		} else if (car1health < 50) {
			color1 = Color.ORANGE;
		} else if (car1health < 75) {
			color1 = Color.YELLOW;
		}
		if (car2health < 25) {
			color2 = Color.RED;
		} else if (car2health < 50) {
			color2 = Color.ORANGE;
		} else if (car2health < 75) {
			color2 = Color.YELLOW;
		}
		
		lap1 = car1.lapCount;
		lap2 = car2.lapCount;
	}

	
	@Override
	public double height() {
		return 0.0;
	}
	@Override
	public double width() {
		return 0.0;
	}
}
