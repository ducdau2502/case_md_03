package dao;

import connection.MyConnection;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDBOf_Category implements IConnectionDB_Category {
    private static final String INSERT_CATEGORY_SQL = "insert into category (name_category, description) value (?, ?);";
    private static final String UPDATE_CATEGORY_SQL = "update category set name_category = ?, description = ? where id_category = ?;";
    private static final String DELETE_CATEGORY_SQL = "delete from category where id_category = ?;";
    private static final String SELECT_ALL_CATEGORY = "select * from category";
    private static final String SELECT_CATEGORY_BY_ID = "select * from category where id_category = ?";

    public ConnectionDBOf_Category() {
    }

    private final MyConnection myConnection = new MyConnection();

    @Override
    public void insertCategory(Category category) throws SQLException {
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_SQL)) {
            preparedStatement.setString(1, category.getName_category());
            preparedStatement.setString(2, category.getDescription());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category selectCategory(int id) {
        Category category = null;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            System.out.println(preparedStatement);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name_category = rs.getString("name_category");
                String description = rs.getString("description");
                category = new Category(id, name_category, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CATEGORY_SQL)) {
            statement.setString(1, category.getName_category());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getId_category());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public boolean deleteCategory(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY_SQL)) {
            statement.setInt(1, id);

            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public List<Category> selectAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORY)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id_category = rs.getInt("id_category");
                String name_category = rs.getString("name_category");
                String description = rs.getString("description");
                categoryList.add(new Category(id_category, name_category, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
}
