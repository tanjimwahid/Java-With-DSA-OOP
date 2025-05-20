
import java.util.*;

public class typeConCast {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Your Float Value: ");
        // type casting
        float name = sc.nextFloat();
        int realName = (int) name;

        System.out.print("Enter the radius of the circle: ");
        int radius = sc.nextInt();

        // type conversion
        double area = radius * 3.14 * radius;
        int ares = (int) area;
        System.out.print(realName);
        System.out.print(", your circle's area is: ");
        System.out.print(ares);
    }
}
