package com.example.user.offplayer;
/*
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

public class MyAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget);
        if (AudioService.BroadcastActions.PREPARED.equals(action)) {
            updateAlbumArt(context, remoteViews); // 재생음악 파일이 변경된 경우.
        }
        updatePlayState(context, remoteViews); // 재생상태 업데이트.
        updateWidget(context, remoteViews); // 앱위젯 업데이트.
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateAlbumArt(Context context, RemoteViews remoteViews) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        long albumId = AudioApplication.getInstance().getServiceInterface().getAudioItem().mAlbumId;
        Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
        Picasso.with(context).load(albumArtUri).into(remoteViews, R.id.img_albumart, appWidgetIds);
    }

    private void updatePlayState(Context context, RemoteViews remoteViews) {
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()) {
            remoteViews.setImageViewResource(R.id.btn_play_pause, R.drawable.ic_pause);
        } else {
            remoteViews.setImageViewResource(R.id.btn_play_pause, R.drawable.ic_play);
        }
        String title = "재생중인 음악이 없습니다.";
        if (AudioApplication.getInstance().getServiceInterface().getAudioItem() != null) {
            title = AudioApplication.getInstance().getServiceInterface().getAudioItem().mTitle;
        }
        remoteViews.setTextViewText(R.id.txt_title, title);

        Intent actionLaunch = new Intent(context, MainActivity.class);
        Intent actionTogglePlay = new Intent(NotificationPlayer.CommandActions.TOGGLE_PLAY);
        Intent actionForward = new Intent(NotificationPlayer.CommandActions.FORWARD);
        Intent actionRewind = new Intent(NotificationPlayer.CommandActions.REWIND);

        PendingIntent launch = PendingIntent.getActivity(context, 0, actionLaunch, 0);
        PendingIntent togglePlay = PendingIntent.getService(context, 0, actionTogglePlay, 0);
        PendingIntent forward = PendingIntent.getService(context, 0, actionForward, 0);
        PendingIntent rewind = PendingIntent.getService(context, 0, actionRewind, 0);

        remoteViews.setOnClickPendingIntent(R.id.img_albumart, launch);
        remoteViews.setOnClickPendingIntent(R.id.btn_play_pause, togglePlay);
        remoteViews.setOnClickPendingIntent(R.id.btn_forward, forward);
        remoteViews.setOnClickPendingIntent(R.id.btn_rewind, rewind);
    }

    private void updateWidget(Context context, RemoteViews remoteViews) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
    }
}*/