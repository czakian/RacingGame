import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *  HiScores - Display the High Score Table and save/load them to/from a text file.
 * 
 *  
 *  @author Kar Wai Lim (u4361554)
 *  
 */

@SuppressWarnings("serial")
public class HiScores extends JComponent {


	Image bgImage = (new ImageIcon(RacingGame.class.getResource("Background.jpg")).getImage());

	private static final int numHiScore = 10; // number of high score to be shown
	private static final HiScore nullScore = new HiScore(null,999999.99);

	private HiScore[] hiScores;
	private int size;
	private boolean sorted;


	public HiScores() {

		// size of the screen
		int xdim = 800;
		int ydim = 500;

		this.setSize(xdim, ydim);
		this.setPreferredSize(new Dimension(xdim,ydim));
		this.setFocusable(true);

		hiScores = new HiScore[numHiScore+1];
		size = 0;
		sorted = false;

		for (int i=0; i < numHiScore+1; i++) {
			hiScores[i] = nullScore;
		}
	}

	public void clear() {
		size = 0;
		sorted = false;
		for (int i=0; i < numHiScore+1; i++) {
			hiScores[i] = nullScore;
		}
	}

	// add High Score to the end
	public void add(HiScore hs) { 
		hiScores[size] = hs;
		if (size < numHiScore) {  
			size++;
		}
		sorted = false;
	}

	public void sort() {
		if (!sorted) {
			Arrays.sort(hiScores);
			sorted = true;
		}
	}

	// insert a new High Score
	public void newHiScore(String name, Double score) {
		HiScore nScore = new HiScore(name, score);
		add(nScore);
		sort();
	}

	// check a score if it is a new High Score
	public boolean isHiScore(Double score) {
		sort();
		Double bestScore = hiScores[numHiScore-1].score();  // must beat the last entry
		return (score < bestScore);
	}

	// load high score from text
	public void loadScore() {

		try {
			String line;
			clear();
			BufferedReader reader = new BufferedReader(new FileReader("hiscore.txt"));
			while ((line = reader.readLine()) != null) {

				String[] str = new String[2];
				str = line.split(";");
				String name = str[0];
				Double score = Double.parseDouble(str[1]);
				HiScore hiscore = new HiScore(name,score);
				add(hiscore);
			}
			sort();

		} catch (FileNotFoundException e) {
			System.out.println("No High Score found! (File not found)");
		} catch (IOException e) {
			System.out.println("No High Score found! (IOException)");
		}

	}

	// save high score to text
	public void saveScore() throws IOException {

		try {
			PrintWriter pr = new PrintWriter("hiscore.txt");
			for (int i=0; (!hiScores[i].equals(nullScore)) && (i<numHiScore); i++) {
				pr.println(hiScores[i].name() + ";" + hiScores[i].score());
			}
			pr.close();

		} catch (FileNotFoundException e) {
			File file = new File("hiscore.txt");
			if (file.createNewFile()) {
				System.out.println("\"hiscore.txt\" is created");
				saveScore();
			} else {
				System.out.println("High Score Table failed to be created!");
			}
		}
	}

	// display the high score table
	@Override
	public void paintComponent(Graphics g) {

		int xName = 10;
		int xScore = 450;
		int y = 40;    
		loadScore();

		g.drawImage(bgImage, 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Monospaced", Font.BOLD, 32));
		for (int i=0; (!hiScores[i].equals(nullScore)) && (i<numHiScore) ; i++) {
			System.out.println("" + hiScores[i]); 
			g.drawString(hiScores[i].name(),xName, y);
			g.drawString(hiScores[i].score() + "",xScore, y);
			y += 40;
		}		
	}


	// print high score on console
	// for testing purpose
	public void printScore() {
		for (int i=0; (!hiScores[i].equals(nullScore)) && (i<numHiScore) ; i++) {
			System.out.println(hiScores[i]);
		}
	}

}
