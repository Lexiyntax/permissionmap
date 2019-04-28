package net.ashtech.permissionmap;

/**
 * Permission Map Handling
 * 
 * Testing various methodologies for handling permission map handling efficiently
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class App {
    
    private static PermTester tester = new PermTester();
    
    public static void main(String[] args) {
        System.out.println("Basic Parsing Test");
        doBasicTest();
    }
    
    private static void doBasicTest() {
        System.out.println(tester.doBasicTest());
    }
    
}
