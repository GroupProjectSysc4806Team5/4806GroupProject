package team.GroupProject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Owner {

    private String name;

    @OneToMany
    private List<Bookstore> stores;

    @Id
    @GeneratedValue
    private Long id;

    public Owner(String name){
        this.name = name;
        stores = new ArrayList<Bookstore>();
    }

    public Owner() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bookstore> getStore() {
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
}
