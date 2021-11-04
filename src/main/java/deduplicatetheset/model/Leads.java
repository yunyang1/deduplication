package deduplicatetheset.model;

import java.util.List;
//POJO

public class Leads {
    private List<UserInfo> leads;

    public List<UserInfo> getLeads() {
        return leads;
    }

    public void setLeads(List<UserInfo> leads) {
        this.leads = leads;
    }

    @Override
    public String toString() {
        return "Leads{" +
                "leads=" + leads +
                '}';
    }
}
