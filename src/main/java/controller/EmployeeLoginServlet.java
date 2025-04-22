package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.EmployeeLoginDAO;
import dto.EmployeeDTO;

@WebServlet("/EmployeeLoginServlet")
public class EmployeeLoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 文字コード設定
        request.setCharacterEncoding("UTF-8");
        
        String userId = request.getParameter("user_id");
        String password = request.getParameter("password");
        
//        System.out.println(userId);
//        System.out.println(password);

        EmployeeLoginDAO loginDAO = new EmployeeLoginDAO();
        EmployeeDTO employee = null;
        
        try {
            // ユーザーIDとパスワードで認証処理
            employee = loginDAO.findByUserIdAndPassword(userId, password);
            

            
            if (employee != null) {
//            	  System.out.println("employee != null");
                // 認証成功 → セッションにユーザー情報を保存
                HttpSession session = request.getSession();
                session.setAttribute("employee", employee);

                session.setAttribute("employeeName", employee.getName());
                session.setAttribute("employeeId", employee.getEmployeeId());
                session.setAttribute("employeeDepartment", employee.getDepartment());
                session.setAttribute("employeePosition", employee.getPosition());
                session.setAttribute("employmentType", employee.getEmploymentType());
                session.setAttribute("userId", employee.getUserId());

                // ログイン後のページにリダイレクト
                response.sendRedirect(request.getContextPath() + "/mainJsp/employeeMenu.jsp");
                
                
            } else {
                // 認証失敗 -> エラーメッセージを設定してログインページに戻す
                String errorMsg = "ユーザーIDまたはパスワードが間違っています。";
                request.setAttribute("error", errorMsg);  // エラーメッセージをリクエストにセット
                request.getRequestDispatcher("/mainJsp/employeeLogin.jsp").forward(request, response);  // login.jspにフォワード
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String errorMsg = "システムエラーが発生しました。";
            request.setAttribute("error", errorMsg);  // エラーメッセージをセット
            request.getRequestDispatcher("/mainJsp/employeeLogin.jsp").forward(request, response);
        }
    }
}
