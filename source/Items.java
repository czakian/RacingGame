import java.util.ArrayList;

/**
 *  Items - An ArrayList of Item
 * 
 *  
 *  @author Eric McCreath 
 *  @edited Christoper Zakian (u4889729)
 *  
 */

@SuppressWarnings("serial")
public class Items extends ArrayList<Item> {
	

	//returns the closest item
	 public Item closest(Item i) { // find the closest Item to i.
	        Item c = null;
	        for (Item v : this) {    
	            if (i != v  && (c == null || c.distance(i) > v.distance(i))) {
	                c = v;
	            }
	        }
	        return c;

	 }
}
