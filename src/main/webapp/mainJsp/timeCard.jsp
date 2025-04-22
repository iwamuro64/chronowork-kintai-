<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">

<meta charset="UTF-8">
<title>打刻画面</title>
</head>
<body>
	<%@ page import="dto.TimeCardStatus"%>
	<%
	TimeCardStatus status = (TimeCardStatus) request.getAttribute("status");
	boolean clockedIn = status != null && status.isClockIn();
	boolean clockedOut = status != null && status.isClockOut();
	boolean breakStarted = status != null && status.isBreakStart();
	boolean breakEnded = status != null && status.isBreakEnd();
	%>

	<header>
		<div class="header">
			<!-- ヘッダーを挿入 -->
			<jsp:include page="../inc/employeeHeader.jsp" />
		</div>
	</header>
	<div class="menu-container">
		<div class="employee_button">
			<form action="${pageContext.request.contextPath}/TimeCardServlet"
				method="post">
				<input type="hidden" name="action" value="clock_in">
				<button type="submit" class="menu-btn"
					<%=clockedIn ? "disabled title='すでに出勤しています'" : ""%>>出勤</button>
			</form>

			<form action="${pageContext.request.contextPath}/TimeCardServlet"
				method="post">
				<input type="hidden" name="action" value="clock_out">
				<button type="submit" class="menu-btn"
					<%=!clockedIn || clockedOut ? "disabled title='まだ出勤していません'" : ""%>>
					退勤</button>
			</form>

			<form action="${pageContext.request.contextPath}/TimeCardServlet"
				method="post">
				<input type="hidden" name="action" value="break_start">
				<button type="submit" class="menu-btn"
					<%=breakStarted && !breakEnded ? "disabled title='すでに休憩中です'" : ""%>>
					休憩</button>
			</form>

			<form action="${pageContext.request.contextPath}/TimeCardServlet"
				method="post">
				<input type="hidden" name="action" value="break_end">
				<button type="submit" class="menu-btn"
					<%=!breakStarted || breakEnded ? "disabled title='休憩は開始していません'" : ""%>>
					休憩終了</button>
			</form>

		</div>
		<form
			action="${pageContext.request.contextPath}/mainJsp/employeeMenu.jsp"
			method="get">
			<input type="submit" value="戻る" class="mini-btn">
		</form>
	</div>
	<footer>
		<div style="text-align: center; padding: 10px;">
			<small>© 2025 CHRONOWORK.</small>
		</div>
	</footer>
</body>
</html>