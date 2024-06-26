package controller;

import java.io.IOException;

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

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	//登録用
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//日本語ニックネーム対応
		request.setCharacterEncoding("UTF-8");

		String user_name = request.getParameter("user_name");
		String user_password_1 = request.getParameter("user_password1");
		String user_password_2 = request.getParameter("user_password2");
		String user_nickname = request.getParameter("user_nickname");

		String nextPage = null;

		//ユーザー名空文字時
		if(user_name.length() == 0) {
			String msg = "ユーザー名を入力してください";
			BJServlet.sendMessage(msg,"/view/users/sign_up.jsp",request,response);
			return;
		}

		//パスワード入力間違い時
		if (!user_password_1.equals(user_password_2)) {
			String msg = "二つのパスワードが一致しません。";
			BJServlet.sendMessage(msg,"/view/users/sign_up.jsp",request,response);
			return;
		}

		if( user_password_1.length() * user_password_1.length() == 0 ) {
			String msg = "パスワードを入力してください";
			BJServlet.sendMessage(msg,"/view/users/sign_up.jsp",request,response);
			return;
		}

		//ニックネーム空文字時
		if(user_nickname.length() == 0) {
			String msg = "ニックネームを入力してください";
			BJServlet.sendMessage(msg,"/view/users/sign_up.jsp",request,response);
			return;
		}

		try {
			String user_password = user_password_1;
			UserDao userDao = new UserDao();
			//データベース登録
			userDao.doCreate(user_name, user_password, user_nickname);
			System.out.println("アカウント作成完了" + user_nickname);

			//以下の処理(doLogin~TOP5取得まで)はLoginServletに任せたいが方法がわからない。
			UserDao userDao_new = new UserDao();
			User user = userDao_new.doLogin(user_name, user_password);

			HttpSession session = request.getSession();
			session.setAttribute("USER", user);

//			//Top5ユーザー取得
//			UserDao newUserDao = new UserDao();
//			List<User> users = newUserDao.selectTopUsers();
//			session.setAttribute("TOPUSERLIST", users);

			nextPage = "/ToGameTopServlet";

		} catch (MyException e) {
			String message = e.getMessage();
			System.out.println(e);
			request.setAttribute("message", message);
			nextPage = "/view/users/login.jsp";
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}

}
