import java.io.*;

public class Driver { 
    public static void main(String [] args){
        File t1 = new File("test1.txt");
        File t2 = new File("test2.txt");
        Polynomial p1 = new Polynomial(t1);
        Polynomial p2 = new Polynomial(t2);
        Polynomial result = p1.multiply(p2);
        Polynomial result2 = p1.add(p2);
        result.saveToFile("result.txt");
        result2.saveToFile("result2.txt");
        System.out.println(p1.evaluate(2.0));
        System.out.println("Has root at 1: " + p1.hasRoot(1));
    } 
} 