<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>従業員登録ページ</title>
    <link rel="stylesheet" href="common/css/style.css">
</head>
<body>
<main>
    <div class="menu">
        <p>従業員登録ページ</p>
    </div>
    <div class="enclose1">
        <form id="employForm" action="${pageContext.request.contextPath}/mainJsp/confirmRegistration.jsp" method="post" onsubmit="return validateForm();">
            <table>
                <tr>
                    <th><label for="name">名前：</label></th>
                    <td><input type="text" id="name" name="name" required></td>
                </tr>
                <tr>
                    <th><label for="department">部署：</label></th>
                    <td><select id="department" name="department" required>
                            <option value="">選択してください</option>
                            <option value="総務部">総務部</option>
                            <option value="人事部">人事部</option>
                            <option value="経理部">経理部</option>
                            <option value="法務部">法務部</option>
                            <option value="広報部">広報部</option>
                            <option value="営業部">営業部</option>
                            <option value="開発部">開発部</option>
                            <option value="CS部">CS部</option>
                        </select></td>
                </tr>
                <tr>
                    <th><label for="position">役職：</label></th>
                    <td><select id="position" name="position" required>
                            <option value="">選択してください</option>
                            <option value="部長">部長</option>
                            <option value="課長">課長</option>
                            <option value="係長">係長</option>
                            <option value="主任">主任</option>
                            <option value="リーダー">リーダー</option>
                            <option value="なし">なし</option>
                        </select></td>
                </tr>
                <tr>
                    <th><label for="employment_type">雇用形態：</label></th>
                    <td><select id="employment_type" name="employment_type" required>
                            <option value="">選択してください</option>
                            <option value="正社員">正社員</option>
                            <option value="契約">契約社員</option>
                            <option value="派遣">派遣社員</option>
                            <option value="アルバイト">アルバイト</option>
                            <option value="パート">パート</option>
                        </select></td>
                </tr>
                <tr>
                    <th><label for="user_id">ユーザーID：</label></th>
                    <td><input type="text" id="user_id" name="user_id" required></td>
                </tr>
                <tr>
                    <th><label for="password">パスワード：</label></th>
                    <td><input type="password" id="password" name="password" required></td>
                </tr>
            </table>
            <button type="button" onclick="clearForm()" class="button">クリア</button>
            <button type="submit" class="button">確認</button>
        </form>
    </div>
</main>
<script>
    // clearForm 関数でフォームをクリア
    function clearForm() {
        document.getElementById("employForm").reset(); // フォームをリセット
    }

    // validateForm 関数はそのまま
    function validateForm() {
        const name = document.getElementById("name").value;
        const department = document.getElementById("department").value;
        const position = document.getElementById("position").value;
        const employ_type = document.getElementById("employment_type").value;
        const user_id = document.getElementById("user_id").value;
        const password = document.getElementById("password").value;

        if (!name || !department || !position || !employ_type || !user_id || !password) {
            alert("すべての必須項目を入力してください。");
            return false;
        }

        return true;
    }
</script>
</body>
</html>
