package com.example.eiga_.readingcalendar.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eiga_.readingcalendar.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CameraPreviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraPreviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    Uri ImageUri;
    private Uri imageUri;

    public CameraPreviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CameraPreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraPreviewFragment newInstance(String imageUriString) {
        CameraPreviewFragment fragment = new CameraPreviewFragment();
        Bundle args = new Bundle();
        args.putString("IMAGE_URI", imageUriString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        imageUri = Uri.parse(args.getString("IMAGE_URI"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera_preview, null);
        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.fragment_camera_constraintlayout);
        // イメージビューワ
        ImageView cameraPreview = view.findViewById(R.id.camera_preview);
        // cameraPreviewに画像を設定。
        cameraPreview.setImageURI(imageUri);

        return view;

    }

}
