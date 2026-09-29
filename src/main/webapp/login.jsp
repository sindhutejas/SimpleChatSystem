<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Chat System</title>
</head>
<body>

    <h2>Simple Chat System</h2>

    <form action="LoginServlet" method="post">

        <label>Username:</label>
        <input type="text" name="username" required>

        <br><br>

        <button type="submit">Login</button>

    </form>

</body>
</html>