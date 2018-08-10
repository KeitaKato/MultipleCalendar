package com.example.eiga_.readingcalendar.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.eiga_.readingcalendar.R;

public class ImageGetService extends Service {

    private View view;
    private WindowManager windowManager;
    private int dpScale;

    @Override
    public void onCreate() {
        super.onCreate();

        // dipを取得。
        dpScale = (int)getResources().getDisplayMetrics().density;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // API 26 以上startForegroundService() -----
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Context context = getApplicationContext();
            String channelId = "default";
            String title = context.getString(R.string.app_name);

            PendingIntent pendingIntent =
                 PendingIntent.getActivity(context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager =
                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            // Notification Channel 設定
            NotificationChannel channel = new NotificationChannel(
                    channelId, title, NotificationManager.IMPORTANCE_DEFAULT);
            if(notificationManager != null) {
                notificationManager.createNotificationChannel(channel);

                Notification notification = new Notification.Builder(context, channelId)
                        .setContentTitle(title)
                        .setSmallIcon(android.R.drawable.btn_star)
                        .setContentText("APPLICATION_OVERLAY")
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .build();

                startForeground(1, notification);
            }
        }
        // ----- startForegroundService()

        // inflaterの生成
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        int typeLayer = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        windowManager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        // windowManagerのパラメータを設定
        WindowManager.LayoutParams params = new WindowManager.LayoutParams (
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                typeLayer,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        // 右下に配置
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.x = 16 * dpScale;
        params.y = 16 * dpScale;

        // レイアウトファイルからInflateするViewを作成
        final ViewGroup nullParent = null;
        view = layoutInflater.inflate(R.layout.service_get_image, nullParent);

        windowManager.addView(view, params);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Viewを削除
        windowManager.removeView(view);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
