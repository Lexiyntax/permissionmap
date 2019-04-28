package net.ashtech.permissionmap;

import net.ashtech.permissionmap.maps.BasicLoopMapper;

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

        return result;
    }
    
}
