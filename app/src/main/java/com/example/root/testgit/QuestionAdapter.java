package com.example.root.testgit;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        proBar.setProgress(getVoteState(position));
        //proBar.setVisibility(View.INVISIBLE);

        qQuestion.setText(this.qList.get(position).getQuestion());
        qAnsLeft.setText(this.qList.get(position).getAnswer1());
        qAnsRight.setText(this.qList.get(position).getAnswer2());


        // -----------------------------------------------------------------|
        // ----------- Button Votes ----------------------------------------|
        // -----------------------------------------------------------------.
        Button proVote = (Button) item.findViewById(R.id.button5);
        proVote.setTag(position);
        proVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int pos = (Integer)arg0.getTag();
                qList.get(pos).setVote(true);
            }});
        Button contraVote = (Button) item.findViewById(R.id.button4);
        contraVote.setTag(position);
        contraVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int pos = (Integer)arg0.getTag();
                qList.get(pos).setVote(false);
            }});
        // -----------------------------------------------------------------.
        // -----------------------------------------------------------------|


        item.setOnTouchListener(new View.OnTouchListener() {
            private int distance;
            private int startX;
            private int currentX;
            private RecyclerView.ViewHolder holder;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) motionEvent.getX();
                    currentX = startX;
                    distance = 0;
                    holder = (RecyclerView.ViewHolder) view.getTag();
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    currentX = (int) motionEvent.getX();
                    distance = currentX - startX;
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    distance = 0;
                }

                if (distance > 300) {
                    distance = 0;
                    view.setBackgroundResource(R.color.ProgressBarBackground);
                } else if (distance < -300) {
                    distance = 0;
                    view.setBackgroundResource(R.color.ProgressBarStatus);
                }

                view.setPadding(distance, 0, -1*distance, 0);

                return true;
            }
        });
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
