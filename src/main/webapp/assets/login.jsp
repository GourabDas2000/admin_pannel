<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 50px;
        }
        .login-container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            margin: auto;
        }
        input {
            width: 90%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        button {
            width: 100%;
            padding: 10px;
            background: #28a745;
            border: none;
            color: white;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background: #218838;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Login Page</h2>
        <form action="<%= pageContext.getServletContext().getContextPath() %>/LoginServlet" method="post">
		    <input type="text" name="userId" placeholder="Username" required><br>
		    <input type="password" name="password" placeholder="Password" required><br>
		    <button type="submit">Login</button>
		</form>
        <% 
            String error = request.getParameter("error");
            if ("invalid".equals(error)) {
        %>
            <p class="error">Invalid username or password</p>
        <% } %>
    </div>
</body>
</html>

