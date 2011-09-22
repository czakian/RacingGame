import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** 
 *  GameTimer - Keep track of the time passed since the start of the game.
 * 
 * 
 *  @author Kar Wai Lim (u4361554)
 * 
 */

public class GameTimer extends Item {

	static final long startTime = System.currentTimeMillis();
	static long currentTime;
	public long timeDiff;
	private long seconds;
	private long minutes;


	public GameTimer() {
		currentTime = System.currentTimeMillis();
		timeDiff = 0;
		seconds = 0;
		minutes = 0;
	}

	@Override
	// calculate the time passed and convert them
	public void step(GameState bw, GameComponent canvas) {
		currentTime = System.currentTimeMillis();
		timeDiff = currentTime - startTime;
		int timeDiffseconds = (int) (timeDiff / 1000);
		minutes = timeDiffseconds / 60;
		seconds = timeDiffseconds % 60;
	}

	@Override
	// draw the timer on the top right corner
	public void draw(GameState h, GameComponent canvas) {
		Graphics g = canvas.getOffscreenGraphics();
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, 32));
		g.drawString("" + (minutes<10? " ":"") + minutes + ":" +
						  (seconds<10?"0" + seconds:seconds), canvas.xdim - 140 , 40);
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
