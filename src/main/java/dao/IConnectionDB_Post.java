package dao;

import model.Post;

import java.sql.SQLException;
import java.util.List;

public interface IConnectionDB_Post {
    public void insertPost(Post post) throws SQLException;

    public Post selectPost(int id);

    public boolean updatePost(Post post) throws SQLException;

    public boolean deletePost(int id) throws SQLException;

    public List<Post> selectAllPost();
}
