package cafe.adriel.androidoauth.model;

import java.io.Serializable;

public class SocialUser implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("[id: %s, name: %s]", id, name);
    }
}