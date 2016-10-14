package cafe.adriel.androidoauth.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import cafe.adriel.androidoauth.oauth.OAuthProvider;

public class SocialUser implements Parcelable {
    private String id;
    private String name;
    private String email;
    private String pictureUrl;
    private String coverUrl;
    private Date birthday;
    private OAuthProvider provider;

    public SocialUser() {

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
                "[id: %s, name: %s, email: %s, pictureUrl: %s, coverUrl: %s, birthday: %s, provider: %s]",
                id, name, email, pictureUrl, coverUrl, birthday, provider);
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
        dest.writeLong(this.birthday != null ? this.birthday.getTime() : -1);
        dest.writeInt(this.provider == null ? -1 : this.provider.ordinal());
    }

    protected SocialUser(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.pictureUrl = in.readString();
        this.coverUrl = in.readString();
        long tmpBirthday = in.readLong();
        this.birthday = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        int tmpProvider = in.readInt();
        this.provider = tmpProvider == -1 ? null : OAuthProvider.values()[tmpProvider];
    }

    public static final Creator<SocialUser> CREATOR = new Creator<SocialUser>() {
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