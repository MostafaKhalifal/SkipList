import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class Database {

	// this is the SkipList object that we are using
	// a string for the name of the rectangle and then
	// a rectangle object, these are stored in a KVPair,
	// see the KVPair class for more information
	private SkipList<String, Rectangle> list;

	/**
	 * The constructor for this class initializes a SkipList object with String and
	 * Rectangle a its parameters.
	 */
	public Database() {
		list = new SkipList<String, Rectangle>();
	}

	/**
	 * Inserts the KVPair in the SkipList if the rectangle has valid coordinates and
	 * dimensions, that is that the coordinates are non-negative and that the
	 * rectangle object has some area (not 0, 0, 0, 0). This insert will insert the
	 * KVPair specified into the sorted SkipList appropriately
	 * 
	 * @param pair the KVPair to be inserted
	 */
	public void insert(KVPair<String, Rectangle> pair) {
//function check_name to check if name is valid or not
		if (!check_name(pair.getKey())) {
			System.out.println("rejected name :" + pair.getKey());
			return;
		}
		// function Is_valid to check if x,y,w,h is valid or not
		if (!pair.getValue().Is_Valid()) {
			System.out.println("Rectangle rejected: (" + pair.getKey() + ',' + pair.getValue().getX() + ','
					+ pair.getValue().getY() + ',' + pair.getValue().getWidth() + ',' + pair.getValue().getHeight()
					+ ')');
			return;
		}
		// insert value
		System.out.println("Rectangle inserted: (" + pair.getKey() + ',' + pair.getValue().getX() + ','
				+ pair.getValue().getY() + ',' + pair.getValue().getWidth() + ',' + pair.getValue().getHeight() + ')');
		list.insert(pair);
	}

	/**
	 * Removes a rectangle with the name "name" if available. If not an error
	 * message is printed to the console.
	 * 
	 * @param name the name of the rectangle to be removed
	 */
	public boolean check_name(String name) {
		// Get the first character of the string and check if it is a letter
		char firstChar = name.charAt(0);
		if (Character.isLetter(firstChar)) {
			return true; // Return true if the first character is a letter
		}
		return false; // Return false if the first character is not a letter
	}

	/**
	 * 
	 * Removes a rectangle with the name "name" from the SkipList, if available. If
	 * the
	 * 
	 * name is not valid, an error message is printed to the console.
	 * 
	 * @param name the name of the rectangle to be removed
	 */
	public void remove(String name) {
		// Remove the KVPair with the specified name from the SkipList
		KVPair<String, Rectangle> result = list.remove(name);

		// Check if the name is valid (starts with a letter), if not print an error
		// message
		if (!check_name(name)) {
			System.out.println("Rectangle rejected: (" + name + ')');
			return;
		}

		// If the KVPair was not found in the SkipList, print an error message
		if (result == null) {
			System.out.println("Rectangle not removed: (" + name + ')');
		}
		// Otherwise, print a success message with the removed KVPair
		else {
			System.out.println("Rectangle removed: " + result.toString());
		}
	}

	/**
	 * Removes a rectangle with the specified coordinates if available. If not an
	 * error message is printed to the console.
	 * 
	 * @param x x-coordinate of the rectangle to be removed
	 * @param y x-coordinate of the rectangle to be removed
	 * @param w width of the rectangle to be removed
	 * @param h height of the rectangle to be removed
	 */
	public void remove(int x, int y, int w, int h) {
		// Create a new Rectangle object with the specified coordinates and dimensions
		Rectangle R = new Rectangle(x, y, w, h);

		// Check if the rectangle is valid (has positive width and height), if not print
		// an error message
		if (!R.Is_Valid()) {
			System.out.println("Rectangle rejected: (" + R.toString() + ')');
			return;
		}

		// Remove the KVPair with the specified Rectangle object from the SkipList
		KVPair<String, Rectangle> result = list.removeByValue(R);

		// If the KVPair was not found in the SkipList, print an error message
		if (result == null) {
			System.out.println("Rectangle not removed: (" + R.toString() + ')');
		}
		// Otherwise, print a success message with the removed KVPair
		else {
			System.out.println("Rectangle removed: " + result.toString());
		}
	}

	/**
	 * Displays all the rectangles inside the specified region. The rectangle must
	 * have some area inside the area that is created by the region, meaning,
	 * Rectangles that only touch a side or corner of the region specified will not
	 * be said to be in the region. You will need a SkipList Iterator for this
	 * 
	 * @param x x-Coordinate of the region
	 * @param y y-Coordinate of the region
	 * @param w width of the region
	 * @param h height of the region
	 */
	public void regionsearch(int x, int y, int w, int h) {
		// Check if the region is valid (has positive width and height), if not print an
		// error message
		if (w <= 0 || h <= 0) {
			System.out.println("Rectangle rejected:(" + x + "," + y + "," + w + "," + h + "):");
			return;
		}

		// Create a new Rectangle object with the specified coordinates and dimensions
		Rectangle R = new Rectangle(x, y, w, h);

		// Iterate over all the KVPair entries in the SkipList
		Iterator<KVPair<String, Rectangle>> itr;
		System.out.println("Rectangles intersecting region (" + R.toString() + "):");
		for (itr = list.iterator(); itr.hasNext();) {
			KVPair<String, Rectangle> p = itr.next();
			String name = p.getKey();
			Rectangle R1 = p.getValue();

			// Check if the Rectangle intersects with the specified region and is not
			// identical to it, if so print its name and coordinates
			if (R1.intersecting(R) && R1 != R) {
				System.out.println("(" + name + ',' + R1.toString() + ")");
			}
		}
	}

	/**
	 * Prints out all the rectangles that Intersect each other by calling the
	 * SkipList method for intersections. You will need to use two SkipList
	 * Iterators for this
	 * 
	 * Finds all pairs of rectangles in the SkipList that intersect with each other
	 * and prints their names and coordinates.
	 */
	public void intersections() {
		// Iterate over all the KVPair entries in the SkipList
		Iterator<KVPair<String, Rectangle>> itr1;
		Iterator<KVPair<String, Rectangle>> itr2;
		System.out.println("Intersections pairs:");
		for (itr1 = list.iterator(); itr1.hasNext();) {
			KVPair<String, Rectangle> p1 = itr1.next();
			String name1 = p1.getKey();
			Rectangle R1 = p1.getValue();

			// Iterate over all the KVPair entries in the SkipList again to compare with the
			// first rectangle
			for (itr2 = list.iterator(); itr2.hasNext();) {
				KVPair<String, Rectangle> p2 = itr2.next();
				String name2 = p2.getKey();
				Rectangle R2 = p2.getValue();

				// Check if the two rectangles intersect with each other and are not identical,
				// if so print their names and coordinates
				if (R1.intersecting(R2) && R1 != R2) {
					System.out.println(p1.toString() + '|' + p2.toString());
				}
			}
		}
	}

	/**
	 * Prints out all the rectangles with the specified name in the SkipList. This
	 * method will delegate the searching to the SkipList class completely.
	 * 
	 * @param name name of the Rectangle to be searched for
	 *
	 *             Searches for all rectangles in the SkipList that have the
	 *             specified name and prints their names and coordinates.
	 * 
	 *             If the name is invalid (does not start with a letter), an error
	 *             message is printed to the console.
	 */
	public void search(String name) {
		// Search for all KVPair entries in the SkipList that have the specified name
		ArrayList<KVPair<String, Rectangle>> result = list.search(name);

		// Check if the name is valid (starts with a letter), if not print an error
		// message
		if (!check_name(name)) {
			System.out.println("rejected name :" + name);
			return;
		}

		// If no KVPair entries were found, print an error message
		if (result == null || result.isEmpty()) {
			System.out.println("Rectangle not found: " + name);
		}
		// Otherwise, print the names and coordinates of all the rectangles found
		else {
			System.out.println("Rectangles found:");
			for (int i = 0; i < result.size(); i++) {
				KVPair<String, Rectangle> pair = result.get(i);
				String rectName = pair.getKey();
				Rectangle rect = pair.getValue();

				System.out.println('(' + rectName + ',' + rect.getX() + ',' + rect.getY() + ',' + rect.getWidth() + ','
						+ rect.getHeight() + ')');
			}
		}
	}

	/**
	 * Prints out a dump of the SkipList which includes information about the size
	 * of the SkipList and shows all of the contents of the SkipList. This will all
	 * be delegated to the SkipList. Dumps the contents of the SkipList to the
	 * console.
	 */
	public void dump() {
		// Call the dump method of the SkipList to print the contents to the console
		list.dump();
	}

}
