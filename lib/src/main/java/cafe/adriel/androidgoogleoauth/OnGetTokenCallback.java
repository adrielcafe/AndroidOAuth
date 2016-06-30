package cafe.adriel.androidgoogleoauth;

public interface OnGetTokenCallback {

    void onSuccess(String token, GoogleAccount account);

    void onError(Exception error);

}