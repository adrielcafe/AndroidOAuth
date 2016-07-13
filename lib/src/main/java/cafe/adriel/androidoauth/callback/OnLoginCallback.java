package cafe.adriel.androidoauth.callback;

import cafe.adriel.androidoauth.model.SocialUser;

public interface OnLoginCallback {

    void onSuccess(String token, SocialUser user);

    void onError(Exception error);

}