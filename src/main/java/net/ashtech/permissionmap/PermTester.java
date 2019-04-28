package net.ashtech.permissionmap;

import net.ashtech.permissionmap.maps.BasicLoopMapper;
import net.ashtech.permissionmap.maps.HashCacheMapper;

/**
 * Permission mapping test implementation
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class PermTester {
    
    private TargetGenerator tgen = new TargetGenerator();
    private PermGenerator pgen = new PermGenerator();

    /**
     * Pretty format a target and permission set
     * @param t target to print
     * @param ps permission set to print
     */
    private String formatSet(String t, String[] ps) {
        String s = t + "\n================\n";
        for (String p : ps) {
            s += p + "\n";
        }
        s += "================\n";
        
        return s;
    }

    /**
     * Pretty format the results of an allow test
     * @param target
     * @param permset
     * @return 
     */
    private String formatAllowTest(String target, boolean testresult) {
        String s = "----------------\n";
        s += "Allow: " + testresult + "\n" + target + "\n";
        return s += "----------------\n";
    }
    
    /**
     * Smoke tests basic test cases against the basic loop mapper
     * 
     * @return pretty string output of basic testing results
     */
    public String doBasicTest() {
        String[] permset = pgen.staticSet();
        BasicLoopMapper mapper = new BasicLoopMapper();
        String target = "";
        String result = "";
        
        result += formatSet(target, permset);
        
        target = tgen.basicTargetTopMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));
        
        target = tgen.basicTargetBottomMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));

        target = tgen.basicTargetNoMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));
        
        target = tgen.basicTargetWildCardMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));

        return result;
    }
    
    /**
     * Smoke tests hash cache mapper
     * 
     * @return pretty string output of hash cache mapper results
     */
    public String doHashCacheTest() {
        String[] permset = pgen.staticSet();
        HashCacheMapper mapper = new HashCacheMapper();
        String target = "";
        String result = "";
        
        result += formatSet(target, permset);

        long startTime = System.currentTimeMillis();
        
        target = tgen.basicTargetTopMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));
        
        target = tgen.basicTargetBottomMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));

        target = tgen.basicTargetNoMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));
        
        target = tgen.basicTargetWildCardMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));

        long endTime = System.currentTimeMillis();
        
        result += "Without cache took " + (endTime - startTime) + " milliseconds\n";
        
        //and again to test caching!
        
        startTime = System.currentTimeMillis();
        
        target = tgen.basicTargetTopMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));
        
        target = tgen.basicTargetBottomMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));

        target = tgen.basicTargetNoMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));
        
        target = tgen.basicTargetWildCardMatch();
        result += formatAllowTest(target, mapper.auth(target,permset));

        endTime = System.currentTimeMillis();
        
        result += "With cache took " + (endTime - startTime) + " milliseconds\n";
        
        //dump the cache for debugging
        
        mapper.printCache();
        
        return result;
    }
    
}
