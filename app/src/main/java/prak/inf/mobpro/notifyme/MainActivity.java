package prak.inf.mobpro.notifyme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private static final String NOTIFICATION_GUIDE_URL =
            "https://developer.android.com/design/patterns/notifications.html";
    private static final String ACTION_UPDATE_NOTIFICATION =
            "prak.inf.mobpro.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION =
            "prak.inf.mobpro.notifyme.ACTION_CANCEL_NOTIFICATION";

    private Button mNotifyButton;
    private Button mUpdateButton;
    private Button mCancelButton;
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyButton = (Button) findViewById(R.id.notify);
        mUpdateButton = (Button) findViewById(R.id.update);
        mCancelButton = (Button) findViewById(R.id.cancel);

        mNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });

        mNotifyButton.setEnabled(true);
        mUpdateButton.setEnabled(false);
        mCancelButton.setEnabled(false);

    }

    public void sendNotification(){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent =
                PendingIntent.getActivity(this,
                        NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new
                Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent =
                PendingIntent.getBroadcast
                        (this, NOTIFICATION_ID, cancelIntent,
                                PendingIntent.FLAG_ONE_SHOT);

        Intent learnMoreIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(NOTIFICATION_GUIDE_URL));
        PendingIntent learnMorePendingIntent =
                PendingIntent.getActivity(this, NOTIFICATION_ID, learnMoreIntent,
                        PendingIntent.FLAG_ONE_SHOT);

        Intent updateIntent = new
                Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent =
                PendingIntent.getBroadcast
                        (this, NOTIFICATION_ID, updateIntent,
                                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = (NotificationCompat.Builder) new
                NotificationCompat.Builder(this)

                .setContentTitle(getString(R.string.notification_title))

                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(R.drawable.ic_android,
                        getString(R.string.learn_more),
                        learnMorePendingIntent)
                .addAction(R.drawable.ic_update,
                        getString(R.string.update_me), updatePendingIntent)
                .setDeleteIntent(cancelPendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID,
                notifyBuilder.build());


        mNotifyButton.setEnabled(false);
        mUpdateButton.setEnabled(true);
        mCancelButton.setEnabled(true);

    }

    public void updateNotification(){
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(),R.drawable.mascot_1);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent learnMoreIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(NOTIFICATION_GUIDE_URL));
        PendingIntent learnMorePendingIntent =PendingIntent.getActivity(this, NOTIFICATION_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT);



        NotificationCompat.Builder notifyBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
              .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDeleteIntent(cancelPendingIntent)
                .addAction(R.drawable.ic_android,
                        getString(R.string.learn_more),
                        learnMorePendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(androidImage)

                        .setBigContentTitle(getString(R.string.notification_updated)));
                       Notification myNotification = notifyBuilder.build();
                        mNotifyManager.notify(NOTIFICATION_ID, myNotification);

        mNotifyButton.setEnabled(false);
        mUpdateButton.setEnabled(false);
        mCancelButton.setEnabled(true);

        myNotification = notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID, myNotification);

    }

    public void cancelNotification(){

        mNotifyButton.setEnabled(true);
        mUpdateButton.setEnabled(false);
        mCancelButton.setEnabled(false);
    }

    private class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ACTION_CANCEL_NOTIFICATION:
                    cancelNotification();

                    break;

                case ACTION_UPDATE_NOTIFICATION:
                    updateNotification();

                    break;

            }
        }
    }

}
