package com.example.mobilecbtexam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sherif146 on 10/06/2017.
 */
public class profileImage extends Fragment {
    ImageView prof_pics;
    byte [] image_data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profileimage, container, false);
        prof_pics = (ImageView) rootView.findViewById(R.id.diaprofile_pic);

        Intent intent = getActivity().getIntent();
        image_data = intent.getByteArrayExtra("PROF_PICS");

        Bitmap bitmap = BitmapFactory.decodeByteArray(image_data,0,image_data.length);
        prof_pics.setImageBitmap(bitmap);
        return rootView;
    }
}
