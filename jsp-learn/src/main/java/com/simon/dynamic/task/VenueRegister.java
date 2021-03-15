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
@WebServlet("/venue")
public class VenueRegister extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(VenueRegister.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String a = request.getParameter("name");
		String b = request.getParameter("address");
		String c = request.getParameter("city");
		String d = request.getParameter("state");
		String e = request.getParameter("phone");
		String f = request.getParameter("website");
		String g = request.getParameter("genres");
		if (a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || e.isEmpty() || f.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/VenueRegister.jsp");
			out.println("<p>Please Fill All the Details</p>");
			rd.include(request, response);
		} else {
			log.info("Insert record into venues table.");
			try {
				jdbcTemplate.update(
						"INSERT INTO venues (name,address,city,state,phone,website,genres) VALUES (?,?,?,?,?,?,?);", a,
						b, c, d, e, f, g);
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
