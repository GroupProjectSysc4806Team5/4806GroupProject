package Bookstore.Model;

import static javax.persistence.CascadeType.ALL;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
@Entity
public class Owner {

    private String password;

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "owner")
    private List<Bookstore> stores;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Owner(String name, String pass){
        this.name = name;
        password = pass;
        stores = new ArrayList<Bookstore>();
    }

    public Owner() {
        stores = new ArrayList<Bookstore>();



    public List<Bookstore> getStores() {
        return stores;
    }


    public Bookstore getBookstoreById(long bookstoreId){
        for (Bookstore bookstore: this.bookstores){
            if (bookstore.getId() == bookstoreId){
                return bookstore;
            }
        }
        return null;
    }

    public void addBookstore(Bookstore bookstore){
        bookstore.setOwner(this);
        this.bookstores.add(bookstore);
    }

    public void removeBookstoreById(long bookstoreId){
        Bookstore bookstoreFound = null;
        for (Bookstore bookstore : this.bookstores){
            if (bookstore.getId() == bookstoreId){
                bookstoreFound = bookstore;
                break;
            }
        }
        if (bookstoreFound != null) {
            this.bookstores.remove(bookstoreFound);
            bookstoreFound.removeBookstoreOwner();
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addStore(Bookstore store){
        stores.add(store);
    }
}
