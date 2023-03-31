import java.util.HashMap;

public class RectangleManager {
    private HashMap<String, Rectangle> rectangles;

    public RectangleManager() {
        rectangles = new HashMap<String, Rectangle>();
    }

    public void insert(String name, int x, int y, int w, int h) {
       
        if (!name.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
            System.out.println("Rectangle rejected: (" + name + ", " + x + ", " + y + ", " + w + ", " + h + ")");
            return;
        }

      
        if (w <= 0 || h <= 0) {
            System.out.println("Rectangle rejected: (" + name + ", " + x + ", " + y + ", " + w + ", " + h + ")");
            return;
        }

       
        if (x < 0 || x + w > 1024 || y < 0 || y + h > 1024) {
            System.out.println("Rectangle rejected: (" + name + ", " + x + ", " + y + ", " + w + ", " + h + ")");
            return;
        }

       
        Rectangle rectangle = new Rectangle(x, y, w, h);
        rectangles.put(name, rectangle);

        System.out.println("Rectangle inserted: (" + name + ", " + x + ", " + y + ", " + w + ", " + h + ")");
    }

    public static void main(String[] args) {
        RectangleManager rectangleManager = new RectangleManager();
        rectangleManager.insert("a", 1, 0, 5, 4); 
        rectangleManager.insert("a", -1, -1, 2, 4); //للتجربه بس .. المفروض يكون input من اليوزر
    }
}

class Rectangle {
    int x;
    int y;
    int w;
    int h;

    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}