<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BlackJack</title>
</head>
<body>
<section class="header-section">
<div class="header">
<a href="<%=request.getContextPath() %>/LoginServlet">ログアウト</a>
</div>
</section>
<section>

<% User USER = (User) session.getAttribute("USER"); %>s
<div class="section-greeting">
<h2>ようこそ <%= USER.getUserNickname() %>さん</h2>
<form class="game_start_form" action="<%= request.getContextPath() %>/view/game/game_betting.jsp"> <!-- formかaにするかで迷う -->
	<div class="div_game_start_button" >
		<button class="game_start_button" type="submit" > ゲームをはじめる(ディール？)</button>
	</div>
</form>
</div>
<div class="ranking">
<h1 class="ranking-header">ランクTOP5</h1>
<!-- jsp記述 -->
</div>
</section>

  <footer class="footer">

  	<a href="<%= request.getContextPath()%>/view/users/certification.jsp">管理者ページ</a>
  	<a href="<%= request.getContextPath()%>/view/users/leave.jsp">ユーザー消去</a>

  </footer>
</body>
</html>