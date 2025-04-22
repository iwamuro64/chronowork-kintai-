<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>従業員追加確認</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="header">
        <jsp:include page="../inc/adminHeader.jsp" />
    </div>

    <div class="menu">
        <div class="main_frame">
            <h2>従業員追加完了</h2>
            <%
                String success = request.getParameter("success");
                if ("true".equals(success)) {
            %>
                    <p style="color: green;">従業員が正常に追加されました。</p>
            <%
                } else {
            %>
                    <p style="color: red;">従業員の追加に失敗しました。もう一度お試しください。</p>
            <%
                }
            %>
            <a href="adminMenu.jsp">管理画面に戻る</a>
        </div>
    </div>

    <div class="footer_design">
        <footer>
            <small>© 2025 CHRONOWORK.</small>
        </footer>
    </div>
</body>
</html>
