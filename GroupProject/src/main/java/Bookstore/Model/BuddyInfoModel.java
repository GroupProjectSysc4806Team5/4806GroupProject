package Bookstore.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BuddyInfoModel {

    private Long id;
    private String name;
    private String phoneNumber;

    @JsonIgnore
    private AddressBookModel addressBookModel;

    public BuddyInfoModel(){	}


    @Id
    @GeneratedValue
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public BuddyInfoModel(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @ManyToOne
    public AddressBookModel getAddressBookModel(){ return this.addressBookModel; }
    public void setAddressBookModel(AddressBookModel addressBookModel){ this.addressBookModel = addressBookModel; }
    public void removeAddressBookModel(){this.addressBookModel = null;}


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public boolean equals(BuddyInfoModel buddy){
        if (this.name.equals(buddy.getName()) && this.phoneNumber.equals((buddy.getPhoneNumber()))){
            return true;
        }
        return false;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String toString() {
        return "name: " + name + " \n" + "Phone Number: " + phoneNumber + "\n";
    }
}