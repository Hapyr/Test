package com.example.root.testgit;

import static android.R.attr.x;
import static com.example.root.testgit.R.id.question;

/**
 * Created by Jakob on 22.11.16.
 */

public class Question {

    private int id;
    private String question;
    private String answer1, answer2;
    private String[] requestion;
    private int yesVote,noVote;
    private int voteWeight;

    public Question(String ques, String ans1, String ans2)
    {
        this.question = ques;
        this.answer1 = ans1;
        this.answer2 = ans2;
        this.id = 0;
    }

    public void displayQuestion()
    {
        System.out.println("Frage: " + this.question);
        System.out.println("->Antwort 1: " + this.answer1);
        System.out.println("->Antwort 2: " + this.answer2);
    }

    /// ------------------------------------------------------
    /// --------- GETer and SETer ----------------------------
    /// ------------------------------------------------------
    public int getId() {return this.id; }
    public String getQuestion(){
        return this.question;
    }
    public String getAnswer1(){
        return this.answer1;
    }
    public String getAnswer2(){
        return this.answer2;
    }
    public String[] getRequestion() {
        return this.requestion;
    }
    public int getYesVote() {
        return this.yesVote;
    }
    public int getNoVote() {
        return this.noVote;
    }
    public int getVoteWeight() {
        return this.voteWeight;
    }
    /// ------------------------------------------------------
}
