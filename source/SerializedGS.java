import java.io.Serializable;


public class SerializedGS implements Serializable {
	
	GameState gs;
	
	public SerializedGS(GameState gs) {
		this.gs = gs;
		
	}

	
	public GameState getGS(){
		return gs;
	}
	
	public void setGS(GameState gs){
		this.gs = gs;
	}
}
