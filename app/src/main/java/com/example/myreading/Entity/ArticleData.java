package com.example.myreading.Entity;

public class ArticleData extends BaseData{
    private int ArticleId;
    private String ArticleChapter;
    private String Title;
    private String ArticleContent;
    private String ArticleNote;
    private String ArticleAuthor;
    private String ArticleTranslation;
    private int Type=3;

    public int getArticleId() {
        return ArticleId;
    }

    public void setArticleId(int articleId) {
        ArticleId = articleId;
    }

    public String getArticleChapter() {
        return ArticleChapter;
    }

    public void setArticleChapter(String articleChapter) {
        ArticleChapter = articleChapter;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getArticleContent() {
        return ArticleContent;
    }

    public void setArticleContent(String articleContent) {
        ArticleContent = articleContent;
    }

    public String getArticleNote() {
        return ArticleNote;
    }

    public void setArticleNote(String articleNote) {
        ArticleNote = articleNote;
    }

    public String getArticleAuthor() {
        return ArticleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        ArticleAuthor = articleAuthor;
    }

    public String getArticleTranslation() {
        return ArticleTranslation;
    }

    public void setArticleTranslation(String articleTranslation) {
        ArticleTranslation = articleTranslation;
    }
    public int getType(){
        return Type;
    }
}
