package com.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        HttpSession session = request.getSession();

        String sender = (String) session.getAttribute("username");
        String receiver = request.getParameter("receiver");
        String message = request.getParameter("message");

        String sql = "INSERT INTO messages " +
                     "(sender, receiver, message) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, message);

            ps.executeUpdate();

            response.setContentType("text/plain");
            response.getWriter().write("Message sent successfully");

        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(500);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException, IOException {

        HttpSession session = request.getSession();

        String currentUser =
                (String) session.getAttribute("username");

        String receiver = request.getParameter("receiver");

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String sql = "SELECT sender, receiver, message, message_time " +
                     "FROM messages " +
                     "WHERE (sender = ? AND receiver = ?) " +
                     "OR (sender = ? AND receiver = ?) " +
                     "ORDER BY message_time ASC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, currentUser);
            ps.setString(2, receiver);
            ps.setString(3, receiver);
            ps.setString(4, currentUser);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String sender = rs.getString("sender");
                String receiverName = rs.getString("receiver");
                String message = rs.getString("message");
                String time = rs.getString("message_time");

                out.println("<div class='message'>");

                out.println("<b>" + sender + "</b> → "
                        + receiverName + "<br>");

                out.println(message + "<br>");

                out.println("<span class='time'>"
                        + time + "</span>");

                out.println("</div>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.println(
                "<p>Error loading messages: "
                + e.getMessage() + "</p>"
            );
        }
    }
}
