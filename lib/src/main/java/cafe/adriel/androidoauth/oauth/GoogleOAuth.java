package cafe.adriel.androidoauth.oauth;

import android.app.Activity;
import android.util.Log;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.model.Verb;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import cafe.adriel.androidoauth.model.SocialUser;

public final class GoogleOAuth extends BaseOAuth {
    private static final String DEFAULT_SCOPES =
            "https://www.googleapis.com/auth/userinfo.profile " +
            "https://www.googleapis.com/auth/userinfo.email";
    private static final String GET_ACCOUNT_URL = "https://www.googleapis.com/plus/v1/people/me";
    private static final String REVOKE_TOKEN_URL = "https://accounts.google.com/o/oauth2/revoke?token=%s";
    private static final Verb REVOKE_TOKEN_VERB = Verb.GET;

    private GoogleOAuth(Activity activity) {
        super(activity, GoogleApi20.instance(), GET_ACCOUNT_URL, REVOKE_TOKEN_URL,
                REVOKE_TOKEN_VERB);
    }

    public static LoginOAuth login(Activity activity) {
        return new LoginOAuth(new GoogleOAuth(activity), DEFAULT_SCOPES);
    }

    public static LogoutOAuth logout(Activity activity) {
        return new LogoutOAuth(new GoogleOAuth(activity));
    }

    @Override
    protected SocialUser toAccount(String json) {
        try {
            Log.e("GOOGLE", json+"");
            JSONObject accountJson = new JSONObject(json);
            SocialUser account = new SocialUser();
            account.setId(accountJson.getString("id"));
            account.setName(accountJson.getString("displayName"));
            if (accountJson.has("emails")) {
                account.setEmail(accountJson
                        .getJSONArray("emails")
                        .getJSONObject(0)
                        .getString("value"));
            }
            if (accountJson.has("image")) {
                account.setPictureUrl(accountJson
                        .getJSONObject("image")
                        .getString("url"));
            }
            if (accountJson.has("cover")) {
                account.setCoverUrl(accountJson
                        .getJSONObject("cover")
                        .getJSONObject("coverPhoto")
                        .getString("url"));
            }
            if(accountJson.has("birthday")){
                try {
                    account.setBirthday(new SimpleDateFormat("yyyy-MM-dd")
                            .parse(accountJson.getString("birthday")));
                } catch (Exception e){ }
            }
            account.setProvider(OAuthProvider.GOOGLE);
            return account;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}