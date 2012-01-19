
package com.gsbina.android.foltter;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UserService extends Service implements Runnable {

    private final String TAG = "UserService";
    private Twitter mTwitter;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConfigurationBuilder confbuilder = new ConfigurationBuilder();
        confbuilder.setOAuthConsumerKey(Foltter.CONSUMER_KEY);
        confbuilder.setOAuthConsumerSecret(Foltter.CONSUMER_SECRET);
        confbuilder.setOAuthAccessToken(Foltter.getToken(this));
        confbuilder.setOAuthAccessTokenSecret(Foltter.getTokenSecret(this));
        mTwitter = new TwitterFactory(confbuilder.build()).getInstance();

        new Thread(this).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {

        try {
            IDs followersIDs = mTwitter.getFollowersIDs(-1);
            ResponseList<User> users = mTwitter.lookupUsers(followersIDs.getIDs());
            for (User user : users) {
                Log.d(TAG, user.getScreenName());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

}
