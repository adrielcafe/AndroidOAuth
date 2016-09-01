package cafe.adriel.androidoauth.model;

import android.os.Parcel;
import android.os.Parcelable;

import cafe.adriel.androidoauth.oauth.OAuthProvider;

public class SocialUser implements Parcelable {
    private String id;
    private String name;
    private String email;
    private String pictureUrl;
    private String coverUrl;
    private OAuthProvider provider;

    public SocialUser() {

    }

    protected SocialUser(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.pictureUrl = in.readString();
        this.coverUrl = in.readString();
        int tmpProvider = in.readInt();
        this.provider = tmpProvider == -1 ? null : OAuthProvider.values()[tmpProvider];
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public OAuthProvider getProvider() {
        return provider;
    }

    public void setProvider(OAuthProvider provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return String.format(
                "[id: %s, name: %s, email: %s, pictureUrl: %s, coverUrl: %s, provider: %s]",
                id, name, email, pictureUrl, coverUrl, provider);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.pictureUrl);
        dest.writeString(this.coverUrl);
        dest.writeInt(this.provider == null ? -1 : this.provider.ordinal());
    }

    public static final Parcelable.Creator<SocialUser> CREATOR = new Parcelable.Creator<SocialUser>() {
        @Override
        public SocialUser createFromParcel(Parcel source) {
            return new SocialUser(source);
        }

        @Override
        public SocialUser[] newArray(int size) {
            return new SocialUser[size];
        }
    };
}