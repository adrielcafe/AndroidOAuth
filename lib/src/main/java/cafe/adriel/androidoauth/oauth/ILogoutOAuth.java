package cafe.adriel.androidoauth.oauth;

import cafe.adriel.androidoauth.callback.OnLogoutCallback;

public interface ILogoutOAuth<T> {

    T setToken(String token);

    T setCallback(OnLogoutCallback callback);

    void init();

}