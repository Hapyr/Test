package com.example.root.testgit;

import static android.R.attr.x;
import static com.example.root.testgit.R.id.question;

/**
 * Created by Jakob on 22.11.16.
 */

public class Question {

    private String question;
    private String answer1, answer2, author_id;
    private int pro, contra, weight, time, id;
    private String[] requestion;
    private int yesVote,noVote;
    private int voteWeight;

    public Question(String ques, String ans1, String ans2, int pro, int contra, int weight, int time, String author_id, int id)
    {
        this.question = ques;
        this.answer1 = ans1;
        this.answer2 = ans2;
        this.author_id = author_id;
        this.pro = pro;
        this.contra = contra;
        this.time = time;
        this.id = id;
    }

    public void displayQuestion()
    {
        System.out.println("Frage: " + this.question);
        System.out.println("->Antwort 1: " + this.answer1);
        System.out.println("->Antwort 2: " + this.answer2);
        System.out.println("->Pro: " + this.pro);
        System.out.println("->Contra: " + this.contra);
        System.out.println("->Weight: " + this.weight);
        System.out.println("->Time: " + this.time);
        System.out.println("->Author ID: " + this.author_id);
        System.out.println("->ID: " + this.id);
    }

    /// ------------------------------------------------------
    /// --------- GETer and SETer ----------------------------
    /// ------------------------------------------------------
    public String getQuestion(){
        return this.question;
    }
    public String getAnswer1(){
        return this.answer1;
    }
    public String getAnswer2(){
        return this.answer2;
    }
    public int getPro(){
        return this.pro;
    }
    public int getContra(){
        return this.contra;
    }
    public int getWeight(){
        return this.weight;
    }
    public int getTime(){
        return this.time;
    }
    public String getAuthorID(){
        return this.author_id;
    }
    public int getID(){
        return this.id;
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
