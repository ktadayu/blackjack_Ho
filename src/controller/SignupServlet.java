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


    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	//登録用
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		//パスワード一致かどうかはとりあえずサーブレット側で書くけど変更するかも
		String user_name = request.getParameter("user_name");
		String user_password_1 = request.getParameter("user_password1");
		String user_password_2 = request.getParameter("user_password2");
		String user_nickname = request.getParameter("user_nickname");

		String nextPage = null;

		if(!user_password_1.equals(user_password_2) ) {
			String msg = "二つのパスワードが一致しません。";
			System.out.println(user_password_1);
			System.out.println(user_password_2);
			request.setAttribute("message", msg);
			nextPage = "/view/users/sign_up.jsp";
		}else {
			try {

			String user_password = user_password_1;
			UserDao userDao = new UserDao();
			//データベース登録
			userDao.doCreate(user_name, user_password,user_nickname);
			System.out.println("アカウント作成完了" + user_nickname);
			//以下の処理はLoginServletに任せたいが方法がわからない。
			//別の方法として、一度login.jspに戻る仕様にするなど。
			UserDao userDao_new = new UserDao();
			User user = userDao_new.doLogin(user_name, user_password);
			HttpSession session = request.getSession();
			session.setAttribute("USER", user);

			nextPage = "/view/game/game_top.jsp";

			}catch(MyException e) {
				String message = e.getMessage();
				System.out.println(e);
				request.setAttribute("message", message);
				nextPage = "/view/users/login.jsp";
			}
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);

	}

}
