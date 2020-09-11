package com.example.mobilecbtexam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sherif146 on 16/04/2017.
 */
public class ViewUserAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<cbtExam> cbtList;

    public ViewUserAdapter(Context context, int layout, List<cbtExam> cbtList){
        //super(context, layout, foodsList);
        this.cbtList = cbtList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount(){
        return cbtList.size();
    }

    @Override
    public Object getItem(int position){
        return cbtList.get(position);
    }

    @Override
    public long getItemId(int position){
        return cbtList.indexOf(getItem(position));
    }

    private class ViewHolder {
        TextView txtDateExam,txtScore,txtPercentage,txtDuration;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        //View row = view;
        ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {

            convertView = inflater.inflate(R.layout.user_item,null);
            holder = new ViewHolder();

            holder.txtDateExam = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtScore = (TextView) convertView.findViewById(R.id.txtScore);
            holder.txtPercentage = (TextView) convertView.findViewById(R.id.txtPercentage);
            holder.txtDuration = (TextView) convertView.findViewById(R.id.txtTime);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        cbtExam cbtExamList = cbtList.get(position);

        holder.txtDateExam.setText(cbtExamList.getDate_exam());
        holder.txtScore.setText(cbtExamList.getCbtScore());
        holder.txtPercentage.setText(cbtExamList.getCbtPercentage());
        holder.txtDuration.setText(cbtExamList.getCbtTime());
        return convertView;
    }
}
