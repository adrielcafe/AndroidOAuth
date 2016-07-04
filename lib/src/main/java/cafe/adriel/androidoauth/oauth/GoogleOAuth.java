package cafe.adriel.androidoauth.oauth;

import android.app.Activity;

import com.github.scribejava.apis.GoogleApi20;

import org.json.JSONObject;

import cafe.adriel.androidoauth.model.SocialUser;

public final class GoogleOAuth extends BaseOAuth {
    private static final String SCOPE = "profile email";
    private static final String CALLBACK = "urn:ietf:wg:oauth:2.0:oob:auto";
    private static final String GET_ACCOUNT_URL = "https://www.googleapis.com/plus/v1/people/me";

    private GoogleOAuth(Activity activity){
        super(activity, GoogleApi20.instance(), SCOPE, GET_ACCOUNT_URL);
        callback = CALLBACK;
    }

    public static GoogleOAuth Builder(Activity activity){
        return new GoogleOAuth(activity);
    }

    @Override
    public BaseOAuth setCallback(String callback){
        throw new IllegalArgumentException("Must use the default Google url callback");
    }

    @Override
    protected SocialUser toAccount(String json){
        try {
            JSONObject accountJson = new JSONObject(json);
            SocialUser account = new SocialUser();
            account.setId(accountJson.getString("id"));
            account.setName(accountJson.getString("displayName"));
            account.setProvider(OAuthProvider.GOOGLE);
            return account;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}