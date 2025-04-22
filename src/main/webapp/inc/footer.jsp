<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
   <!-- フッター -->
    <footer class="footer">
      <p>©2025 PhoenixTicket</p>
<!--      <button onclick="location.href='${pageContext.request.contextPath}/mainJsp/info'">問い合わせ</button>-->
						<form action="${pageContext.request.contextPath}/info" method="get">
					<button type="submit">問い合わせ</button>
				</form>
    </footer>
