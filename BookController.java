package mybean.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.model.Book;

@WebServlet("/book")
public class BookController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String command = req.getParameter("command");
		String url = "";
		HttpSession session = req.getSession();
		ArrayList<Book> bookList = 
				(ArrayList<Book>)session.getAttribute("bookList");
		
		if(command.equals("shop")) {
			url = "/WEB-INF/view/bookshop.jsp";
		}
		else if(command.equals("cart")) {
			url = "/WEB-INF/view/bookshop.jsp";
			
			if(bookList == null) {
				bookList = new ArrayList<Book>();
			}
			
			bookList.add(getBook(req));
			session.setAttribute("bookList", bookList);
		}
		else if(command.equals("checkout")) {
			url = "/WEB-INF/view/checkout.jsp";
		}
		
		RequestDispatcher view = req.getRequestDispatcher(url);
		view.forward(req, resp);
	}
	
	public Book getBook(HttpServletRequest req) {
		String book = req.getParameter("book");
		String quantity = req.getParameter("quantity");
		
		StringTokenizer token = new StringTokenizer(book, "/");
		String title = token.nextToken().trim();
		String author = token.nextToken().trim();
		int price = Integer.parseInt(token.nextToken().trim());
		
		Book dto = new Book();
		dto.setAuthor(author);
		dto.setPrice(price);
		dto.setQuantity(Integer.parseInt(quantity));
		dto.setTitle(title);
			
		return dto;
	}
}













