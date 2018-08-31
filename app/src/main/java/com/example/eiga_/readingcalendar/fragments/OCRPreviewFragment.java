package com.example.eiga_.readingcalendar.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eiga_.readingcalendar.R;
import com.example.eiga_.readingcalendar.utils.MyContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OCRPreviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OCRPreviewFragment extends Fragment {
    private static OCRPreviewFragment fragment;
    public static final String TESS_TWO_BROADCAST_KEY = "TESS_TWO_INTENT_SERVICE";
    private String OCRData;
    private localBroadcastReceiver localBroadcastReceiver = null;
    private TextView OCRPreview;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public OCRPreviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OCRPreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OCRPreviewFragment newInstance() {
        fragment = new OCRPreviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ブロードキャストを設定
        if(this.localBroadcastReceiver == null) {
            // インテントフィルター
            IntentFilter intentFilter = new IntentFilter(OCRPreviewFragment.TESS_TWO_BROADCAST_KEY);
            // ローカルブロードキャストを受け取るレシーバー
            this.localBroadcastReceiver = new localBroadcastReceiver();
            // ローカルブロードキャストを設定。
            LocalBroadcastManager.getInstance(MyContext.getContext()).registerReceiver(this.localBroadcastReceiver,intentFilter);
        }
        Bundle args = getArguments();
        if (args != null) {
            if(args.containsKey("OCR_DATA")) {
                OCRData = args.getString("OCR_DATA");
            } else {
                OCRData = "読み込み中";
            }
        } else {
            newInstance();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ocr_preview, null);

        OCRPreview = view.findViewById(R.id.tess_two_test);
        OCRPreview.setText(OCRData);
        return view;

    }

    public class localBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String OCRData = intent.getStringExtra("OCR_DATA");
            Bundle args = getArguments();
            if (OCRData != null){
                if (args != null) {
                    args.putString("OCR_DATA", OCRData);
                    fragment.setArguments(args);
                } else {
                    newInstance();
                }
                OCRPreview.setText(OCRData);
            }
        }
    }
}
