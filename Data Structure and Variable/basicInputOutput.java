
import java.util.*;

public class BasicInputOutput {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Your Name: ");
        String name = sc.nextLine();

        System.out.print("Enter the radius of the circle: ");
        int radius = sc.nextInt();

        double area = radius * Math.PI * radius;

        System.out.print(name);
        System.out.print(", your circle's area is: ");
        System.out.print(area);
    }
}
