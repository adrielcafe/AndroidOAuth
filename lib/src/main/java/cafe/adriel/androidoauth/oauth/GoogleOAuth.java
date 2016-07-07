package cafe.adriel.androidoauth.oauth;

import android.app.Activity;

import com.github.scribejava.apis.GoogleApi20;

import org.json.JSONObject;

import cafe.adriel.androidoauth.model.SocialUser;

public final class GoogleOAuth extends BaseOAuth {
    private static final String SCOPE = "profile email";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob:auto";
    private static final String GET_ACCOUNT_URL = "https://www.googleapis.com/plus/v1/people/me";

    private GoogleOAuth(Activity activity){
        super(activity, GoogleApi20.instance(), SCOPE, GET_ACCOUNT_URL);
        redirectUri = REDIRECT_URI;
    }

    public static GoogleOAuth Builder(Activity activity){
        return new GoogleOAuth(activity);
    }

    @Override
    public BaseOAuth setRedirectUri(String callback){
        throw new IllegalArgumentException("Must use the default Google url redirect");
    }

    @Override
    protected SocialUser toAccount(String json){
        try {
            JSONObject accountJson = new JSONObject(json);
            SocialUser account = new SocialUser();
            account.setId(accountJson.getString("id"));
            account.setName(accountJson.getString("displayName"));
            if(accountJson.has("emails")) {
                account.setEmail(accountJson
                        .getJSONArray("emails")
                        .getJSONObject(0)
                        .getString("value"));
            }
            if(accountJson.has("image")){
                account.setPictureUrl(accountJson
                        .getJSONObject("image")
                        .getString("url"));
            }
            if(accountJson.has("cover")) {
                account.setCoverUrl(accountJson
                        .getJSONObject("cover")
                        .getJSONObject("coverPhoto")
                        .getString("url"));
            }
            account.setProvider(OAuthProvider.GOOGLE);
            return account;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}