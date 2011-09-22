import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *  HiScoreGui - Create a window to display the High Score Table.
 * 
 *  
 *  @author Christopher Zakian (u4889729)
 *  
 */

@SuppressWarnings("serial")
public class HighScoreGui extends JInternalFrame {

	public JFrame jframe;
	public HiScores hs;

	public HighScoreGui() {
		super("HiScores", 
				false, //resizable
				true, //closable
				false, //maximizable
				true);//iconifiable     

		jframe = new JFrame("High Score Table");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hs = new HiScores();
		(jframe.getContentPane()).add(hs);
		jframe.pack();
		jframe.setFocusable(true);
		jframe.setVisible(true);
	}
}
