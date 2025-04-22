<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList, java.util.Date"%>
<%@ page import="dto.AttendanceDTO"%>
<%@ page import="dto.EmployeeDTO"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="java.time.DayOfWeek"%>
<%@ page import="java.time.temporal.TemporalAdjusters"%>
<%@ page import="java.time.format.TextStyle"%>
<%@ page import="java.util.Locale"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
    // セッションから従業員ID取得
    dto.EmployeeDTO employee = (dto.EmployeeDTO) session.getAttribute("employee");
    if (employee == null) {
        response.sendRedirect("employeeLogin.jsp");
        return;
    }

    List<AttendanceDTO> attendanceList = (List<AttendanceDTO>) request.getAttribute("attendanceList");
    if (attendanceList == null) {
        attendanceList = new ArrayList<>(); // JSP側でも保険として初期化
    }

    LocalDate displayYM = (LocalDate) request.getAttribute("displayYearMonth");
    if (displayYM == null) {
        displayYM = LocalDate.now();
    }

    LocalDate firstDayOfMonth = displayYM.withDayOfMonth(1);
    LocalDate lastDayOfMonth = displayYM.withDayOfMonth(displayYM.lengthOfMonth());

    List<LocalDate> daysInMonth = new ArrayList<>();
    for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
        daysInMonth.add(date);
    }

    request.setAttribute("daysInMonth", daysInMonth);
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<meta charset="UTF-8">
<title>勤怠確認画面</title>
<style>
.sunday {
	color: red;
}

.saturday {
	color: blue;
}
</style>
</head>
<body>
    <header>
        <jsp:include page="../inc/employeeHeader.jsp" />
    </header>
    <main>
    <div class="menu-container">
        <h2>
            勤怠記録：<%=displayYM.getYear()%>年
            <%=displayYM.getMonthValue()%>月
        </h2>

        <div>
            <a href="AttendanceCheckServlet?yearMonth=<%=displayYM.minusMonths(1).toString().substring(0, 7)%>" class="menu-btn">先月</a>
            | <a href="AttendanceCheckServlet?yearMonth=<%=LocalDate.now().toString().substring(0, 7)%>" class="menu-btn">今月</a>
            | <a href="AttendanceCheckServlet?yearMonth=<%=displayYM.plusMonths(1).toString().substring(0, 7)%>" class="menu-btn">来月</a>
        </div>

        <table border="1" class="center-table">
            <thead>
                <tr>
                    <th>日付</th>
                    <th>出勤時刻</th>
                    <th>退勤時刻</th>
                    <th>稼働時間</th>
                    <th>休憩時間</th>
                    <th>残業時間</th>
                    <th>備考</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="currentDay" items="${daysInMonth}">
                    <%
                    LocalDate current = (LocalDate) pageContext.getAttribute("currentDay");
                    DayOfWeek dow = current.getDayOfWeek();
                    String dowJp = dow.getDisplayName(TextStyle.SHORT, Locale.JAPANESE);
                    String cssClass = "";
                    if (dow == DayOfWeek.SUNDAY)
                        cssClass = "sunday";
                    else if (dow == DayOfWeek.SATURDAY)
                        cssClass = "saturday";

                    AttendanceDTO matchedRecord = null;
                    for (AttendanceDTO record : attendanceList) {
                        if (record.getWorkDate().equals(current)) {
                            matchedRecord = record;
                            break;
                        }
                    }
                    %>
                    <tr>
                        <td class="<%=cssClass%>"><%=current.getMonthValue()%>/<%=current.getDayOfMonth()%>（<%=dowJp%>）</td>
                        <td><%=matchedRecord != null && matchedRecord.getClockIn() != null ? matchedRecord.getClockIn().toLocalTime() : ""%></td>
                        <td><%=matchedRecord != null && matchedRecord.getClockOut() != null ? matchedRecord.getClockOut().toLocalTime() : ""%></td>
                        <td><%=matchedRecord != null && matchedRecord.getWorkHours() != null ? matchedRecord.getWorkHours() : ""%></td>
                        <td><%=matchedRecord != null && matchedRecord.getBreakHours() != null ? matchedRecord.getBreakHours() : ""%></td>
                        <td><%=matchedRecord != null && matchedRecord.getOvertimeHours() != null ? matchedRecord.getOvertimeHours() : ""%></td>
                        <td><%=matchedRecord != null && matchedRecord.getNote() != null ? matchedRecord.getNote() : ""%></td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <th>Total</th>
                    <th></th>
                    <th></th>
                    <th><%=request.getAttribute("totalWorkHours")%></th>
                    <th></th>
                    <th><%=request.getAttribute("totalOvertimeHours")%></th>
                    <th></th>
                </tr>
            </tfoot>
        </table>

        <form action="${pageContext.request.contextPath}/mainJsp/employeeMenu.jsp" method="get">
            <input type="submit" value="戻る" class="mini-btn">
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
