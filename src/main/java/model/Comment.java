package model;

public class Comment {
    private int id_comment;
    private int id_post;
    private int id_account;
    private String content;

    public Comment(int id_comment, int id_post, int id_account, String content) {
        this.id_comment = id_comment;
        this.id_post = id_post;
        this.id_account = id_account;
        this.content = content;
    }

    public int getId_comment() {
        return id_comment;
    }

    public void setId_comment(int id_comment) {
        this.id_comment = id_comment;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
