package cafe.adriel.androidgoogleoauth;

import java.io.Serializable;

public class GoogleAccount implements Serializable {
    private String id;
    private String displayName;
    private String gender;
    private String profileUrl;
    private String imageUrl;
    private String language;
    private boolean isPlusUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isPlusUser() {
        return isPlusUser;
    }

    public void setPlusUser(boolean plusUser) {
        isPlusUser = plusUser;
    }

    @Override
    public String toString(){
        return String.format(
                "[id: %s, displayName: %s, gender: %s, profileUrl: %s, " +
                "imageUrl: %s, language: %s, isPlusUser: %s]",
                id, displayName, gender, profileUrl, imageUrl, language, isPlusUser);
    }
}