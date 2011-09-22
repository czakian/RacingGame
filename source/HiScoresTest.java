import java.io.IOException;

/**
 *  HiScoresTest - Test Class for HiScores and HiScore class.
 * 
 *  @param args
 *  @throws IOException 
 *  
 *  @author Kar Wai Lim (u4361554)
 *  
 */

public class HiScoresTest {


	public static void main(String[] args) throws IOException {

		HiScore s1 = new HiScore("A", 21.321);
		HiScore s2 = new HiScore("B", 1.311);
		HiScore s3 = new HiScore("C", 22.662);
		HiScore s4 = new HiScore("D", 20.213);
		HiScore s5 = new HiScore("E", 17.013);

		HiScores h = new HiScores();
		h.add(s1);
		h.add(s2);
		h.add(s3);
		h.add(s4);
		h.add(s5);
		h.newHiScore("F", 9.776);

		h.printScore();
		System.out.println("################");

		h.saveScore();

		HiScores g = new HiScores();
		g.loadScore();
		g.newHiScore("G", 0.2);
		g.newHiScore("H", 0.245);
		g.newHiScore("I", 0.245);
		g.newHiScore("J", 0.245);
		g.newHiScore("K", 0.254);
		g.newHiScore("L", 0.243);

		g.printScore();
		System.out.println("################");
		System.out.println(g.isHiScore(99.023));
		System.out.println(g.isHiScore(9.023));
		System.out.println(g.isHiScore(20.22));
	}

}
