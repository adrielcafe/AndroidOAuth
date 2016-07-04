package cafe.adriel.androidoauth.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cafe.adriel.androidoauth.callback.OnGetTokenCallback;
import cafe.adriel.androidoauth.model.SocialUser;
import cafe.adriel.androidoauth.oauth.FacebookOAuth;
import cafe.adriel.androidoauth.oauth.GoogleOAuth;

public class MainActivity extends AppCompatActivity {
    private TextView tokenView;
    private TextView accountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenView = (TextView) findViewById(R.id.token);
        accountView = (TextView) findViewById(R.id.account);
    }

    void googleLogin(View v){
        GoogleOAuth.Builder(this)
            .setClientId(Credentials.GOOGLE_CLIENT_ID)
            .setClientSecret(Credentials.GOOGLE_CLIENT_SECRET)
            .setTokenCallback(new OnGetTokenCallback() {
                @Override
                public void onSuccess(String token, SocialUser account) {
                    tokenView.setText("Google Token: \n" + token);
                    accountView.setText("Google User: \n" + account+"");
                }
                @Override
                public void onError(Exception error) {
                    error.printStackTrace();
                }
            })
            .login();
    }

    void facebookLogin(View v){
        FacebookOAuth.Builder(this)
            .setClientId(Credentials.FACEBOOK_APP_ID)
            .setClientSecret(Credentials.FACEBOOK_APP_SECRET)
            .setCallback(Credentials.FACEBOOK_REDIRECT_URI)
            .setTokenCallback(new OnGetTokenCallback() {
                @Override
                public void onSuccess(String token, SocialUser account) {
                    tokenView.setText("Facebook Token: \n" + token);
                    accountView.setText("Facebook User: \n" + account+"");
                }
                @Override
                public void onError(Exception error) {
                    error.printStackTrace();
                }
            })
            .login();
    }

}