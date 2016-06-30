package cafe.adriel.androidgoogleoauth;

public interface OnGetTokenCallback {

    void onSuccess(String token);

    void onError(Exception error);

}