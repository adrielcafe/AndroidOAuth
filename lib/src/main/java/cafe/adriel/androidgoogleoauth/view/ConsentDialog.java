package cafe.adriel.androidgoogleoauth.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cafe.adriel.androidgoogleoauth.callback.OnGetCodeCallback;

public class ConsentDialog extends DialogFragment {
    private static final String EXTRA_AUTH_URL = "authUrl";
    private static final String EXTRA_STATE = "state";

    private String authUrl;
    private String state;
    private String code;
    private OnGetCodeCallback callback;

    public static ConsentDialog newInstance(String authUrl, String state) {
        Bundle args = new Bundle();
        args.putString(EXTRA_AUTH_URL, authUrl);
        args.putString(EXTRA_STATE, state);
        ConsentDialog frag = new ConsentDialog();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authUrl = getArguments().getString(EXTRA_AUTH_URL);
        state = getArguments().getString(EXTRA_STATE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        KeyboardWebView webView = new KeyboardWebView(getActivity());
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(view.getTitle().startsWith("Success") || url.contains("code=")) {
                    getCode(view.getTitle(), url);
                }
            }
        });
        webView.loadUrl(authUrl);

        return new AlertDialog.Builder(getActivity())
                .setView(webView)
                .create();
    }

    public ConsentDialog setOnGetCodeCallback(OnGetCodeCallback callback){
        this.callback = callback;
        return this;
    }

    private void getCode(String title, String url){
        if(title.startsWith("Success")){
            state = title.substring(title.indexOf("state="), title.indexOf("&"))
                    .replace("state=", "");
            code = title.substring(title.indexOf("code="))
                    .replace("code=", "");
        } else if(url.contains("code=")){
            state = url.substring(url.indexOf("state="))
                    .replace("state=", "")
                    .replace("#", "");
            code = url.substring(url.indexOf("code="), url.indexOf("&"))
                    .replace("code=", "");
        }
        if(ConsentDialog.this.state.equals(state)){
            callback.onSuccess(code);
        } else {
            callback.onError(new Exception("Wrong state"));
        }
        dismiss();
    }

}