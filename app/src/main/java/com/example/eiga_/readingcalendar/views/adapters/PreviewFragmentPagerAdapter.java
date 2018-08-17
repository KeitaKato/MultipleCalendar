package com.example.eiga_.readingcalendar.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.eiga_.readingcalendar.fragments.CameraPreviewFragment;
import com.example.eiga_.readingcalendar.fragments.OCRPreviewFragment;
import com.example.eiga_.readingcalendar.utils.MyContext;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PreviewFragmentPagerAdapter extends FragmentPagerAdapter {

    private String lang = "eng";
    private String filePath;
    private String imageUriString;
    private String OCRData;
    private Context context;

    public PreviewFragmentPagerAdapter(FragmentManager manager, String imageUriString) {
        super(manager);
        context = MyContext.getContext();
        filePath = context.getFilesDir() + "/tesseract/";
        checkFile(new File(filePath + "tessdata/"));
        this.imageUriString = imageUriString;
        this.OCRData = getOCRData(imageUriString);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return OCRPreviewFragment.newInstance(OCRData);
            case 1:
                return CameraPreviewFragment.newInstance(imageUriString);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public  CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "読み取りデータ";
            case 1:
                return "元画像";

        }
        return null;
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

    private String getOCRData(String imageUriString) {
        Uri imageUri = Uri.parse(imageUriString);
        // UriからInputStreamを生成。
        InputStream stream = null;
        try {
            stream = context.getContentResolver().openInputStream((Uri)imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        // tess-twoでocrデータを取得。
        TessBaseAPI baseApi = new TessBaseAPI();
        baseApi.init(filePath, lang);
        baseApi.setImage(bitmap);
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();

        return recognizedText;

    }
}
