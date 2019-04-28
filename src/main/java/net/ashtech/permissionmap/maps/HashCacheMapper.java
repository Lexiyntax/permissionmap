package net.ashtech.permissionmap.maps;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Caches results of basic loop mapping for quicker results if we have seen this
 * before; we cache previous 'true' actions assuming we want authorized users
 * we've seen before to have the quickest access
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class HashCacheMapper implements PermInterface {
    
    Logger log = Logger.getLogger(HashCacheMapper.class.getName());
    
    //in production we wouldn't need to store the target string, allowing future
    //  lookup timing optimization via other implementations (i.e. in production
    //  this may be in redis or another type of store)
    private HashMap cache = new HashMap<String, String>();
    
    //we could of course use this caching implentation on any mapper
    private BasicLoopMapper mapper = new BasicLoopMapper();

    //digest generator
    private MessageDigest dg;
    
    //we declare this globally so we can reuse the same one and reset it so it 
    //  cleans up instead of growing
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    public HashCacheMapper() {
        try {
            this.dg = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Java install is missing MD5 security algorithm guaranteed to be there by the java specification, this should not be possible");
        }
    }
    
    /**
     * Print out a dump of what's in the cache
     */
    public void printCache() {
        Iterator cacheI = cache.entrySet().iterator();
        while (cacheI.hasNext()) {
            Map.Entry<String, String> cacheEntry = (Map.Entry)cacheI.next();
            System.out.println(
                    cacheEntry.getKey() + 
                    ": " + 
                    cacheEntry.getValue()
            );
        }
    }

    @Override
    public boolean auth(String target, String[] permissions) {
 
        //default to false
        boolean result = false;
        
        try {
            bos.reset();
            bos.write(target.getBytes("UTF-8"));
            for (String perm : permissions) {
                bos.write(perm.getBytes("UTF-8"));
            }
            
            //we encode to base64 as byte[] can be dups in data structures due
            //  to the way it equality tests
            String digest = Base64.encode(dg.digest(bos.toByteArray()));
            
            if (cache.containsKey(digest)) {
                //if we have a match in the cache, we're done; see cache management
                //  comment below regarding why this bit is here
                //result = true;
                log.fine("Cache hit");
                return true;
            }
            else {
                result = mapper.auth(target, permissions);
            }

            //this step is located here for cache management; fall through instead
            //  of returning on true above if we want to do something to tell a 
            //  persistent cache layer that we had a 'hit', current implementation 
            //  only gets here if we were not in cache already
            if (result) {
                log.fine("Cache miss");
                cache.put(digest, target);
            }
            
        } 
        catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, null, e);
            //if cache handling fails, fallback to mapper
            result = mapper.auth(target, permissions);
        }
        catch (IOException e) {
            log.warning("Cache digest byte handling failed");
            result = mapper.auth(target, permissions);
        }
        catch (BufferOverflowException e) {
            log.warning("HashCacheMapper buffer was too small");
            result = mapper.auth(target, permissions);
        }
        
        return result;
    }
    
}
