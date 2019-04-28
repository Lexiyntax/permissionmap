package net.ashtech.permissionmap.maps;

/**
 * Defines interface for permission map testing implementations
 * 
 * All implementations are to support the single wildcard '*' for all only when
 * exclusively in the string; any other token string to be assumed explicit
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public interface PermInterface {
    
    /**
     * @param target target permission to test
     * @param permissions permission set to apply against
     * @return whether the target is allowed to access based on the given permissions
     */
    public boolean auth(final String target, final String[] permissions);
    
}
