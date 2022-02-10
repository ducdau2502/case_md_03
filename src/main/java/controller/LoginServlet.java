package controller;

import dao.ConnectionDBOf_Account;
import dao.ConnectionDBOf_Post;
import model.Account;
import model._ListOfPost;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    ConnectionDBOf_Account connectionDBOf_account = new ConnectionDBOf_Account();
    ConnectionDBOf_Post connectionDBOf_post = new ConnectionDBOf_Post();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    protected void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "loginGet":
                    loginGet(request, response);
                    break;
                case "loginPost":
                    loginPost(request, response);
                    break;
                case "logout":
                    logout(request, response);
                case "registerGet":
                    registerGet(request, response);
                    break;
                case "registerPost":
                    registerPost(request, response);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = connectionDBOf_account.logout();
        request.setAttribute("account", account);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view/home_view.jsp");
        requestDispatcher.forward(request, response);
    }

    private void registerPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String re_password = request.getParameter("re_password");
        String phoneNumber = request.getParameter("phonenumber");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        Account account;
        RequestDispatcher requestDispatcher;
        if (connectionDBOf_account.checkRegister(username, password, phoneNumber)) {
            if (password.equals(re_password)) {
                if (connectionDBOf_account.checkExitingAccount(username, email)) {
                    account = new Account(username, password, phoneNumber, email, address);
                    connectionDBOf_account.insertAccount(account);
                    String messLogin1 = "Your account has been created";
                    request.setAttribute("messLogin1", messLogin1);
                    String messLogin2 = "Please login your account";
                    request.setAttribute("messLogin2", messLogin2);
                    requestDispatcher = request.getRequestDispatcher("/login/sign_in.jsp");
                } else {
                    String messLogin1 = "Register Failed";
                    request.setAttribute("messLogin1", messLogin1);
                    String messLogin2 = "username or email have been existed";
                    request.setAttribute("messLogin2", messLogin2);
                    requestDispatcher = request.getRequestDispatcher("/login/register.jsp");
                }
            } else {
                String messLogin1 = "Register Failed";
                request.setAttribute("messLogin1", messLogin1);
                String messLogin2 = "Re password not match";
                request.setAttribute("messLogin2", messLogin2);
                requestDispatcher = request.getRequestDispatcher("/login/register.jsp");
            }
        } else {
            String messLogin1 = "Register Failed";
            request.setAttribute("messLogin1", messLogin1);
            String messLogin2 = "Input miss match";
            request.setAttribute("messLogin2", messLogin2);
            requestDispatcher = request.getRequestDispatcher("/login/register.jsp");
        }

        List<_ListOfPost> listOfPosts = connectionDBOf_post.selectListOfPost();
        request.setAttribute("listOfPosts", listOfPosts);
        requestDispatcher.forward(request, response);
    }

    private void registerGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<_ListOfPost> listOfPosts = connectionDBOf_post.selectListOfPost();
        request.setAttribute("listOfPosts", listOfPosts);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/register.jsp");
        requestDispatcher.forward(request, response);
    }

    private void loginPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<_ListOfPost> listOfPosts = connectionDBOf_post.selectListOfPost();
        request.setAttribute("listOfPosts", listOfPosts);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Account account = connectionDBOf_account.checkLogin(username, password);
        RequestDispatcher requestDispatcher = null;
        if (account != null) {
            if (account.getStatus() == 1) {
                request.setAttribute("account", account);
                if (account.getRollno() == 1) {
                    String messLogin1 = "Login Successful!!!";
                    request.setAttribute("messLogin1", messLogin1);
                    String messLogin2 = "Enjoy my 4rum";
                    request.setAttribute("messLogin2", messLogin2);
                    requestDispatcher = request.getRequestDispatcher("/view/home_view.jsp");
                } else if (account.getRollno() == 0) {
                    String messLogin1 = "Login Successful!!!";
                    request.setAttribute("messLogin1", messLogin1);
                    String messLogin2 = "Welcome to Admin page";
                    request.setAttribute("messLogin2", messLogin2);
                    requestDispatcher = request.getRequestDispatcher("/admin");
                }
            } else {
                String messLogin1 = "Login Failed!";
                request.setAttribute("messLogin1", messLogin1);
                String messLogin2 = "Your account has been blocked";
                request.setAttribute("messLogin2", messLogin2);
                requestDispatcher = request.getRequestDispatcher("/login/sign_in.jsp");
            }
        } else {
            String messLogin1 = "Login Failed!";
            request.setAttribute("messLogin1", messLogin1);
            String messLogin2 = "Please try again";
            request.setAttribute("messLogin2", messLogin2);
            requestDispatcher = request.getRequestDispatcher("/login/sign_in.jsp");
        }

        assert requestDispatcher != null;
        requestDispatcher.forward(request, response);
    }

    private void loginGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<_ListOfPost> listOfPosts = connectionDBOf_post.selectListOfPost();
        request.setAttribute("listOfPosts", listOfPosts);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/sign_in.jsp");
        requestDispatcher.forward(request, response);
    }
}
