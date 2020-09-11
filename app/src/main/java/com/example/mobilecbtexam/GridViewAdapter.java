package com.example.mobilecbtexam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sherif146 on 06/04/2017.
 */
public class GridViewAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Food> foodsList;

    public GridViewAdapter(Context context, int layout, List<Food> foodsList){
        //super(context, layout, foodsList);
        this.foodsList = foodsList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount(){
        return foodsList.size();
    }

    @Override
    public Object getItem(int position){
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position){
        return foodsList.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView iv;
        TextView txtName,txtUsername;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        //View row = view;
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item,null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtUsername = (TextView) convertView.findViewById(R.id.txtUsername);
            holder.iv = (ImageView) convertView.findViewById(R.id.profile_pic);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Food food = foodsList.get(position);

        holder.txtName.setText(food.getName());
        holder.txtUsername.setText(food.getUsername());
        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage,0,foodImage.length);
        holder.iv.setImageBitmap(bitmap);
        return convertView;
    }

}
