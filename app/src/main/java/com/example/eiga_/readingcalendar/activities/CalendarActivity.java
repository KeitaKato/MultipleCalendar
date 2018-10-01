package com.example.eiga_.readingcalendar.activities;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Px;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.services.ImageGetService;
import com.example.eiga_.readingcalendar.services.TessTwoIntentService;
import com.example.eiga_.readingcalendar.utils.MyContext;
import com.example.eiga_.readingcalendar.utils.PxDpUtil;
import com.example.eiga_.readingcalendar.views.adapters.CalendarAdapter;

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
    private FloatingActionButton cameraButton;
    private FloatingActionButton presetButton;
    private FloatingActionButton plansButton;
    private LinearLayout presetLayout;
    private LinearLayout cameraLayout;
    private LinearLayout plansLayout;
    private View mFabBackground;

    enum ButtonState {
        CLOSE,
        OPEN
    }

    ButtonState mButtonState = ButtonState.CLOSE;

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

        // フローティングアクションボタンにリスナーセット
        actionButton = findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(ActionButtonListener);

        // 各ボタンを表示、リスナーセット
        mFabBackground = findViewById(R.id.fabBackground);

        presetLayout = findViewById(R.id.AddPresetLayout);
        presetButton = findViewById(R.id.AddPresetFab);
        presetButton.setOnClickListener(PresetButtonListener);

        cameraLayout = findViewById(R.id.AddCameraLayout);
        cameraButton = findViewById(R.id.AddCameraFab);
        cameraButton.setOnClickListener(CameraButtonListener);

        plansLayout = findViewById(R.id.AddPlansLayout);
        plansButton = findViewById(R.id.AddPlansFab);
        plansButton.setOnClickListener(PlansButtonListener);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkOverlayPermission() {
        if(!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    View.OnClickListener ActionButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int iconWhile = PxDpUtil.dpToPx(66, MyContext.getContext());
            int iconWhileMini = PxDpUtil.dpToPx(10,MyContext.getContext());

            if (mButtonState == ButtonState.CLOSE) {
                fabOpen(iconWhile,iconWhileMini);
                actionButton.setElevation(mFabBackground.getElevation() + actionButton.getElevation() + PxDpUtil.dpToPx(20, MyContext.getContext()));

            } else {
                fabClose();
            }
        }
    };

    View.OnClickListener CameraButtonListener = new View.OnClickListener() {
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

    View.OnClickListener PresetButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CalendarActivity.this, AddPresetActivity.class);
            startActivity(intent);

        }
    };

    View.OnClickListener PlansButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CalendarActivity.this, AddMultiplePlansActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == WRITE_STORAGE_PERMISSION_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            CameraButtonListener.onClick(cameraButton);
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

    private void fabOpen(int iconWhile,int iconWhileMini) {
        plansLayout.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(plansLayout, "translationY",-iconWhile );
        animator.setDuration(200);
        animator.start();

        cameraLayout.setVisibility(View.VISIBLE);
        animator = ObjectAnimator.ofFloat(cameraLayout, "translationY", -iconWhile * 2 + iconWhileMini);
        animator.setDuration(200);
        animator.start();

        presetLayout.setVisibility(View.VISIBLE);
        animator = ObjectAnimator.ofFloat(presetLayout, "translationY", -iconWhile * 3 + iconWhileMini + iconWhileMini);
        animator.setDuration(200);
        animator.start();

        animator = ObjectAnimator.ofFloat(actionButton, "rotation", 45);
        animator.setDuration(200);
        animator.start();

        mButtonState = ButtonState.OPEN;
        mFabBackground.setVisibility(View.VISIBLE);
    }

    private void fabClose() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(presetLayout, "translationY",0);
        animator.setDuration(200);
        animator.start();
        presetLayout.setVisibility(View.GONE);

        animator = ObjectAnimator.ofFloat(cameraLayout, "translationY", 0);
        animator.setDuration(200);
        animator.start();
        cameraLayout.setVisibility(View.GONE);

        animator = ObjectAnimator.ofFloat(plansLayout, "translationY", 0);
        animator.setDuration(200);
        animator.start();
        plansLayout.setVisibility(View.GONE);

        animator = ObjectAnimator.ofFloat(actionButton, "rotation", 0);
        animator.setDuration(200);
        animator.start();

        mButtonState = ButtonState.CLOSE;
        mFabBackground.setVisibility(View.GONE);
    }

}
