package team.GroupProject;

public class Owner {

    private String name;

    private Bookstore store;

    public Owner(String name, Bookstore store){
        this.name = name;
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bookstore getStore() {
        return store;
    }

    public void setStore(Bookstore store) {
        this.store = store;
    }
}
