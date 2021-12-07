package com.example.myreading.Entity;

import java.io.Serializable;

public class PoemData extends BaseData implements Serializable {
    private int poemId;
    private String Title;
    private String PoemAuthor;
    private String PoemKind;
    private String PoemContent;
    private String PoemIntro;
    private int Type=2;



    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getPoemContent() {
        return PoemContent;
    }

    public void setPoemContent(String poemContent) {
        PoemContent = poemContent;
    }

    public String getPoemIntro() {
        return PoemIntro;
    }

    public void setPoemIntro(String poemIntro) {
        PoemIntro = poemIntro;
    }

    public int getPoemId() {
        return poemId;
    }

    public void setPoemId(int poemId) {
        this.poemId = poemId;
    }

    public String getPoemAuthor() {
        return PoemAuthor;
    }

    public void setPoemAuthor(String poemAuthor) {
        PoemAuthor = poemAuthor;
    }

    public String getPoemKind() {
        return PoemKind;
    }

    public void setPoemKind(String poemKind) {
        PoemKind = poemKind;
    }
    public int getType(){
        return Type;
    }
}
