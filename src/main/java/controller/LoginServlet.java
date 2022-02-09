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

    private void registerPost(HttpServletRequest request, HttpServletResponse response) {
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
                    requestDispatcher = request.getRequestDispatcher("/view/home_view.jsp");
                } else if (account.getRollno() == 0) {
                    requestDispatcher = request.getRequestDispatcher("/admin");
                }
            } else {
                String messLogin = "Your account has been blocked";
                request.setAttribute("messLogin", messLogin);
                requestDispatcher = request.getRequestDispatcher("/login/sign_in.jsp");
            }
        } else {
            String messLogin = "Login Failed";
            request.setAttribute("messLogin", messLogin);
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
