package controller;

import dao.ConnectionDBOf_Account;
import dao.ConnectionDBOf_Category;
import dao.ConnectionDBOf_Post;
import model.Account;
import model.Category;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminServlet", urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    ConnectionDBOf_Account connectionDBOf_account = new ConnectionDBOf_Account();
    ConnectionDBOf_Category connectionDBOf_category = new ConnectionDBOf_Category();
    ConnectionDBOf_Post connectionDBOf_post = new ConnectionDBOf_Post();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    private void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "block":
                    blockAccount(request, response);
                    break;
                case "unblock":
                    unblockAccount(request, response);
                    break;
                case "createGet_Category":
                    createGet_Category(request, response);
                    break;
                case "createPost_Category":
                    createPost_Category(request, response);
                    break;
                case "displayCategory":
                    displayAllCategory(request, response);
                    break;
                default:
                    displayAllUser(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayAllCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/view_category.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createPost_Category(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name_category = request.getParameter("name_category");
        String description = request.getParameter("description");
        Category category = new Category(name_category, description);
        connectionDBOf_category.insertCategory(category);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/create_category.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createGet_Category(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/create_category.jsp");
        requestDispatcher.forward(request, response);
    }

    private void unblockAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idUnblock = Integer.parseInt(request.getParameter("id"));
        connectionDBOf_account.unblockAccount(idUnblock);
        displayAllUser(request, response);
    }

    private void blockAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idBlock = Integer.parseInt(request.getParameter("id"));
        connectionDBOf_account.blockAccount(idBlock);
        displayAllUser(request, response);
    }

    private void displayAllUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Account> accounts = connectionDBOf_account.selectAllAccount();
        request.setAttribute("accounts", accounts);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/home_admin.jsp");
        requestDispatcher.forward(request, response);
    }
}
