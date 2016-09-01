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

    public void googleLogin(View v) {
        GoogleOAuth.login(this)
                .setClientId(Credentials.GOOGLE_CLIENT_ID)
                .setClientSecret(Credentials.GOOGLE_CLIENT_SECRET)
                .setAdditionalScopes("https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/user.birthday.read")
                .setRedirectUri(Credentials.GOOGLE_REDIRECT_URI)
                .setCallback(new OnLoginCallback() {
                    @Override
                    public void onSuccess(String token, SocialUser user) {
                        afterLogin(token, user);
                    }
                    @Override
                    public void onError(Exception error) {
                        error.printStackTrace();
                    }
                })
                .init();
    }

    public void facebookLogin(View v) {
        FacebookOAuth.login(this)
                .setClientId(Credentials.FACEBOOK_APP_ID)
                .setClientSecret(Credentials.FACEBOOK_APP_SECRET)
                .setAdditionalScopes("user_friends user_birthday")
                .setRedirectUri(Credentials.FACEBOOK_REDIRECT_URI)
                .setCallback(new OnLoginCallback() {
                    @Override
                    public void onSuccess(String token, SocialUser user) {
                        afterLogin(token, user);
                    }
                    @Override
                    public void onError(Exception error) {
                        error.printStackTrace();
                    }
                })
                .init();
    }

    public void logout(View v) {
        if (currentToken != null) {
            OnLogoutCallback callback = new OnLogoutCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "Logout Success!", Toast.LENGTH_SHORT).show();
                    afterLogout();
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
            Toast.makeText(this, "Token not found. Login first.", Toast.LENGTH_SHORT).show();
        }
    }

    private void afterLogin(String token, SocialUser user){
        currentToken = token;
        currentProvider = user.getProvider();
        tokenView.setText(currentProvider +" Token: \n" + token);
        accountView.setText(currentProvider +" User: \n" + user);
    }

    private void afterLogout(){
        tokenView.setText("");
        accountView.setText("");
    }

}