<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.EmployeeSearchDTO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>従業員情報編集</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="header">
        <jsp:include page="../inc/adminHeader.jsp" />
    </div>

    <div class="menu">
        <div class="main_frame">
            <h2>従業員情報編集</h2>

            <form action="${pageContext.request.contextPath}/AdminEmployeeEditServlet" method="post">
                <input type="hidden" name="employeeId" value="<%= request.getAttribute("employeeId") %>">
                
                <label for="name">名前:</label>
                <input type="text" id="name" name="name" value="<%= request.getAttribute("employeeName") %>" required><br>

                <!-- 部署のプルダウンメニュー -->
                <label for="department">部署:</label>
                <select id="department" name="department" required>
                    <option value="総務部" <%= "総務部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>総務部</option>
                    <option value="人事部" <%= "人事部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>人事部</option>
                    <option value="経理部" <%= "経理部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>経理部</option>
                    <option value="法務部" <%= "法務部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>法務部</option>
                    <option value="広報部" <%= "広報部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>広報部</option>
                    <option value="営業部" <%= "営業部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>営業部</option>
                    <option value="開発部" <%= "開発部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>開発部</option>
                    <option value="CS部" <%= "CS部".equals(request.getAttribute("employeeDepartment")) ? "selected" : "" %>>CS部</option>
                </select><br>

                <!-- 役職のプルダウンメニュー -->
                <label for="position">役職:</label>
                <select id="position" name="position" required>
                    <option value="部長" <%= "部長".equals(request.getAttribute("employeePosition")) ? "selected" : "" %>>部長</option>
                    <option value="課長" <%= "課長".equals(request.getAttribute("employeePosition")) ? "selected" : "" %>>課長</option>
                    <option value="係長" <%= "係長".equals(request.getAttribute("employeePosition")) ? "selected" : "" %>>係長</option>
                    <option value="主任" <%= "主任".equals(request.getAttribute("employeePosition")) ? "selected" : "" %>>主任</option>
                    <option value="リーダー" <%= "リーダー".equals(request.getAttribute("employeePosition")) ? "selected" : "" %>>リーダー</option>
                    <option value="その他" <%= "その他".equals(request.getAttribute("employeePosition")) ? "selected" : "" %>>その他</option>
                </select><br>

                <!-- 雇用形態のプルダウンメニュー -->
                <label for="employmentType">雇用形態:</label>
                <select id="employmentType" name="employmentType" required>
                    <option value="正社員" <%= "正社員".equals(request.getAttribute("employeeEmploymentType")) ? "selected" : "" %>>正社員</option>
                    <option value="契約社員" <%= "契約社員".equals(request.getAttribute("employeeEmploymentType")) ? "selected" : "" %>>契約社員</option>
                    <option value="派遣社員" <%= "派遣社員".equals(request.getAttribute("employeeEmploymentType")) ? "selected" : "" %>>派遣社員</option>
                    <option value="アルバイト" <%= "アルバイト".equals(request.getAttribute("employeeEmploymentType")) ? "selected" : "" %>>アルバイト</option>
                    <option value="パート" <%= "パート".equals(request.getAttribute("employeeEmploymentType")) ? "selected" : "" %>>パート</option>
                </select><br>

                <label for="userId">ユーザーID:</label>
                <input type="text" id="userId" name="userId" value="<%= request.getAttribute("employeeUserId") %>" required><br>

                <input type="submit" value="更新" class="button">
            </form>

            <br>
            <form action="${pageContext.request.contextPath}/mainJsp/adminEmployeeSearch.jsp" method="get">
                <input type="submit" value="検索ページに戻る" class="button">
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
