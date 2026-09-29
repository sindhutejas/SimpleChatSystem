<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String username = (String) session.getAttribute("username");

    if (username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Simple Chat System</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        .chat-container {
            width: 500px;
            margin: 50px auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
        }

        h2 {
            text-align: center;
        }

        .user-info {
            margin-bottom: 15px;
        }

        input {
            width: 95%;
            padding: 10px;
            margin: 5px 0;
        }

        button {
            padding: 10px 20px;
            cursor: pointer;
        }

        #messages {
            height: 300px;
            overflow-y: auto;
            border: 1px solid #ccc;
            padding: 10px;
            margin: 15px 0;
        }

        .message {
            padding: 8px;
            margin-bottom: 8px;
            border-bottom: 1px solid #eee;
        }

        .time {
            font-size: 12px;
            color: gray;
        }
    </style>
</head>

<body>

<div class="chat-container">

    <h2>Simple Chat System</h2>

    <div class="user-info">
        <b>Logged in as:</b>
        <%= username %>
    </div>

    <label>Receiver:</label>
    <input type="text" id="receiver" placeholder="Enter receiver username">

    <div id="messages">
        <p>No messages yet.</p>
    </div>

    <input type="text" id="message" placeholder="Type your message">

    <button onclick="sendMessage()">Send</button>

</div>

<script>

function sendMessage() {

    let receiver = document.getElementById("receiver").value;
    let message = document.getElementById("message").value;

    if (receiver === "" || message === "") {
        alert("Please enter receiver and message");
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open("POST", "ChatServlet", true);

    xhr.setRequestHeader(
        "Content-Type",
        "application/x-www-form-urlencoded"
    );

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            document.getElementById("message").value = "";

            loadMessages();
        }
    };

    xhr.send(
        "receiver=" + encodeURIComponent(receiver) +
        "&message=" + encodeURIComponent(message)
    );
}


function loadMessages() {

    let receiver = document.getElementById("receiver").value;

    if (receiver === "") {
        return;
    }

    let xhr = new XMLHttpRequest();

    xhr.open(
        "GET",
        "ChatServlet?receiver=" +
        encodeURIComponent(receiver),
        true
    );

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4 && xhr.status === 200) {

            document.getElementById("messages").innerHTML =
                xhr.responseText;
        }
    };

    xhr.send();
}


// Automatically check for new messages every 2 seconds
setInterval(loadMessages, 2000);

</script>

</body>
</html>