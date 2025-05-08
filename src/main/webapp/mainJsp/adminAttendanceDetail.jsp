<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>従業員の勤怠情報</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>従業員の勤怠情報</h1>

    <!-- 従業員選択フォーム -->
    <form action="AdminAttendanceDetailServlet" method="get">
        <label for="employee_id">従業員を選択:</label>
        <select name="employee_id" id="employee_id">
            <c:forEach var="employee" items="${employeeList}">
                <option value="${employee.employee_id}">${employee.name} (${employee.department})</option>
            </c:forEach>
        </select>
        <button type="submit">選択</button>
    </form>

    <!-- 勤怠情報の表示 -->
    <c:if test="${not empty attendanceList}">
        <h2>${attendanceList[0].name}さんの勤怠情報</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>従業員名</th>
                    <th>部署</th>
                    <th>役職</th>
                    <th>勤務日</th>
                    <th>出勤時間</th>
                    <th>退勤時間</th>
                    <th>休憩開始時間</th>
                    <th>休憩終了時間</th>
                    <th>メモ</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="record" items="${attendanceList}">
                    <tr>
                        <td>${record.name}</td>
                        <td>${record.department}</td>
                        <td>${record.position}</td>
                        <td>${record.workDate}</td>
                        <td>${record.clockIn}</td>
                        <td>${record.clockOut}</td>
                        <td>${record.breakStart}</td>
                        <td>${record.breakEnd}</td>
                        <td>${record.note}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty attendanceList}">
        <p>選択した従業員の勤怠情報はありません。</p>
    </c:if>

</body>
</html>
