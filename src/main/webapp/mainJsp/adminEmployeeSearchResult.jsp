<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dto.EmployeeSearchDTO" %> <!-- パッケージ名を修正 -->

<%
    // セッションから管理者名を取得
    String adminName = (String) session.getAttribute("adminName");

    // ログインしていない場合はログイン画面へリダイレクト
    if (adminName == null) {
        response.sendRedirect("mainJsp/adminLogin.jsp");
        return;
    }

    // 検索結果を取得
    List<EmployeeSearchDTO> employees = (List<EmployeeSearchDTO>) request.getAttribute("employees");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>従業員検索結果</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="header">
        <jsp:include page="../inc/adminHeader.jsp" />
    </div>

    <div class="menu">
        <div class="main_frame">
            <h2>従業員検索結果</h2>

            <%-- 検索結果表示 --%>
            <%
                if (employees != null && !employees.isEmpty()) {
            %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>従業員ID</th>
                            <th>名前</th>
                            <th>部署</th>
                            <th>役職</th>
                            <th>雇用形態</th>
                            <th>ユーザーID</th>
                            <th>編集</th>
                            <th>削除</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (EmployeeSearchDTO employee : employees) { %>
                        <tr>
                            <td><%= employee.getEmployeeId() %></td>
                            <td><%= employee.getName() %></td>
                            <td><%= employee.getDepartment() %></td>
                            <td><%= employee.getPosition() %></td>
                            <td><%= employee.getEmploymentType() %></td>
                            <td><%= employee.getUserId() %></td>
                            <td>
                                <!-- 編集ボタン -->
                                <form action="${pageContext.request.contextPath}/AdminEmployeeEditServlet" method="get">
                                    <input type="hidden" name="employeeId" value="<%= employee.getEmployeeId() %>">
                                    <input type="submit" value="編集" class="button">
                                </form>
                            </td>
                            <td>
                                <!-- 削除ボタン -->
                                <form action="${pageContext.request.contextPath}/AdminEmployeeDeleteServlet" method="post" onsubmit="return confirm('本当に削除しますか？');">
                                    <input type="hidden" name="employeeId" value="<%= employee.getEmployeeId() %>">
                                    <input type="submit" value="削除" class="button">
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <%
                } else {
            %>
                <p>該当する従業員は見つかりませんでした。</p>
            <% 
                }
            %>
            
            <!-- 戻るボタン -->
            <form action="${pageContext.request.contextPath}/mainJsp/adminEmployeeSearch.jsp" method="get">
                <input type="submit" value="検索ページに戻る" class="button">
            </form>

            <!-- TOPボタン -->
            <form action="${pageContext.request.contextPath}/mainJsp/adminMenu.jsp" method="get">
                <input type="submit" value="TOPに戻る" class="button">
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
