package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import exception.MyException;
import model.User;

/**
 * Servlet implementation class ToGameTopServlet
 */
@WebServlet("/ToGameTopServlet")
public class ToGameTopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String nextPage;
		String message;

		//セッションの"USER"属性をトークン代わりに用いる
		if (session.getAttribute("USER") == null) {
			nextPage = "/view/users/login.jsp";
			message = "不正なアクセス";

			request.setAttribute("message", message);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
			requestDispatcher.forward(request, response);
			return;
		}

		//game-topページに表示するランキングを取得する。
		try {
			UserDao userDao = new UserDao();
			List<User> ranking = userDao.selectTopUsers();
			request.setAttribute("ranking", ranking);
		} catch (MyException e) {
			e.printStackTrace();
		}

		nextPage = "/view/game/game_top.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);

	}

}
