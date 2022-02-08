package model;

import java.util.Date;

public class _ListOfPost {
    private int id_post;
    private String title;
    private String content;
    private String author;
    private Date date_created;
    private String category;

    public _ListOfPost(int id_post, String title, String content, String author, Date date_created, String category) {
        this.id_post = id_post;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date_created = date_created;
        this.category = category;
    }

    public int getId_post() {
        return id_post;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate_created() {
        return date_created;
    }

    public String getCategory() {
        return category;
    }
}
