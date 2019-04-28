package net.ashtech.permissionmap.maps;

/**
 * Utility functions common to PermInterface implementations
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class ParseUtil {
    
    public String[] tokenize(String s) throws InvalidPermSetException {
        String[] ts = s.split(":");
        if (ts.length != 4) {
            throw new InvalidPermSetException("Bad length of " + ts.length + " expected 4");
        }
        return ts;
    }
    
}
