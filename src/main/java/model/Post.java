package model;

import java.util.Date;

public class Post {
    private int id_post;
    private String title;
    private String content;
    private Date date_created;
    private int id_account;
    private int id_category;

    public Post(int id_post, String title, String content, Date date_created, int id_account, int id_category) {
        this.id_post = id_post;
        this.title = title;
        this.content = content;
        this.date_created = date_created;
        this.id_account = id_account;
        this.id_category = id_category;
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }
}
