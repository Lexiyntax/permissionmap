package net.ashtech.permissionmap;

/**
 * Generates ':' separated four element permission targets to test against
 * permission groups
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class TargetGenerator {
    
    /**
     * @return Basic single target matching the top of the static set
     */
    public String basicTargetTopMatch() {
        return "a:b:c:d";
    }
    
    /**
     * @return Basic single target matching the bottom of the static set
     */
    public String basicTargetBottomMatch() {
        return "b:b:*:c";
    }
    
    /**
     * @return Basic single target matching nothing in the static set
     */
    public String basicTargetNoMatch() {
        return "x:x:x:x";
    }
    
    /**
     * @return Basic single target with wildcard non-exact match against static set
     */
    public String basicTargetWildCardMatch() {
        return "a:x:d:c";
    }
    
}
