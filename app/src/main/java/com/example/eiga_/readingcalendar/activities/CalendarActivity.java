package com.example.eiga_.readingcalendar.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.services.ImageGetService;
import com.example.eiga_.readingcalendar.services.TessTwoIntentService;
import com.example.eiga_.readingcalendar.views.adapters.CalendarAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity{

    private TextView titleText;
    private Button prevButton, nextButton;
    private CalendarAdapter mCalendarAdapter;
    private GridView calendarGridView;
    private Intent serviceIntent;
    // 保存された画像のURI
    private Uri _imageUri;
    private String imageUriString;
    private static int OVERLAY_PERMISSION_REQ_CODE = 1000;
    private static int CAMERA_REQ_CODE = 3000;
    private static int WRITE_STORAGE_PERMISSION_REQ_CODE = 2000;
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            checkOverlayPermission();
        }

        titleText = findViewById(R.id.titleText);
        prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });
        calendarGridView = findViewById(R.id.calendarGridView);
        mCalendarAdapter = new CalendarAdapter(this);
        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());

        actionButton = findViewById(R.id.floatingActionButton2);
        actionButton.setOnClickListener(actionButtonListener);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkOverlayPermission() {
        if(!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    View.OnClickListener actionButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //WRITE_EXTERNAL_STORAGEの許可が下りていないなら...
            if(ActivityCompat.checkSelfPermission(CalendarActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 許可を求めるダイアログを表示。
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(CalendarActivity.this, permissions, WRITE_STORAGE_PERMISSION_REQ_CODE);
                return;
            }

            // 日時データのフォーマットを生成。
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            // 現在のデータを取得。
            Date now = new Date(System.currentTimeMillis());
            // データをフォーマットに成形。
            String nowStr = dateFormat.format(now);
            // ストレージに格納する画像のファイル名を生成。
            String fileName = "ReadingCalendarPhoto_" + nowStr + ".jpg";
            // ContentValuesを生成。
            ContentValues values = new ContentValues();
            // 画像ファイル名を設定。
            values.put(MediaStore.Images.Media.TITLE, fileName);
            // 画像ファイルの種類を設定。
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            // ContentResolverを生成。
            ContentResolver resolver = getContentResolver();
            // ContentResolverを使ってURIを生成。
            _imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            // cameraIntentオブジェクトを生成。
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Extra情報として_imageUriを設定。
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
            // アクティビティを起動
            startActivityForResult(cameraIntent, CAMERA_REQ_CODE);

            // serviceIntentを生成。
            serviceIntent = new Intent(getApplication(), ImageGetService.class);
            // API 26以上はstartForegroundService
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }

    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == WRITE_STORAGE_PERMISSION_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            actionButtonListener.onClick(actionButton);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Log.d("debug","SYSTEM_ALERT_WINDOW permission not granted...");
                }
            }
        } else if (requestCode == CAMERA_REQ_CODE && resultCode == RESULT_OK) {
            // service停止
            stopService(serviceIntent);

            // previewIntentを作成。
            Intent previewIntent = new Intent(CalendarActivity.this, PreviewActivity.class);
            // _imageUriをStringに変換
            imageUriString = _imageUri.toString();
            // intentにデータを格納。
            previewIntent.putExtra("IMAGE_URI", imageUriString);
            // PreviewActivityの起動
            startActivity(previewIntent);

            //TessTwoIntentを作成
            Intent tessTwoIntent = new Intent(CalendarActivity.this, TessTwoIntentService.class);
            tessTwoIntent.putExtra("IMAGE_URI", imageUriString);
            startService(tessTwoIntent);
        }
    }

}
