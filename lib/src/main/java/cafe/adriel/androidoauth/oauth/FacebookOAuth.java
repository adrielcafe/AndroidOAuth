package cafe.adriel.androidoauth.oauth;

import android.app.Activity;
import android.util.Log;

import com.github.scribejava.apis.FacebookApi;

import org.json.JSONObject;

import cafe.adriel.androidoauth.model.SocialUser;

public final class FacebookOAuth extends BaseOAuth {
    private static final String SCOPE = "public_profile email";
    private static final String GET_ACCOUNT_URL = "https://graph.facebook.com/v2.6/me?fields=name,email,picture,cover";

    private FacebookOAuth(Activity activity){
        super(activity, FacebookApi.instance(), SCOPE, GET_ACCOUNT_URL);
    }

    public static FacebookOAuth Builder(Activity activity){
        return new FacebookOAuth(activity);
    }

    @Override
    protected SocialUser toAccount(String json){
        try {
            JSONObject accountJson = new JSONObject(json);
            SocialUser account = new SocialUser();
            account.setId(accountJson.getString("id"));
            account.setName(accountJson.getString("name"));
            if(accountJson.has("email")) {
                account.setEmail(accountJson.getString("email"));
            }
            if(accountJson.has("picture")) {
                account.setPictureUrl(accountJson
                        .getJSONObject("picture")
                        .getJSONObject("data")
                        .getString("url"));
            }
            if(accountJson.has("cover")) {
                account.setCoverUrl(accountJson
                        .getJSONObject("cover")
                        .getString("source"));
            }
            account.setProvider(OAuthProvider.FACEBOOK);
            return account;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}