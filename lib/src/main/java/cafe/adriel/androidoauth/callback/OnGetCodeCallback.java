package cafe.adriel.androidoauth.callback;

public interface OnGetCodeCallback {

    void onSuccess(String code);

    void onError(Exception error);

}