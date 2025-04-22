<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String name = (String) session.getAttribute("employeeName");
%>

<header class="header-container">
    <div class="logo-container">
        <form action="${pageContext.request.contextPath}/mainJsp/employeeMenu.jsp" method="get">
            <button type="submit" class="logo-button">
                <img src="${pageContext.request.contextPath}/images/logo2wide.jpg" alt="従業員メニューへ" class="logo-img">
            </button>
        </form>
    </div>

    <div class="spacer"></div> <!-- 空白スペース用 -->

    <div class="auth-button-container">
        <%
            if (name != null) {
        %>
            <!-- ログアウトボタン -->
            <form action="${pageContext.request.contextPath}/EmployeeLogoutServlet" method="post">
                <button type="submit" class="logout-button">ログアウト</button>
            </form>
        <%
            } else {
        %>
            <!-- ログインボタン -->
            <form action="${pageContext.request.contextPath}/mainJsp/employeeLogin.jsp" method="get">
                <button type="submit" class="login-button">ログイン</button>
            </form>
        <%
            }
        %>
    </div>
</header>
