<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Bill</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
        }
        .container {
            width: 400px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px gray;
            display: block; /* Initially visible */
        }
        .search-container {
            display: none; /* Initially hidden */
            margin-top: 20px;
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
            width: 100%;
        }
        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
            display: none; /* Initially hidden */
        }
        th, td {
            border: 1px solid gray;
            padding: 8px;
        }
    </style>
</head>
<body>
    <h2>Create Bill</h2>

    <!-- Bill Form (Initially Visible) -->
    <div class="container" id="billFormContainer">
        <h3>Generate New Bill</h3>
        <form id="billForm" action="CreateBillServlet" method="post">
            <input type="text" id="billCustomerId" name="billCustomerId" readonly placeholder="Customer ID"><br>
            <input type="number" id="billAmount" name="billAmount" required placeholder="Bill Amount"><br>
            <input type="date" id="billDueDate" name="billDueDate" required><br>
            <select id="billStatus" name="billStatus">
                <option value="unpaid">Unpaid</option>
                <option value="paid">Paid</option>
            </select><br>
            <button type="submit">Submit Bill</button>
        </form>
        <p id="billMessage"></p>
        <button onclick="showSearch()">Search for Existing Customer</button>
    </div>

    <!-- Search Section (Initially Hidden) -->
    <div class="search-container" id="searchContainer">
        <input type="text" id="billSearchBar" placeholder="Enter customer name or ID">
        <button id="searchBillButton">Search</button>
        <button onclick="showForm()">Back</button>
    </div>

    <!-- Search Results (Initially Hidden) -->
    <table id="billTable">
        <thead>
            <tr>
                <th>Customer Name</th>
                <th>ID</th>
                <th>Previous Bill</th>
                <th>Due Date</th>
                <th>Status</th>
                <th>Amount</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody id="billTableBody"></tbody>
    </table>

    <script>
        function showSearch() {
            document.getElementById("billFormContainer").style.display = "none";
            document.getElementById("searchContainer").style.display = "block";
        }

        function showForm() {
            document.getElementById("billFormContainer").style.display = "block";
            document.getElementById("searchContainer").style.display = "none";
            document.getElementById("billTable").style.display = "none";
        }

        document.getElementById("searchBillButton").addEventListener("click", function() {
            let searchQuery = document.getElementById("billSearchBar").value;
            
            fetch("SearchConsumerForBillServlet?query=" + searchQuery)
                .then(response => response.text())
                .then(data => {
                    document.getElementById("billTableBody").innerHTML = data;
                    document.getElementById("billTable").style.display = "table"; 
                })
                .catch(error => console.error("Error fetching consumer data:", error));
        });

        function setCustomerId(consumerId) {
            document.getElementById("billCustomerId").value = consumerId;
            showForm();
        }
    </script>
</body>
</html>
