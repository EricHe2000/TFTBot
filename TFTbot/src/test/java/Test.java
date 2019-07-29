import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;

public class Test {
	public static void main(final String[] args) throws Exception {
		Robot r = new Robot();
		r.delay(2000);
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		System.out.print("x: " + x);
		System.out.print("y: " + y);
	}
}