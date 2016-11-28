package com.example.root.testgit;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Luis on 23.11.2016.
 */

class QuestionAdapter extends ArrayAdapter<Question> {

    final Context context;
    final ArrayList<Question> qList;

    public QuestionAdapter(Context context, ArrayList<Question> qItems) {
        super(context, R.layout.question_frame, qItems);
        this.context = context;
        this.qList = qItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentList) {
        LayoutInflater listInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = listInflater.inflate(R.layout.question_frame, parentList, false);

        TextView qQuestion = (TextView) item.findViewById(R.id.qQuestion);
        TextView qAnsLeft = (TextView) item.findViewById(R.id.qAnsLeft);
        TextView qAnsRight = (TextView) item.findViewById(R.id.qAnsRight);

        ProgressBar proBar = (ProgressBar) item.findViewById(R.id.progressBar);
        int state = (int)(int)Math.round(Math.random() * (100 - 1)+ 1);
        proBar.setProgress(getVoteState(position));

        qQuestion.setText(this.qList.get(position).getQuestion());
        qAnsLeft.setText(this.qList.get(position).getAnswer1());
        qAnsRight.setText(this.qList.get(position).getAnswer2());

        return item;
    }

    public int getVoteState(int position){
        return (int)(100*((float)(this.qList.get(position).getPro()))/((float)(this.qList.get(position).getPro() + this.qList.get(position).getContra())));
    }


    @Override
    public Question getItem(int position) {
        return qList.get(position);
    }
}
