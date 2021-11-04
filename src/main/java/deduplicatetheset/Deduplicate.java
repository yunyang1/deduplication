package deduplicatetheset;

import deduplicatetheset.model.UserInfo;

import java.util.*;

/**
 *
 * Driver Class for the Deduplicate
 *
 */
public class Deduplicate {

    private UnionSet<String> unionSet = new UnionSet<>();
    private Map<String, UserInfo> rootIDUserinfo = new HashMap<>();

    public boolean removeNonRootUserInfo(String root, String possibleRoot) {
        if (!possibleRoot.equals(root) && rootIDUserinfo.containsKey(possibleRoot)) {
            rootIDUserinfo.remove(possibleRoot);
            return true;
        }
        return false;
    }

    public void printRootUserInfo() {
        System.out.println("Reconciled result: ");
        for (Map.Entry<String, UserInfo> ele : rootIDUserinfo.entrySet()) {
            System.out.println("\tUnionSet ID: " + ele.getKey() + " => Reconciled User Info: " + ele.getValue());
        }
    }

    private void updateRootIDUserinfo(String root, UserInfo newUserInfo){
        UserInfo rootUserInfo = rootIDUserinfo.get(root);
        if (rootUserInfo!= newUserInfo) {
            System.out.println("Found duplicated user info:");
            UserInfo reconciledUserInfo = rootUserInfo.reconcile(newUserInfo);
            if (reconciledUserInfo != rootUserInfo) {
                System.out.println(String.format("\tUpdated user info of union set id %s with user info:\n\t%s", root, newUserInfo));
                printFieldChanges(rootIDUserinfo.get(root), reconciledUserInfo);
                rootIDUserinfo.put(root, reconciledUserInfo);
            }
        }
    }

    private void printFieldChanges(UserInfo from, UserInfo to){
        if (!from.getId().equals(to.getId())) {
            System.out.println(String.format("\t\tField (%s) changed from (%s) to (%s)", "ID", from.getId(), to.getId()));
        }
        if (!from.getEmail().equals(to.getEmail())) {
            System.out.println(String.format("\t\tField (%s) changed from (%s) to (%s)", "EMAIL", from.getEmail(), to.getEmail()));
        }
        if (!from.getFirstName().equals(to.getFirstName())) {
            System.out.println(String.format("\t\tField (%s) changed from (%s) to (%s)", "FIRST NAME", from.getFirstName(), to.getFirstName()));
        }
        if (!from.getLastName().equals(to.getLastName())) {
            System.out.println(String.format("\t\tField (%s) changed from (%s) to (%s)", "LAST NAME", from.getLastName(), to.getLastName()));
        }
        if (!from.getAddress().equals(to.getAddress())) {
            System.out.println(String.format("\t\tField (%s) changed from (%s) to (%s)", "ADDRESS", from.getAddress(), to.getAddress()));
        }
        if (!from.getEntryDate().equals(to.getEntryDate())) {
            System.out.println(String.format("\t\tField (%s) changed from (%s) to (%s)", "ENTRYDATE", from.getEntryDate(), to.getEntryDate()));
        }
    }

    private String findRoot(UserInfo userInfo){
        // find the root in the union set
        String idRoot = unionSet.find(userInfo.getId());
        String emailRoot = unionSet.find(userInfo.getEmail());
        String root = null;
        if (idRoot.equals(emailRoot)) {
            // already in a union
            root = idRoot;
        } else {
            // union
            root = unionSet.union(idRoot, emailRoot);
            // reconcile old root with new root
            if (!root.equals(idRoot)) {
                updateRootIDUserinfo(root, rootIDUserinfo.remove(idRoot));
            } else {
                updateRootIDUserinfo(root, rootIDUserinfo.remove(emailRoot));
            }
        }
        return root;
    }

    private void addToUnionSet(UserInfo userInfo){
        if (!unionSet.contains(userInfo.getId())) {
            unionSet.addNewRoot(userInfo.getId());
            rootIDUserinfo.put(userInfo.getId(), userInfo);
        }
        if (!unionSet.contains(userInfo.getEmail())) {
            unionSet.addNewRoot(userInfo.getEmail());
            rootIDUserinfo.put(userInfo.getEmail(), userInfo);
        }
    }

    public Collection<UserInfo> reconcile(List<UserInfo> rawUserInfo) {
        for (int i = 0; i < rawUserInfo.size(); i++) {
            UserInfo userInfo = rawUserInfo.get(i);
            System.out.print("====================================================================");
            System.out.println("====================================================================");
            System.out.println("Reconciling: \n\t" + userInfo);
            userInfo.setIdx(i);

            // init
            addToUnionSet(userInfo);

            //find root
            String root = findRoot(userInfo);

            // reconcile current user info with the user info which associate with root
            updateRootIDUserinfo(root, userInfo);

            printRootUserInfo();
            System.out.print("====================================================================");
            System.out.println("====================================================================\n\n");
        }
        return rootIDUserinfo.values();
    }
}
