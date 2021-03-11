package Bookstore.Model;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
import static javax.persistence.CascadeType.ALL;

@Entity
public class AddressBookModel {

    private Long id;
    private List<BuddyInfoModel> buddiesList;

    public AddressBookModel(){
        this.buddiesList = new ArrayList<BuddyInfoModel>();
    }

    @Id
    @GeneratedValue
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public void addBuddy(BuddyInfoModel buddyInfoModel){

        this.buddiesList.add(buddyInfoModel);
        buddyInfoModel.setAddressBookModel(this);

    }

    public BuddyInfoModel addBuddy(String name, String phoneNumber){

        BuddyInfoModel buddyInfoModel = new BuddyInfoModel(name, phoneNumber);
        this.buddiesList.add(buddyInfoModel);
        buddyInfoModel.setAddressBookModel(this);
        return buddyInfoModel;

    }

    public void removeBuddy(BuddyInfoModel buddyInfoModel){

        boolean buddyFound = false;

        BuddyInfoModel foundBuddy = null;

        for (BuddyInfoModel buddy : this.buddiesList){
            if (buddy.equals(buddyInfoModel)){
                buddyFound = true;
                foundBuddy = buddy;
            }
        }
        if (buddyFound){
            this.buddiesList.remove(foundBuddy);
            foundBuddy.removeAddressBookModel();
        }
    }

    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "addressBookModel")
    public List<BuddyInfoModel> getBuddiesList(){
        return buddiesList;
    }
    public void setBuddiesList(List<BuddyInfoModel> buddyInfoModelList){ this.buddiesList = buddyInfoModelList; }

    public boolean containsBuddy(BuddyInfoModel buddy){
        boolean buddyFound = false;
        for (BuddyInfoModel b : this.buddiesList){
            if(b.equals(buddy)){
                buddyFound = true;
                break;
            }
        }
        return buddyFound;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (BuddyInfoModel buddyInfoModel : this.buddiesList){
            sb.append(buddyInfoModel.toString());
        }
        return sb.toString();
    }

    public static void main (String args[]){
        AddressBookModel addressBookModel = new AddressBookModel();

        addressBookModel.addBuddy(new BuddyInfoModel("Kamran", "888-1234"));
        addressBookModel.addBuddy(new BuddyInfoModel("J", "613-22222"));

        System.out.println(addressBookModel.toString());
    }
}