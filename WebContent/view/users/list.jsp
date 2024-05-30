<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー一覧</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/view/game/style/style.css">
<% String msg = (String) request.getAttribute("message");%>
<% List<User> users = (List<User>) request.getAttribute("USERLIST"); %>
</head>
<body>
<section>
    <div class="user-container">
        <h1 class="list-header">ユーザー一覧</h1>

        <% if(msg!=null){ %>
        	<p><%= msg %></p>
        <%}else{} %>
        <table>
            <tr>
                <th class="table-header">ニックネーム</th>
                <th class="table-header">チップ数</th>
                <th class="table-header">勝率</th>
            </tr>
            <% for(User user: users){ %>
            <tr>
                <th><%=user.getUserNickname() %></th>
                <th><%=user.getNumberOfTips()%></th>
                <th>勝率</th>
                <th><a href="<%= request.getContextPath()%>/LeaveServlet?user_nickname=<%=user.getUserNickname() %>"><button type="button" >削除</button></a></th>
            </tr>
            <% } %>
        </table>
    </div>
</section>





</body>
</html>