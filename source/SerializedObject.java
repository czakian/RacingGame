import java.io.*;
import java.util.*;

public class SerializedObject implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public Car car;

   public SerializedObject() {
   }

   public Car getCar(){
	   return car;
   }
   
   public void setCar(Car c){
	   car = c;
   }
}