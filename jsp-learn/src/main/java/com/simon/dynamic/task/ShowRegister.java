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
@WebServlet("/show")
public class ShowRegister extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(VenueRegister.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String a = request.getParameter("artist");
        String b = request.getParameter("venue");
        String c = request.getParameter("time");
        if (a.isEmpty() || b.isEmpty() || c.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ShowRegister.jsp");
            out.println("<p>Please Fill All the Details</p>");
            rd.include(request, response);
        } else {
            log.info("Insert record into shows table.");
            try {
                jdbcTemplate.update("INSERT INTO shows (artist,venue,time) VALUES (?,?,?);", a, b, c);
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
