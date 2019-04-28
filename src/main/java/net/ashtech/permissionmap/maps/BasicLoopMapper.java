package net.ashtech.permissionmap.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Basic for loop implementation
 * 
 * Brute force for loop implementation
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class BasicLoopMapper implements PermInterface {

    private ParseUtil parse = new ParseUtil();
    private Logger log = Logger.getLogger(BasicLoopMapper.class.getName());
    
    @Override
    public boolean auth(String target, String[] permissions) {
        try {
            
            String[] t = parse.tokenize(target);
            
            List<String[]> permlist = new ArrayList(permissions.length);
            for (String p : permissions) {
                permlist.add(parse.tokenize(p));
            }
            
            for (String[] perm : permlist) {
                boolean itmatch = true;
                for (int i = 0; i < 4; i++) {
                    if (t[i].equals(perm[i]) || "*".equals(perm[i])) {
                        log.log(Level.FINE, t[i] + perm[i] + " true");
                    }
                    else {
                        log.log(Level.FINE, t[i] + perm[i] + " false");
                        itmatch = false;
                    }
                }
                if (itmatch) {
                    return true;
                }
            }
            //if we get here there were no true permission matches
            return false;
            
        }
        catch (InvalidPermSetException e) {
            log.log(Level.SEVERE, null, e);
            //on error, deny access
            return false;
        }
    }
    
    
    
}
