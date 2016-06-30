package cafe.adriel.androidgoogleoauth;

public interface OnGetCodeCallback {

    void onSuccess(String code);

    void onError(Exception error);

}