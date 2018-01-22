package tzz.com.myapplication;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;

public class KeyboardActivity extends AppCompatActivity {
    private Context mContext;
    private KeyboardView keyboardView;
    private WebView webView;
    private Keyboard k2;// 数字键盘
    private RelativeLayout relativeLayout_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView1);
        relativeLayout_root = (RelativeLayout) findViewById(R.id.relativeLayout_root);

        webView = (WebView) findViewById(R.id.webView);
        webView.addJavascriptInterface(new JsBikeInterfaceImpl(), "JsBikeInterface");
        webView.loadUrl("file:///android_asset/input.html");
        webView.getSettings().setJavaScriptEnabled(true);
        mContext = this;

        k2 = new Keyboard(this, R.xml.number);
        keyboardView.setKeyboard(k2);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);//按键时是否会弹出一个放大的 键 的图案
        keyboardView.setOnKeyboardActionListener(onKeyboardActionListener);

    }

    KeyboardView.OnKeyboardActionListener onKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                webView.loadUrl("javascript:deleteText()");
            } else {
                //键盘输入
                String str = Character.toString((char) primaryCode);
                webView.loadUrl("javascript:setKeyText('" + str + "')");

            }
        }
    };

    public void showKeyboard() {
        //弹出键盘
        //必须在主线程中调用，否则可能显示不出来键盘
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                keyboardView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void hideKeyboard() {
        //隐藏键盘
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                keyboardView.setVisibility(View.GONE);
            }
        });

    }

    public class JsBikeInterfaceImpl {
        /**
         * Instantiate the interface and set the context
         */
        public JsBikeInterfaceImpl() {

        }

        /**
         * 弹出键盘
         *
         * @return
         */
        @JavascriptInterface
        public void ShowKB() { showKeyboard(); }

        @JavascriptInterface
        public void HideKB() {
            hideKeyboard();
        }
    }

}
