import java.util.HashMap;

public class IterTest {

    public static void main(String[] args) {
        HashMap<String, Integer> testHashMap = new HashMap<String, Integer>();
        testHashMap.put("num1",1);
        testHashMap.put("num2",2);
        testHashMap.put("num3",3);

        testHashMap.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
            System.out.println("*******");
        });
    }
}