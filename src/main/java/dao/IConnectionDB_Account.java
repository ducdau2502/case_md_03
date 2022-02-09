package dao;

import model.Account;
import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface IConnectionDB_Account {
    public void insertAccount(Account account) throws SQLException;

    public List<Account> selectAllAccount();

    public boolean blockAccount(int id) throws SQLException;

    public boolean unblockAccount(int id) throws SQLException;

    public Account checkLogin(String username, String password);

    Account getAccountById(int id);
}
