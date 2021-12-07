package com.example.myreading.Entity;

public class SentenceData extends BaseData{
    private int SentenceId;
    private String SentenceContent;
    private String Title;
    private int Type=1;

    public int getSentenceId() {
        return SentenceId;
    }

    public void setSentenceId(int sentenceId) {
        SentenceId = sentenceId;
    }

    public String getSentenceContent() {
        return SentenceContent;
    }

    public void setSentenceContent(String sentenceContent) {
        SentenceContent = sentenceContent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getType(){
        return Type;
    }

   /* public SentenceData(String sentenceSource, String sentenceContent) {  //这里只采用用户名和密码
        super();
      sentenceSource=sentenceSource;
      sentenceContent=sentenceContent;
    }

    */
}
