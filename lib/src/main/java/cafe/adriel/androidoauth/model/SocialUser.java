package cafe.adriel.androidoauth.model;

import java.io.Serializable;
import java.security.Provider;

import cafe.adriel.androidoauth.oauth.OAuthProvider;

public class SocialUser implements Serializable {
    private String id;
    private String name;
    private OAuthProvider provider;

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

    public OAuthProvider getProvider() {
        return provider;
    }

    public void setProvider(OAuthProvider provider) {
        this.provider = provider;
    }

    @Override
    public String toString(){
        return String.format("[id: %s, name: %s, provider: %s]", id, name, provider);
    }
}