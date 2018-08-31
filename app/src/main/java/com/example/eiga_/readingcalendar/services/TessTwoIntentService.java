package com.example.eiga_.readingcalendar.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;

import com.example.eiga_.readingcalendar.utils.MyContext;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TessTwoIntentService extends IntentService {

    private String filePath;
    private String lang = "eng";
    private Context context;

    public TessTwoIntentService() {
        super("TessTwoIntentService");
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        context = MyContext.getContext();
        filePath = context.getFilesDir() + "/tesseract/";
        checkFile(new File(filePath + "tessdata/"));
        String imageUriString = intent.getStringExtra("IMAGE_URI");
        Uri _imageUri = Uri.parse(imageUriString);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), _imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tessTwoData = getTessTwoData(bitmap);

        // LocalBroadcastManagerにデータをおくる
        Intent localBroadcastIntent = new Intent();
        localBroadcastIntent.putExtra("OCR_DATA", tessTwoData);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localBroadcastIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void checkFile(File file) {
        if (!file.exists() && file.mkdirs()) {
            copyFiles();
        }
        if(file.exists()) {
            String dataFilePath = filePath+ "/tessdata/"+ lang +".traineddata";
            File dataFile = new File(dataFilePath);

            if (!dataFile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String dataPath = filePath + "/tessdata/" + lang + ".traineddata";
            InputStream inStream = context.getAssets().open("tessdata/"+ lang +".traineddata");

            OutputStream outStream = new FileOutputStream(dataPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, read);
            }

            outStream.flush();
            outStream.close();
            inStream.close();

            File file = new File(dataPath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTessTwoData(Bitmap bitmap ) {

        // tess-twoでocrデータを取得。
        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.init(filePath, lang);
        baseApi.setImage(bitmap);
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();

        return recognizedText;

    }

}
