import java.io.Serializable;


public class SerializedInt implements Serializable {
	
	int i;
	
	public SerializedInt(int i) {
		this.i = i;
	}
	
	public int getI(){
		return i;
	}
	
	public void setI(int i){
		this.i = i;
	}

}
