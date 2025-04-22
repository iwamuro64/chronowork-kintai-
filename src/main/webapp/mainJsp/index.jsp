<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--<%-->
<!--	session.removeAttribute("loginUserId");-->
<!--	session.removeAttribute("employeeCode");-->
<!--%>-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOPページ</title>

</head>
<body>
	<!-- ヘッダーを挿入 -->

	<main>
		<div class="menu">
			<p>TOPページ</p>
		</div>
		<div class="i_main_wrapper">
			<div class="employee_button">
				<!-- 管理者用メニューのリンク -->
				<a href="adminLogin.jsp"> <input type="button" class="regist_button" value="管理者用メニュー">
				</a>
				<!-- 従業員用メニューのリンク -->
				<a href="employeeLogin.jsp"> <input type="button" class="regist_button" value="従業員用メニュー">
				</a>
			</div>
		</div>

	</main>

	<!-- フッターを挿入 -->


</body>
</html>
