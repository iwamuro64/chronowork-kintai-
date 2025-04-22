<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // セッションにユーザー情報が保存されているかチェック
    if (session.getAttribute("adminName") != null) {  // セッション属性名はadminNameに変更
        response.sendRedirect("adminMenu.jsp");  // ログイン済みの場合のリダイレクト先
    } else {
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者用ログイン</title>
    <link rel="stylesheet" href="/css/style.css">  <!-- CSSファイルのパス修正 -->
</head>
<body>
    <div class="header">
        <jsp:include page="../inc/adminHeader.jsp" />
    </div>
    
    <div class="menu">
        <div class="main_frame">
            <p>管理者用ログイン画面</p>
        </div>
    </div>
    
    <div class="main_wrapper">
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
        <!-- ログインフォーム -->
        <form action="${pageContext.request.contextPath}/AdminLoginServlet" method="post">
            <table class="login_table">
                <tr>
                    <td><label for="admin_name">管理者名</label></td>
                    <td><input type="text" id="admin_name" name="admin_name" placeholder="管理者名" required></td>
                </tr>
                <tr>
                    <td><label for="password">パスワード</label></td>
                    <td><input type="password" id="password" name="password" placeholder="パスワード" required></td>
                </tr>
                <tr>
                    <td colspan="2" class="submit_button">
                        <input type="submit" value="ログイン">
                    </td>
                </tr>
            </table>
        </form>
    </div>
    
    <!-- 戻るボタン -->
    <div class="button">
        <input type="button" value="TOP" onclick="window.location.href='index.jsp'"> <!-- TOPに戻るボタン -->
    </div>

    <div class="footer_design">
        <footer>
            <small>© 2025 CHRONOWORK.</small>
        </footer>
    </div>
</body>
</html>

<% } %>  <!-- セッションチェックのifブロック終了 -->
