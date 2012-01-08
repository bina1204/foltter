
package com.gsbina.android.foltter;

import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class StartActivity extends Activity {

    private final int REQUEST_OAUTH = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Foltter.hasToken(this)) {
            Intent intent = new Intent();
            intent.setClass(this, FolderListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent();
            intent.setClass(this, OAuthActivity.class)
                    .putExtra(OAuthActivity.CALLBACK, Foltter.OAUTH_CALLBACK_URL)
                    .putExtra(OAuthActivity.CONSUMER_KEY, Foltter.CONSUMER_KEY)
                    .putExtra(OAuthActivity.CONSUMER_SECRET, Foltter.CONSUMER_SECRET);
            startActivityForResult(intent, REQUEST_OAUTH);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode) {
            long userId = data.getLongExtra(OAuthActivity.USER_ID, 0);
            String screenName = data.getStringExtra(OAuthActivity.SCREEN_NAME);

            Toast.makeText(this, userId + "\n" + screenName, Toast.LENGTH_SHORT).show();

            String token = data.getStringExtra(OAuthActivity.TOKEN);
            String tokenSecret = data.getStringExtra(OAuthActivity.TOKEN_SECRET);

            Foltter.writeToken(this, new AccessToken(token, tokenSecret));

            Intent intent = new Intent();
            intent.setClass(this, FolderListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

}
