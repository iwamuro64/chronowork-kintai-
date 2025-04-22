<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // セッションから管理者名を取得
    String adminName = (String) session.getAttribute("adminName");

    // ログインしていない場合はログイン画面へリダイレクト
    if (adminName == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>従業員管理</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="header">
        <jsp:include page="../inc/adminHeader.jsp" />
    </div>

    <div class="menu">
        <div class="main_frame">
            <h2>従業員追加</h2>
            <form action="${pageContext.request.contextPath}/AdminEmployeeAddServlet" method="post" id="employeeAddForm">
                <table>
                    <tr>
                        <td><label for="addName">名前</label></td>
                        <td><input type="text" id="addName" name="addName" placeholder="名前" required></td>
                    </tr>
                    
                    <!-- 部署のプルダウンメニュー -->
                    <tr>
                        <td><label for="addDepartment">部署</label></td>
                        <td>
                            <select id="addDepartment" name="addDepartment" required>
                            	<option value="">-- 選択してください --</option>
                                <option value="総務部">総務部</option>
                                <option value="人事部">人事部</option>
                                <option value="経理部">経理部</option>
                                <option value="法務部">法務部</option>
                                <option value="広報部">広報部</option>
                                <option value="営業部">営業部</option>
                                <option value="開発部">開発部</option>
                                <option value="CS部">CS部</option>
                            </select>
                        </td>
                    </tr>
                    
                    <!-- 役職のプルダウンメニュー -->
                    <tr>
                        <td><label for="addPosition">役職</label></td>
                        <td>
                            <select id="addPosition" name="addPosition" required>
                            	<option value="">-- 選択してください --</option>
                                <option value="部長">部長</option>
                                <option value="課長">課長</option>
                                <option value="係長">係長</option>
                                <option value="主任">主任</option>
                                <option value="リーダー">リーダー</option>
                                <option value="その他">その他</option>
                            </select>
                        </td>
                    </tr>
                    
                    <!-- 雇用形態のプルダウンメニュー -->
                    <tr>
                        <td><label for="addEmploymentType">雇用形態</label></td>
                        <td>
                            <select id="addEmploymentType" name="addEmploymentType" required>
                            	<option value="">-- 選択してください --</option>
                                <option value="正社員">正社員</option>
                                <option value="契約社員">契約社員</option>
                                <option value="派遣社員">派遣社員</option>
                                <option value="アルバイト">アルバイト</option>
                                <option value="パート">パート</option>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="addUserId">ユーザーID</label></td>
                        <td><input type="text" id="addUserId" name="addUserId" placeholder="ユーザーID" required></td>
                    </tr>
                    <tr>
                        <td><label for="addPassword">パスワード</label></td>
                        <td><input type="password" id="addPassword" name="addPassword" placeholder="パスワード" required></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="reset" value="リセット" class="button">
                            <input type="submit" value="追加" class="button">
                        </td>
                    </tr>
                </table>
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
