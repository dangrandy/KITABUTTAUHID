package com.papa.razzi.kitabtauhid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DataModelAdapter extends ArrayAdapter<DataModel> {
    public DataModelAdapter(Context context, ArrayList<DataModel> musics) {
        super(context,0,musics);
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View listitemview = convertView;
        if (listitemview == null) {
            listitemview = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent, false);
        }
        DataModel currentmodel = getItem(position);
        TextView tvMusic = listitemview.findViewById(R.id.tv_music);
        tvMusic.setText(currentmodel.getMusicName());
        return listitemview;
    }
}
