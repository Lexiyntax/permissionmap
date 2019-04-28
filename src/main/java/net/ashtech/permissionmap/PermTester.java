package net.ashtech.permissionmap;

import net.ashtech.permissionmap.maps.BasicLoopMapper;
import net.ashtech.permissionmap.maps.HashCacheMapper;
import net.ashtech.permissionmap.maps.PermInterface;
import net.ashtech.permissionmap.maps.TokenTreeMapper;

/**
 * Permission mapping test implementation
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class PermTester {
    
    private TargetGenerator tgen = new TargetGenerator();
    private PermGenerator pgen = new PermGenerator();

    int iterations = 10000;
    
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
        
        long startTime = System.currentTimeMillis();
        result += testBasicWithIterations(mapper, permset);
        long endTime = System.currentTimeMillis();
        
        result += "Static Set Basic test mapper took " + (endTime - startTime) + " milliseconds\n";
        
        startTime = System.currentTimeMillis();
        result += testBigBadWithIterations(mapper, pgen.bigBadSet());
        endTime = System.currentTimeMillis();
        
        result += "Big Bad Set Basic test mapper took " + (endTime - startTime) + " milliseconds\n";
        
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
        result += testBasicWithIterations(mapper, permset);
        long endTime = System.currentTimeMillis();
        
        result += "Static Set With caching took " + (endTime - startTime) + " milliseconds\n";

        startTime = System.currentTimeMillis();
        result += testBigBadWithIterations(mapper, pgen.bigBadSet());
        endTime = System.currentTimeMillis();
        
        result += "Big Bad Set With caching took " + (endTime - startTime) + " milliseconds\n";

        
        //dump the cache for debugging
        
        mapper.printCache();
        
        return result;
    }
    
    /**
     * Smoke tests tree mapper
     * 
     * @return pretty string output of tree mapper results
     */
    public String doTreeMapperTest() {
        String[] permset = pgen.staticSet();
        TokenTreeMapper mapper = new TokenTreeMapper();
        String result = "";
        
        long startTime = System.currentTimeMillis();
        result += testBasicWithIterations(mapper, permset);
        long endTime = System.currentTimeMillis();
        
        result += "Static Set with Tree Mapping took " + (endTime - startTime) + " milliseconds\n"
                
        startTime = System.currentTimeMillis();
        result += testBigBadWithIterations(mapper, pgen.bigBadSet());
        endTime = System.currentTimeMillis();
        
        result += "Big Bad Set with Tree Mapping took " + (endTime - startTime) + " milliseconds\n"
                
        return result;
    }
    
    private String testBasicWithIterations (PermInterface mapper, String[] permset) {
        String result = "";
        
        String t1 = tgen.basicTargetTopMatch();
        result += formatAllowTest(t1, mapper.auth(t1,permset));
        
        String t2 = tgen.basicTargetBottomMatch();
        result += formatAllowTest(t2, mapper.auth(t2,permset));

        String t3 = tgen.basicTargetNoMatch();
        result += formatAllowTest(t3, mapper.auth(t3,permset));
        
        String t4 = tgen.basicTargetWildCardMatch();
        result += formatAllowTest(t4, mapper.auth(t4,permset));

        for (int i = 0; i < iterations; i++) {
            mapper.auth(t1,permset);
            mapper.auth(t2,permset);
            mapper.auth(t3,permset);
            mapper.auth(t4,permset);
        }
        
        return result;
    }
    
    private String testBigBadWithIterations (PermInterface mapper, String[] permset) {
        String result = "";
        
        String t1 = tgen.bigBadSetEndMatch();
        result += formatAllowTest(t1, mapper.auth(t1, permset));
        
        String t2 = tgen.bigBadSetNoMatch();
        result += formatAllowTest(t2, mapper.auth(t2, permset));
        
        for (int i = 0; i < iterations; i++) {
            mapper.auth(t1, permset);
            mapper.auth(t2, permset);
        }
        
        return result;
    }
    
}
