package cafe.adriel.androidoauth.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cafe.adriel.androidoauth.callback.OnLoginCallback;
import cafe.adriel.androidoauth.callback.OnLogoutCallback;
import cafe.adriel.androidoauth.model.SocialUser;
import cafe.adriel.androidoauth.oauth.FacebookOAuth;
import cafe.adriel.androidoauth.oauth.GoogleOAuth;
import cafe.adriel.androidoauth.oauth.OAuthProvider;

public class MainActivity extends AppCompatActivity {
    private OAuthProvider currentProvider;
    private String currentToken;

    private TextView tokenView;
    private TextView accountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenView = (TextView) findViewById(R.id.token);
        accountView = (TextView) findViewById(R.id.account);
    }

    void googleLogin(View v) {
        GoogleOAuth.login(this)
                .setClientId(Credentials.GOOGLE_CLIENT_ID)
                .setClientSecret(Credentials.GOOGLE_CLIENT_SECRET)
                .setRedirectUri(Credentials.GOOGLE_REDIRECT_URI)
                .setCallback(new OnLoginCallback() {
                    @Override
                    public void onSuccess(String token, SocialUser user) {
                        currentProvider = user.getProvider();
                        currentToken = token;
                        tokenView.setText("Google Token: \n" + token);
                        accountView.setText("Google User: \n" + user + "");
                    }

                    @Override
                    public void onError(Exception error) {
                        error.printStackTrace();
                    }
                })
                .init();
    }

    void facebookLogin(View v) {
        FacebookOAuth.login(this)
                .setClientId(Credentials.FACEBOOK_APP_ID)
                .setClientSecret(Credentials.FACEBOOK_APP_SECRET)
                .setRedirectUri(Credentials.FACEBOOK_REDIRECT_URI)
                .setCallback(new OnLoginCallback() {
                    @Override
                    public void onSuccess(String token, SocialUser user) {
                        currentProvider = user.getProvider();
                        currentToken = token;
                        tokenView.setText("Facebook Token: \n" + token);
                        accountView.setText("Facebook User: \n" + user + "");
                    }

                    @Override
                    public void onError(Exception error) {
                        error.printStackTrace();
                    }
                })
                .init();
    }

    void logout(View v) {
        if (currentToken != null) {
            OnLogoutCallback callback = new OnLogoutCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "Logout Success!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception error) {
                    Toast.makeText(MainActivity.this, "Logout Error: " + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            };
            switch (currentProvider) {
                case GOOGLE:
                    GoogleOAuth.logout(this)
                            .setToken(currentToken)
                            .setCallback(callback)
                            .init();
                    break;
                case FACEBOOK:
                    FacebookOAuth.logout(this)
                            .setToken(currentToken)
                            .setCallback(callback)
                            .init();
                    break;
            }
        } else {
            Toast.makeText(this, "Token not found. Login first.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}