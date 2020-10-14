import java.util.Arrays;

public class Example {

    public static void main(String[] args) {
        String phrase1 = "CSE143 is fun!";
        String phrase2 = "CSE143isfun!";
        
        // Split based on whitespace
        String[] split1 = phrase1.split("\\s+");
        String[] split2 = phrase2.split("\\s+");
    
        // Print out the arrays to see their contents
        System.out.println(Arrays.toString(split1)); // size = 3
        System.out.println(Arrays.toString(split2)); // size = 1
    }
}