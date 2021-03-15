package com.simon.dynamic.task;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("serial")
@WebServlet("/artist")
public class ArtistRegister extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(ArtistRegister.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String a = request.getParameter("name");
		String b = request.getParameter("city");
		String c = request.getParameter("state");
		String d = request.getParameter("phone");
		String e = request.getParameter("website");
		String f = request.getParameter("genres");
		if (a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || e.isEmpty() || f.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ArtistRegister.jsp");
			out.println("<p>Please Fill All the Details</p>");
			rd.include(request, response);
		} else {
			log.info("Insert record into artists table.");
			try {
				jdbcTemplate.update("INSERT INTO artists (name,city,state,phone,website,genres) VALUES (?,?,?,?,?,?);",
						a, b, c, d, e, f);
			} catch (Exception ex) {
				log.info("Insertion failed.");
				log.error(ex.getMessage());
				out.println("<p>Insertion failed.</p>");
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.include(request, response);
		}
	}
}
