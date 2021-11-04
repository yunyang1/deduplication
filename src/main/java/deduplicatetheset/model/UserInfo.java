package deduplicatetheset.model;
// POJO

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class UserInfo {
    private String _id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private Date entryDate;
    private Integer idx;
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", entryDate='" + new SimpleDateFormat(DATE_FORMAT).format(entryDate) + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(_id, userInfo._id) && Objects.equals(email, userInfo.email) && Objects.equals(firstName, userInfo.firstName) && Objects.equals(lastName, userInfo.lastName) && Objects.equals(address, userInfo.address) && Objects.equals(entryDate, userInfo.entryDate) && Objects.equals(idx, userInfo.idx);
    }

    public UserInfo reconcile(UserInfo other){
        if (this.getEntryDate().equals(other.getEntryDate())){
            if (this.getIdx().equals(other.getIdx())){
                return this;
            }else{
                return this.getIdx().compareTo(other.getIdx()) > 0 ? this : other;
            }
        }else{
            return this.getEntryDate().compareTo(other.getEntryDate()) > 0 ? this : other;
        }
    }
}
