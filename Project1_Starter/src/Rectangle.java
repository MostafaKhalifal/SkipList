/* The Rectangle class represents a rectangle with specified dimensions and
 * coordinates.
 */
public class Rectangle {
// Declaring variables for Rectangle dimensions
	private int x;
	private int y;
	private int width;
	private int height;

	/**
	 * 
	 * Constructs a rectangle with the specified dimensions and coordinates.
	 * 
	 * @param x the x-coordinate of the rectangle
	 * @param y the y-coordinate of the rectangle
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 */
	public Rectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

// Getters and setters for Rectangle properties
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public void setHeight(int h) {
		this.height = h;
	}

	/**
	 * 
	 * Checks whether the rectangle is valid or not.
	 * 
	 * @return true if the rectangle is valid, false otherwise
	 */
	public boolean Is_Valid() {
		try {
			if ((x >= 0 && x + width <= 1024) && (y >= 0 && y + height <= 1024) && (width > 0) && (height > 0)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
// Printing stack trace in case of any exceptions
			e.printStackTrace();
		}
// Returning false if the Rectangle is not valid
		return false;
	}

	/**
	 * 
	 * Checks whether the rectangle has a valid region or not.
	 * 
	 * @return true if the rectangle has a valid region, false otherwise
	 */
	public boolean regionsearch() {
		if (width > 0 && height > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Checks whether the rectangle intersects with another rectangle or not.
	 * 
	 * @param Rec2 the rectangle to check for intersection with
	 * @return true if the rectangles intersect, false otherwise
	 */
	public boolean intersecting(Rectangle Rec2) {
// Check if the rectangles intersect by checking if their x, y, width, and height values overlap
		return x < Rec2.x + Rec2.width && x + width > Rec2.x && y < Rec2.y + Rec2.height && y + height > Rec2.y;
	}

	/**
	 * 
	 * Returns a string representation of the rectangle in the format
	 * "x,y,width,height".
	 * 
	 * @return a string representation of the rectangle
	 */
	public String toString() {
		return Integer.toString(x) + ',' + Integer.toString(y) + ',' + Integer.toString(width) + ','
				+ Integer.toString(height);
	}

	/**
	 * 
	 * Checks whether this rectangle is equal to another object.
	 * 
	 * @param o the object to compare to
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		Rectangle R = (Rectangle) o;
		if (x == R.x && y == R.y && width == R.width && height == R.height)
			return true;
		return false;
	}
}
