package com.example.root.testgit;

import static android.R.attr.x;
import static com.example.root.testgit.R.id.question;

/**
 * Created by Jakob on 22.11.16.
 */

public class Comment {

   private String text,author,id;
    private int time;

    public Comment(String text, String author, String id, int time)
    {
        this.text = text;
        this.author = author;
        this.id = id;
        this.time = time;
    }

    public void displayComments()
    {
        System.out.println("Frage: " + this.text);
        System.out.println("->Antwort 1: " + this.author);
        System.out.println("->Antwort 2: " + this.id);
        System.out.println("->Pro: " + this.time);
    }
    /// ------------------------------------------------------
    /// --------- GETer and SETer ----------------------------
    /// ------------------------------------------------------
    public String getText(){
        return this.text;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getId(){
        return this.id;
    }
    /// ------------------------------------------------------
}
