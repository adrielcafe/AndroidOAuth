package cafe.adriel.androidgoogleoauth;

import android.app.Activity;
import android.os.AsyncTask;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.util.Random;

public final class GoogleOAuth {
    private static final String SCOPE = "profile";
    private static final String CALLBACK = "urn:ietf:wg:oauth:2.0:oob:auto";

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
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onSuccess(accessToken.getAccessToken());
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

}