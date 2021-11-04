package deduplicatetheset;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import deduplicatetheset.model.Leads;
import deduplicatetheset.model.UserInfo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0){
            System.err.println("please input file path");
            return;
        }
        JsonReader reader = new JsonReader(new FileReader(args[0]));
        Leads infos = new GsonBuilder().setDateFormat(UserInfo.DATE_FORMAT)
                .create().fromJson(reader, new TypeToken<Leads>() {}.getType());
        System.out.print("====================================================================");
        System.out.print("input records");
        System.out.println("====================================================================");
        for (UserInfo userInfo: infos.getLeads()){
            System.out.println(userInfo);
        }
        System.out.print("=========================================================================");
        System.out.print("end");
        System.out.println("=========================================================================\n\n");
        Deduplicate deduplicate = new Deduplicate();
        Collection<UserInfo> deduplicatedUserInfo = deduplicate.reconcile(infos.getLeads());

        System.out.print("====================================================================");
        System.out.print("Final output");
        System.out.println("====================================================================");
        for (UserInfo userInfo: deduplicatedUserInfo){
            System.out.println(userInfo);
        }
        System.out.print("=========================================================================");
        System.out.print("end");
        System.out.println("=========================================================================\n\n");
    }

}
