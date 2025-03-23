<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Panel</title>
<link rel="stylesheet" type="text/css" href="index.css">
<script src="index.js" defer></script>
</head>
<body>
    <nav class="admin-navbar">
        <div class="menu-icon" onclick="toggleSidebar()">&#9776;</div>
        <div class="user-info">
            <span class="user-name">John Doe</span>
            <span class="user-email">johndoe@example.com</span>
            <button class="logout-button">Logout</button>
        </div>
    </nav>
    
    <div class="sidebar" id="sidebar">
        <h3>List of Operations</h3>
        <ul>
            <li onclick="loadPage('home')">Home</li>
            <li onclick="loadPage('search')">Search Customer</li>
            <li onclick="loadPage('register')">Register Customer</li>
            <li onclick="loadPage('complaints')">View Complaints</li>
            <li onclick="loadPage('bill')">Create Bill</li>
        </ul>
    </div>
    
    <div class="content" id="content">
        
        <div id="home" class="operation active">
            <h2>Welcome to Home Page</h2>
            <p>Select an operation from the sidebar.</p>
        </div>
        
        
        <!-- 1. Search Customer Section -->
        <div id="search" class="operation">
           <h2>Customer Search</h2>
			<div id="searchContainer">
			    <input type="text" id="searchBar" placeholder="Enter customer name or ID">
			    <select id="filterOptions">
			        <option value="all">View All</option>
			        <option value="paid">Paid</option>
			        <option value="unpaid">Unpaid</option>
			    </select>
			    <button id="searchButton">Search</button>
			</div>
			<div id="customerList">
			    <table>
			        <thead>
			            <tr>
			                <th>Customer Name</th>
			                <th>ID</th>
			                <th>Bill Date</th>
			                <th>Payment Date</th>
			                <th>Status</th>
			                <th>Amount</th>
			            </tr>
			        </thead>
			        <tbody id="customerTableBody"></tbody>
			    </table>
			</div>
        </div>

        <!-- 2. Register Customer Section -->
        <div id="register" class="operation">
            <h2>Register Customer</h2>
			 <%@ include file="register.jsp" %>
        </div>

        <!-- 3. View Complaints Section -->
        <div id="complaints" class="operation">
            <%@ include file="complain.jsp" %>
        </div>

        <!-- 4. Create Bill Section -->
        <div id="bill" class="operation">
             <%@ include file="createBill.jsp" %>
        </div>

    </div>
</body>
</html>
