<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<header>
    <p>
        <a href="adminMenu.jsp" class="logo">CHRONOWORK管理画面</a>
    </p>

    <c:if test="${not empty admin}">
        <div class="adminName">管理者名 : ${adminName}</div>
    </c:if>

    <!-- ログアウトボタン -->
    <button type="button" class="logout-button" onclick="location.href='${pageContext.request.contextPath}/AdminLogoutServlet'">ログアウト</button>

</header>