package dao;

import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface IConnectionDB_Category {
    public void insertCategory(Category category) throws SQLException;

    public Category selectCategory(int id);

    public boolean updateCategory(Category category) throws SQLException;

    public boolean deleteCategory(int id) throws SQLException;

    public List<Category> selectAllCategory();
}
