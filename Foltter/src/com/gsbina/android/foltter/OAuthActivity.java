
package com.gsbina.android.foltter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OAuthActivity extends Activity {

    // Activity�J�n���̃C���e���g�p�����[�^
    public static final String CALLBACK = "callback";
    public static final String CONSUMER_KEY = "consumer_key";
    public static final String CONSUMER_SECRET = "consumer_secret";

    // Activity�I�����̃C���e���g�p�����[�^
    public static final String USER_ID = "user_id";
    public static final String SCREEN_NAME = "screen_name";
    public static final String TOKEN = "token";
    public static final String TOKEN_SECRET = "token_secret";

    private static final String OAUTH_VERIFIER = "oauth_verifier";

    private WebView mWebView;
    private String mCallback;
    private Twitter mTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(Activity.RESULT_CANCELED);

        // �v���O���X�\��
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        // View�̃Z�b�g�A�b�v
        mWebView = new WebView(this);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
        setContentView(mWebView);

        // Twitter��OAuth�F�؉�ʂŖ��񃆁[�U���A�A�J�E���g����͂����邽�߂ɕK�v
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(false);

        Intent intent = getIntent();
        mCallback = intent.getStringExtra(CALLBACK);
        String consumerKey = intent.getStringExtra(CONSUMER_KEY);
        String consumerSecret = intent.getStringExtra(CONSUMER_SECRET);
        if ((mCallback == null) || (consumerKey == null)
                || (consumerSecret == null)) {
            finish();
        }

        mTwitter = new TwitterFactory().getInstance();
        mTwitter.setOAuthConsumer(consumerKey, consumerSecret);

        PreTask preTask = new PreTask();
        preTask.execute();
    }

    // �O����
    public class PreTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(Void... params) {
            String authorizationUrl = null;
            try {
                // �񓯊��������K�v�ȃ��\�b�h�̌Ăяo��
                RequestToken requestToken = mTwitter.getOAuthRequestToken();
                if (requestToken != null) {
                    authorizationUrl = requestToken.getAuthorizationURL();
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return authorizationUrl;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            setProgressBarIndeterminateVisibility(false);
            if (result != null) {
                mWebView.loadUrl(result);
            } else {
                finish();
            }
        }

    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            setProgress(newProgress * 100);
        }

    };

    private WebViewClient mWebViewClient = new WebViewClient() {

        // ����̃y�[�W���t�b�N
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean result = true;
            if ((url != null) && (url.startsWith(mCallback))) {
                Uri uri = Uri.parse(url);
                String oAuthVerifier = uri.getQueryParameter(OAUTH_VERIFIER);
                PostTask postTask = new PostTask();
                postTask.execute(oAuthVerifier);
            } else {
                result = super.shouldOverrideUrlLoading(view, url);
            }
            return result;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            shouldOverrideUrlLoading(view, url);
        }

    };

    // �㏈��
    public class PostTask extends AsyncTask<String, Void, AccessToken> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected AccessToken doInBackground(String... params) {
            AccessToken accessToken = null;
            if (params != null) {
                try {
                    // �񓯊��������K�v�ȃ��\�b�h�̌Ăяo��
                    accessToken = mTwitter.getOAuthAccessToken(params[0]);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
            return accessToken;
        }

        @Override
        protected void onPostExecute(AccessToken result) {
            super.onPostExecute(result);
            setProgressBarIndeterminateVisibility(false);
            if (result != null) {
                long userId = result.getUserId();
                String screenName = result.getScreenName();
                String token = result.getToken();
                String tokenSecret = result.getTokenSecret();
                Intent intent = new Intent();
                intent.putExtra(USER_ID, userId);
                intent.putExtra(SCREEN_NAME, screenName);
                intent.putExtra(TOKEN, token);
                intent.putExtra(TOKEN_SECRET, tokenSecret);
                setResult(Activity.RESULT_OK, intent);
            }
            finish();
        }
    }
}
