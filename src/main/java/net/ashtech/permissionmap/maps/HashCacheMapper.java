package net.ashtech.permissionmap.maps;

import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

/**
 * Caches results of basic loop mapping for quicker results if we have seen this
 * before; does positive or negative caching by hashing both the target and the
 * full permission list so any change is detected
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class HashCacheMapper implements PermInterface {
    
    Logger log = Logger.getLogger(HashCacheMapper.class.getName());
    
    //in production this may be in redis or another type of persistent store
    private HashMap<String, Boolean> cache = new HashMap<>();
    
    //we could of course use this caching implentation on any mapper
    private BasicLoopMapper mapper = new BasicLoopMapper();

    //digest generator
    private MessageDigest dg;
    
    //we declare this globally so we can reuse the same one and reset it so it 
    //  cleans up instead of growing
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    
    Base64.Encoder b64encoder = Base64.getEncoder();
    
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
            Map.Entry<String, Boolean> cacheEntry = (Map.Entry)cacheI.next();
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
            String digest = b64encoder.encodeToString(dg.digest(bos.toByteArray()));
            
            if (cache.containsKey(digest)) {
                //if we have a match in the cache, we're done
                log.fine("Cache hit");
                return cache.get(digest);
            }
            else {
                result = mapper.auth(target, permissions);
                log.fine("Cache miss");
                cache.put(digest, result);
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
