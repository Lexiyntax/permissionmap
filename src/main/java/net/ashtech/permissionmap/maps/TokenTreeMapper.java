package net.ashtech.permissionmap.maps;

import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This mapper builds a token tree based on property sets in the target
 * permission map to reduce the number of tests needed in large permission sets
 *
 * @author Daniel E. Markle <dmarkle@ashtech.net>
 */
public class TokenTreeMapper implements PermInterface {

    @Override
    public boolean auth(String target, String[] permissions) {
        HashMap<String, HashMap> treetop = new HashMap<>();
        for (String permstring : permissions) {
            String[] permset = permstring.split(":");
            buildTreeBranches(treetop, permset, 0);
        }
        
        String[] targetset = target.split(":");
        return scanTreeForMatch(treetop, targetset, 0);
    }
    
    private void buildTreeBranches(HashMap<String, HashMap> branch, String[] leaves, int depth) {
        if (depth < leaves.length) {
            if (branch.containsKey(leaves[depth])) {
                buildTreeBranches(branch.get(leaves[depth]), leaves, depth + 1);
            }
            else {
                branch.put(leaves[depth], new HashMap<String, HashMap>());
                buildTreeBranches(branch.get(leaves[depth]), leaves, depth + 1);
            }
        }
    }
    
    private boolean scanTreeForMatch(HashMap<String, HashMap> branch, String[] targetset, int depth) {
        if (depth < targetset.length) {
            if (branch.containsKey(targetset[depth])) {
                return scanTreeForMatch(branch.get(targetset[depth]), targetset, depth + 1);
            }
            else if (branch.containsKey("*")) {
                return scanTreeForMatch(branch.get("*"), targetset, depth + 1);
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    }
    
    private void printTree(HashMap<String, HashMap> tree, int depth) {
        if (! tree.isEmpty()) {
            Iterator branchiter = tree.entrySet().iterator();
            while (branchiter.hasNext()) {
                Map.Entry<String, HashMap> branch = (Map.Entry)branchiter.next();
                System.out.println(Strings.repeat(" ", depth) + branch.getKey());
                printTree(branch.getValue(), depth + 1);
            }
        }
    }
    
}
