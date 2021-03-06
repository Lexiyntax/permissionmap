package net.ashtech.permissionmap;

/**
 * Generates ':' separated four-element permission groups
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class PermGenerator {
    
    /**
     * @return Simple always the same basic test set
     */
    public String[] staticSet() {
        String[] s = new String[4];
        s[0] = "a:b:c:d";
        s[1] = "a:*:d:c";
        s[2] = "b:a:c:d";
        s[3] = "b:b:*:c";
        return s;
    }
    
    /**
     * @return larger test set that is a worst case for scanning implementations
     */
    public String[] bigBadSet() {
        String[] s= new String[1000];
        for (int i = 0; i < 999; i++) {
            s[i] = "xray:xray:xray:xray";
        }
        s[999] = "alpha:bravo:charlie:delta";
        return s;
    }
    
}
