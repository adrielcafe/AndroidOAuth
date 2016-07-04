package cafe.adriel.androidoauth.oauth;

import android.app.Activity;
import android.os.AsyncTask;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.util.Random;

import cafe.adriel.androidoauth.callback.OnGetCodeCallback;
import cafe.adriel.androidoauth.callback.OnGetTokenCallback;
import cafe.adriel.androidoauth.model.SocialUser;
import cafe.adriel.androidoauth.view.ConsentDialog;

public abstract class BaseOAuth {
    protected Activity activity;
    protected String clientId;
    protected String clientSecret;
    protected String scope;
    protected String callback;
    protected String getAccountUrl;
    protected DefaultApi20 api;
    protected OnGetTokenCallback tokenCallback;

    protected BaseOAuth(Activity activity, DefaultApi20 api, String scope, String getAccountUrl){
        this.activity = activity;
        this.api = api;
        this.scope = scope;
        this.getAccountUrl = getAccountUrl;
    }

    public BaseOAuth setClientId(String clientId){
        this.clientId = clientId;
        return this;
    }

    public BaseOAuth setClientSecret(String clientSecret){
        this.clientSecret = clientSecret;
        return this;
    }

    public BaseOAuth setCallback(String callback){
        this.callback = callback;
        return this;
    }

    public BaseOAuth setTokenCallback(OnGetTokenCallback tokenCallback){
        this.tokenCallback = tokenCallback;
        return this;
    }

    public void login(){
        String state = new Random().nextInt(999_999_999)+"";
        final OAuth20Service service = new ServiceBuilder()
                .apiKey(clientId)
                .apiSecret(clientSecret)
                .state(state)
                .scope(scope)
                .callback(callback)
                .build(api);
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
                                    final SocialUser account = getAccount(service, accessToken);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tokenCallback.onSuccess(accessToken.getAccessToken(), account);
                                        }
                                    });
                                } catch (final Exception e){
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tokenCallback.onError(e);
                                        }
                                    });
                                }
                            }
                        });
                    }
                    @Override
                    public void onError(Exception error) {
                        tokenCallback.onError(error);
                    }
                })
                .show(activity.getFragmentManager(), ConsentDialog.class.getName());
    }

    private SocialUser getAccount(OAuth20Service service, OAuth2AccessToken accessToken){
        try {
            OAuthRequest request = new OAuthRequest(Verb.GET, getAccountUrl, service);
            service.signRequest(accessToken, request);
            Response response = request.send();
            return toAccount(response.getBody());
        } catch (Exception e){
            return null;
        }
    }

    protected abstract SocialUser toAccount(String json);

}