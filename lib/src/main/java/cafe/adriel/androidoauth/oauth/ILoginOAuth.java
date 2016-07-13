package cafe.adriel.androidoauth.oauth;

import cafe.adriel.androidoauth.callback.OnLoginCallback;

public interface ILoginOAuth<T> {

    T setClientId(String clientId);

    T setClientSecret(String clientSecret);

    T setRedirectUri(String redirectUri);

    T setCallback(OnLoginCallback callback);

    void init();

}