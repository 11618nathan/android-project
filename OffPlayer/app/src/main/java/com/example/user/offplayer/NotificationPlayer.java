package com.example.user.offplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class NotificationPlayer {

    // AudioService

    private final static int NOTIFICATION_PLAYER_ID = 0x342;
    private AudioService mService;
    private NotificationManager mNotificationManager;
    private NotificationManagerBuilder mNotificationManagerBuilder;
    private boolean isForeground;

    public NotificationPlayer(AudioService service) {
        mService = service;
        mNotificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void updateNotificationPlayer() {
        /*
        cancel();
        mNotificationManagerBuilder = new NotificationManagerBuilder();
        mNotificationManagerBuilder.execute();
        */
        /*
         */
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), mService.getAudioItem().mAlbumId);
                Bitmap largIcon = null;
                try {
                    largIcon = Picasso.with(mService).load(albumArtUri).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent actionTogglePlay = new Intent(CommandActions.TOGGLE_PLAY);
                Intent actionForward = new Intent(CommandActions.FORWARD);
                Intent actionRewind = new Intent(CommandActions.REWIND);
                Intent actionClose = new Intent(CommandActions.CLOSE);
                PendingIntent togglePlay = PendingIntent.getService(mService, 0, actionTogglePlay, 0);
                PendingIntent forward = PendingIntent.getService(mService, 0, actionForward, 0);
                PendingIntent rewind = PendingIntent.getService(mService, 0, actionRewind, 0);
                PendingIntent close = PendingIntent.getService(mService, 0, actionClose, 0);


                android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(mService);
                builder
                        .setContentTitle(mService.getAudioItem().mTitle)
                        .setContentText(mService.getAudioItem().mArtist)
                        .setLargeIcon(largIcon)
                        .setContentIntent(PendingIntent.getActivity(mService, 0, new Intent(mService, MainActivity.class), 0));

                builder.addAction(new android.support.v4.app.NotificationCompat.Action(R.drawable.ic_rewind, "", rewind));
                builder.addAction(new android.support.v4.app.NotificationCompat.Action(mService.isPlaying() ? R.drawable.ic_pause : R.drawable.ic_play, "", togglePlay));
                builder.addAction(new android.support.v4.app.NotificationCompat.Action(R.drawable.ic_forward, "", forward));
                builder.addAction(new android.support.v4.app.NotificationCompat.Action(R.drawable.ic_close, "", close));
                int[] actionsViewIndexs = new int[]{1,2,3};
                builder.setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(actionsViewIndexs));
                builder.setSmallIcon(R.mipmap.ic_launcher);

                Notification notification = builder.build();

                NotificationManagerCompat.from(mService).notify(NOTIFICATION_PLAYER_ID, notification);

                if (!isForeground) {
                    isForeground = true;
                    // 서비스를 Foreground 상태로 만든다
                    mService.startForeground(NOTIFICATION_PLAYER_ID, notification);
                }

                return null;
            }
        }.execute();
    }

    public void removeNotificationPlayer() {
        cancel();
        mService.stopForeground(true);
        isForeground = false;
    }

    private void cancel() {
        if (mNotificationManagerBuilder != null) {
            mNotificationManagerBuilder.cancel(true);
            mNotificationManagerBuilder = null;
        }
    }

    public class CommandActions {
        public final static String REWIND = "REWIND";
        public final static String TOGGLE_PLAY = "TOGGLE_PLAY";
        public final static String FORWARD = "FORWARD";
        public final static String CLOSE = "CLOSE";
    }

    private class NotificationManagerBuilder extends AsyncTask<Void, Void, Notification> {
        private RemoteViews mRemoteViews;
        private NotificationCompat.Builder mNotificationBuilder;
        private PendingIntent mMainPendingIntent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent mainActivity = new Intent(mService, MainActivity.class);
            mMainPendingIntent = PendingIntent.getActivity(mService, 0, mainActivity, 0);
            mRemoteViews = createRemoteView(R.layout.notification_player);
            mNotificationBuilder = new NotificationCompat.Builder(mService);
            mNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                    .setOngoing(true)
                    .setContentIntent(mMainPendingIntent)
                    .setContent(mRemoteViews);

            Notification notification = mNotificationBuilder.build();
            notification.priority = Notification.PRIORITY_MAX;
            notification.contentIntent = mMainPendingIntent;
            if (!isForeground) {
                isForeground = true;
                // 서비스를 Foreground 상태로 만든다
                mService.startForeground(NOTIFICATION_PLAYER_ID, notification);
            }
        }

        @Override
        protected Notification doInBackground(Void... params) {
            mNotificationBuilder.setContent(mRemoteViews);
            mNotificationBuilder.setContentIntent(mMainPendingIntent);
            mNotificationBuilder.setPriority(Notification.PRIORITY_MAX);
            Notification notification = mNotificationBuilder.build();
            updateRemoteView(mRemoteViews, notification);
            return notification;
        }

        @Override
        protected void onPostExecute(Notification notification) {
            super.onPostExecute(notification);
            try {
                mNotificationManager.notify(NOTIFICATION_PLAYER_ID, notification);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private RemoteViews createRemoteView(int layoutId) {
            RemoteViews remoteView = new RemoteViews(mService.getPackageName(), layoutId);
            Intent actionTogglePlay = new Intent(CommandActions.TOGGLE_PLAY);
            Intent actionForward = new Intent(CommandActions.FORWARD);
            Intent actionRewind = new Intent(CommandActions.REWIND);
            Intent actionClose = new Intent(CommandActions.CLOSE);
            PendingIntent togglePlay = PendingIntent.getService(mService, 0, actionTogglePlay, 0);
            PendingIntent forward = PendingIntent.getService(mService, 0, actionForward, 0);
            PendingIntent rewind = PendingIntent.getService(mService, 0, actionRewind, 0);
            PendingIntent close = PendingIntent.getService(mService, 0, actionClose, 0);

            remoteView.setOnClickPendingIntent(R.id.btn_play_pause, togglePlay);
            remoteView.setOnClickPendingIntent(R.id.btn_forward, forward);
            remoteView.setOnClickPendingIntent(R.id.btn_rewind, rewind);
            remoteView.setOnClickPendingIntent(R.id.btn_close, close);
            return remoteView;
        }

        private void updateRemoteView(RemoteViews remoteViews, Notification notification) {
            if (mService.isPlaying()) {
                remoteViews.setImageViewResource(R.id.btn_play_pause, R.drawable.ic_pause);
            } else {
                remoteViews.setImageViewResource(R.id.btn_play_pause, R.drawable.ic_play);
            }

            String title = mService.getAudioItem().mTitle;
            remoteViews.setTextViewText(R.id.txt_title, title);
            Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), mService.getAudioItem().mAlbumId);
            Picasso.with(mService).load(albumArtUri).error(R.mipmap.ic_launcher).into(remoteViews, R.id.img_albumart, NOTIFICATION_PLAYER_ID, notification);
        }
    }
}
