<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <jsp:include page="../inc/employeeHeader.jsp" />
    </header>

    <main>
        <div class="menu-container">
            <h2>ログイン</h2>
            <form action="${pageContext.request.contextPath}/EmployeeLoginServlet" method="post">
                <div class="form-field">
                    <label for="user_id">ユーザーID：</label>
                    <input type="text" id="user_id" name="user_id" required>
                </div>
                <div class="form-field">
                    <label for="password">パスワード：</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div>
                    <button type="submit" class="mini-btn">ログイン</button>
                </div>
            </form>

            <%-- エラーメッセージ表示 --%>
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div style="color: red;">
                <p><%= error %></p>
            </div>
            <%
                }
            %>

            <!-- 戻るボタン -->
            <form action="${pageContext.request.contextPath}/mainJsp/index.jsp" method="get">
                <button type="submit" class="mini-btn">TOP</button>
            </form>
        </div>
    </main>

    <footer>
        <div style="text-align: center; padding: 10px;">
            <small>© 2025 CHRONOWORK.</small>
        </div>
    </footer>
</body>
</html>
