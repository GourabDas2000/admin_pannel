<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
        }
        form {
            background: white;
            
            padding: 20px;
            border-radius: 8px;
            width: 300px;
            margin: auto;
            box-shadow: 0px 0px 10px gray;
        }
        input, select {
            width: 90%;
            padding: 8px;
            margin: 5px 0;
        }
        button {
            background-color: blue;
            color: white;
            padding: 10px;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h2>Register</h2>
    <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
        <input type="text" name="name" placeholder="Full Name" required><br>
        <input type="email" name="email" placeholder="Email" required><br>
        <input type="text" name="country_code" placeholder="Country Code (e.g., +1)"><br>
        <input type="text" name="mobile_number" placeholder="Mobile Number"><br>
        <input type="text" name="userId" placeholder="Username" required><br>
        <select name="role">
            <option value="user">Register as User</option>
            <option value="admin">Register as Admin</option>
        </select><br>
        <input type="password" name="password" placeholder="Password (for admin only)"><br>
        <button type="submit">Register</button>
    </form>
</body>
</html>
