package Bookstore.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Owner {

    private String name;

    private String password;

    @OneToMany(cascade= CascadeType.ALL,mappedBy = "owner")
    private List<Bookstore> stores;

    @Id
    @GeneratedValue
    private Long id;

    public Owner(String name, String pass){
        this.name = name;
        password = pass;
        stores = new ArrayList<Bookstore>();
    }

    public Owner() {
        stores = new ArrayList<Bookstore>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bookstore> getStores() {
        return stores;
    }

    public void setStores(List<Bookstore> stores) {
        this.stores = stores;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
