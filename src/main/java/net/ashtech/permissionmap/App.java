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
        
        System.out.println("Hash Cache Test");
        doHashCacheTest();

        System.out.println("Token Tree Test");
        doTokenTreeTest();

    }
    
    private static void doBasicTest() {
        System.out.println(tester.doBasicTest());
    }
    
    private static void doHashCacheTest() {
        System.out.println(tester.doHashCacheTest());
    }
    
    private static void doTokenTreeTest() {
        System.out.println(tester.doTreeMapperTest());
    }
    
}
