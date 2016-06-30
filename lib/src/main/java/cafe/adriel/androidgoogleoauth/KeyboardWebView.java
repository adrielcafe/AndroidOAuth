package cafe.adriel.androidgoogleoauth;

import android.content.Context;
import android.view.MotionEvent;
import android.webkit.WebView;

/*
    By RuslanK
    http://stackoverflow.com/a/13418767
 */
public class KeyboardWebView extends WebView {
    public KeyboardWebView(Context context) {
        super(context);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                if (!hasFocus()) {
                    requestFocus();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}