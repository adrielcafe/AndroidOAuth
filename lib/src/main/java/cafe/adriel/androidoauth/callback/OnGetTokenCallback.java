package cafe.adriel.androidoauth.callback;

import cafe.adriel.androidoauth.model.SocialUser;

public interface OnGetTokenCallback {

    void onSuccess(String token, SocialUser user);

    void onError(Exception error);

}