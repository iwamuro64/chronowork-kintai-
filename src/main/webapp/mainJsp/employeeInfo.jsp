<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.EmployeeSearchDTO" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>従業員情報表示</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
	<!-- セッションからユーザー情報を取得して表示 -->
    <%
        String name = (String) session.getAttribute("employeeName");
        if (name == null) {
            response.sendRedirect("employeeLogin.jsp"); // セッションが無効な場合、login.jspにリダイレクト
            return;
        }
    %>
    
    <header>
		<div class="header">
			<!-- ヘッダーを挿入 -->
			<jsp:include page="../inc/employeeHeader.jsp" />
		</div>
	</header>
	
    <div class="menu-container">
        <div class="main_frame">
            <h2>従業員情報</h2>

            <!-- 従業員情報の表示 -->
            <table border="1" class="center-table">
                <tr>
                    <th>名前</th>
                    <td><%= session.getAttribute("employeeName") %></td>
                </tr>
                <tr>
                    <th>部署</th>
                    <td><%= session.getAttribute("employeeDepartment") %></td>
                </tr>
                <tr>
                    <th>役職</th>
                    <td><%= session.getAttribute("employeePosition") %></td>
                </tr>
                <tr>
                    <th>雇用形態</th>
                    <td><%= session.getAttribute("employmentType") %></td>
                </tr>
                <tr>
                    <th>ユーザーID</th>
                    <td><%= session.getAttribute("userId") %></td>
                </tr>
            </table>

            <br>
            <br>

            <!-- 従業員メニューに戻るボタン -->
            <form action="${pageContext.request.contextPath}/mainJsp/employeeMenu.jsp" method="get">
                <input type="submit" value="戻る" class="mini-btn">
            </form>
        </div>
    </div>

    <div class="footer_design">
        <footer>
            <small>© 2025 CHRONOWORK.</small>
        </footer>
    </div>
</body>
</html>
