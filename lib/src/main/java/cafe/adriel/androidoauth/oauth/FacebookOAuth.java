package cafe.adriel.androidoauth.oauth;

import android.app.Activity;

import com.github.scribejava.apis.FacebookApi;

import org.json.JSONObject;

import cafe.adriel.androidoauth.model.SocialUser;

public final class FacebookOAuth extends BaseOAuth {
    private static final String SCOPE = "public_profile email";
    private static final String GET_ACCOUNT_URL = "https://graph.facebook.com/v2.6/me";

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
            return account;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}