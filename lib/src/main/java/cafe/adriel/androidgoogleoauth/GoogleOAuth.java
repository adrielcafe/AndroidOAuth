package cafe.adriel.androidgoogleoauth;

import android.app.Activity;
import android.os.AsyncTask;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import org.json.JSONObject;

import java.util.Random;

public final class GoogleOAuth {
    private static final String SCOPE = "profile";
    private static final String CALLBACK = "urn:ietf:wg:oauth:2.0:oob:auto";
    private static final String GET_ACCOUNT_URL = "https://www.googleapis.com/plus/v1/people/me";

    private Activity activity;
    private String clientId;
    private String clientSecret;
    private OnGetTokenCallback callback;

    private GoogleOAuth(Activity activity){
        this.activity = activity;
    }

    public static GoogleOAuth Builder(Activity activity){
        return new GoogleOAuth(activity);
    }

    public GoogleOAuth setClientId(String clientId){
        this.clientId = clientId;
        return this;
    }

    public GoogleOAuth setClientSecret(String clientSecret){
        this.clientSecret = clientSecret;
        return this;
    }

    public GoogleOAuth setCallback(OnGetTokenCallback callback){
        this.callback = callback;
        return this;
    }

    public void login(){
        final String state = new Random().nextInt(999_999_999)+"";
        final OAuth20Service service = new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .state(state)
                .scope(SCOPE)
                .callback(CALLBACK)
                .build(GoogleApi20.instance());
        final String authUrl = service.getAuthorizationUrl();
        ConsentDialog.newInstance(authUrl, state)
                .setOnGetCodeCallback(new OnGetCodeCallback() {
                    @Override
                    public void onSuccess(final String code) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final OAuth2AccessToken accessToken = service.getAccessToken(code);
                                    final GoogleAccount account = getAccount(service, accessToken);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onSuccess(accessToken.getAccessToken(), account);
                                        }
                                    });
                                } catch (final Exception e){
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onError(e);
                                        }
                                    });
                                }
                            }
                        });
                    }
                    @Override
                    public void onError(Exception error) {
                        callback.onError(error);
                    }
                })
                .show(activity.getFragmentManager(), ConsentDialog.class.getName());
    }

    private GoogleAccount getAccount(OAuth20Service service, OAuth2AccessToken accessToken){
        try {
            OAuthRequest request = new OAuthRequest(Verb.GET, GET_ACCOUNT_URL, service);
            service.signRequest(accessToken, request);
            Response response = request.send();
            return toAccount(response.getBody());
        } catch (Exception e){
            return null;
        }
    }

    private GoogleAccount toAccount(String json){
        try {
            JSONObject accountJson = new JSONObject(json);
            GoogleAccount account = new GoogleAccount();
            account.setId(accountJson.getString("id"));
            account.setDisplayName(accountJson.getString("displayName"));
            account.setGender(accountJson.getString("gender"));
            account.setProfileUrl(accountJson.getString("url"));
            account.setImageUrl(accountJson.getJSONObject("image").getString("url"));
            account.setLanguage(accountJson.getString("language"));
            account.setPlusUser(accountJson.getBoolean("isPlusUser"));
            return account;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}