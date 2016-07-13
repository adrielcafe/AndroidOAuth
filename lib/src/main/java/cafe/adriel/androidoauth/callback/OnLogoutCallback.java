package cafe.adriel.androidoauth.callback;

public interface OnLogoutCallback {

    void onSuccess();

    void onError(Exception error);

}