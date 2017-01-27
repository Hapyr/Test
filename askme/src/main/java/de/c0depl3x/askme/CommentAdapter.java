package de.c0depl3x.askme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Luis on 23.11.2016.
 */

class CommentAdapter extends ArrayAdapter<Comment> {

    final Context context;
    final ArrayList<Comment> cList;

    public CommentAdapter(Context context, ArrayList<Comment> cItems) {
        super(context, R.layout.comment_frame, cItems);
        this.context = context;
        this.cList = cItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentList) {
        LayoutInflater listInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = listInflater.inflate(R.layout.comment_frame, parentList, false);

        TextView qQuestion = (TextView) item.findViewById(R.id.qComment);
        qQuestion.setText(this.cList.get(position).getText());

        return item;
    }



    @Override
    public Comment getItem(int position) {
        return cList.get(position);
    }
}
