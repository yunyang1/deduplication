package deduplicatetheset;

import java.util.HashMap;
import java.util.Map;

public class UnionSet<A> {
    private Map<A, A> parent;
    private Map<A, Integer> size;

    public UnionSet(){
        parent = new HashMap<>();
        size = new HashMap<>();
    }

    /**
     * find the root and compress the root
     * @param ele: element in the union set
     * @return root of the union
     */
    public A find(A ele){
        while(!parent.get(ele).equals(ele)){
            // find and compress
            parent.put(ele, parent.get(parent.get(ele)));
            ele = parent.get(ele);
        }
        return ele;
    }

    /**
     * union two set and return new root
     * @param left :
     * @param right :
     * @return new root
     */
    public A union(A left, A right){
        A root_left = parent.get(left);
        A root_right = parent.get(right);

        if (size.get(root_left) < size.get(root_right)){
            parent.put(root_left, root_right);
            size.put(root_right, size.get(root_left) + size.get(root_right));
            return root_right;
        }
        else{
            parent.put(root_right, root_left);
            size.put(root_left, size.get(root_left) + size.get(root_right));
            return root_left;
        }
    }

    /**
     * check if element is in the set
     * @param ele
     * @return whether element is in the set
     */
    public boolean contains(A ele){
        return parent.containsKey(ele);
    }


    /**
     * add a new root in the union set
     * @param ele new root
     */
    public void addNewRoot(A ele){
        parent.put(ele, ele);
        size.put(ele,1);
    }

}
