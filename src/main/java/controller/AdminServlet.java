package controller;

import dao.ConnectionDBOf_Account;
import dao.ConnectionDBOf_Category;
import dao.ConnectionDBOf_Post;
import model.Account;
import model.Category;
import model._ListOfPost;

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
                case "displayAllPost":
                    displayAllPost(request, response);
                    break;
                case "displayPostById_category":
                    displayPostById_category(request, response);
                    break;
                case "deletePost":
                    deletePost(request, response);
                    break;
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
                case "editGet_Category":
                    editGet_Category(request, response);
                    break;
                case "editPost_Category":
                    editPost_Category(request, response);
                    break;
                case "deleteCategory":
                    deleteCategory(request, response);
                    break;
                default:
                    displayAllUser(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPostById_category(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_category = Integer.parseInt(request.getParameter("id"));
        List<_ListOfPost> listOfPosts = connectionDBOf_post.selectListOfPostByIdCategory(id_category);
        request.setAttribute("listOfPosts", listOfPosts);
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/post/view_post.jsp");
        requestDispatcher.forward(request, response);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idDelete = Integer.parseInt(request.getParameter("id"));
        connectionDBOf_post.deletePost(idDelete);

        displayAllPost(request, response);
    }

    private void displayAllPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<_ListOfPost> listOfPosts = connectionDBOf_post.selectListOfPost();
        request.setAttribute("listOfPosts", listOfPosts);
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/post/view_post.jsp");
        requestDispatcher.forward(request, response);
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idDelete = Integer.parseInt(request.getParameter("id"));
        connectionDBOf_category.deleteCategory(idDelete);
        displayAllCategory(request, response);
    }

    private void editPost_Category(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id_category = Integer.parseInt(request.getParameter("id"));
        String name_category = request.getParameter("name_category");
        String description = request.getParameter("description");
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        Category category = new Category(id_category, name_category, description);
        connectionDBOf_category.updateCategory(category);
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/category/edit_category.jsp");
        requestDispatcher.forward(request, response);
    }

    private void editGet_Category(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id_category = Integer.parseInt(request.getParameter("id"));
        Category categoryEdit = connectionDBOf_category.selectCategory(id_category);
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/category/edit_category.jsp");
        request.setAttribute("category", categoryEdit);
        requestDispatcher.forward(request, response);
    }

    private void displayAllCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/category/view_category.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createPost_Category(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name_category = request.getParameter("name_category");
        String description = request.getParameter("description");
        Category category = new Category(name_category, description);
        connectionDBOf_category.insertCategory(category);
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/category/create_category.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createGet_Category(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/category/create_category.jsp");
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
        List<Category> categoryList = connectionDBOf_category.selectAllCategory();
        request.setAttribute("categoryList", categoryList);
        if (checkAccount(request) != null) {
            request.setAttribute("account", checkAccount(request));
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/home_admin.jsp");
        requestDispatcher.forward(request, response);
    }

    private Account checkAccount(HttpServletRequest request) {
        if ((request.getParameter("account_id")) != null) {
            int id = Integer.parseInt(request.getParameter("account_id"));
            return connectionDBOf_account.getAccountById(id);
        }
        return null;
    }
}
