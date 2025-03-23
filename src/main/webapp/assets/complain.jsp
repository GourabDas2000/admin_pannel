<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Complaints</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
        }
        .container {
            width: 80%;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px gray;
        }
        input {
            padding: 8px;
            width: 300px;
            margin: 10px;
        }
        button {
            background-color: blue;
            color: white;
            padding: 10px;
            border: none;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: lightgray;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>View Complaints</h2>
        
        <!-- Search Box -->
        <div id="complaintsSearch">
            <input type="text" id="complaintsSearchBar" placeholder="Enter customer name or ID">
            <button id="complaintsSearchButton">Search</button>
        </div>

        <!-- Complaint Table -->
        <div id="complaintsList">
            <table>
                <thead>
                    <tr>
                        <th>Customer Name</th>
                        <th>Complaint ID</th>
                        <th>Description</th>
                        <th>Solution</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody id="complaintsTableBody">
                    <!-- Fetched complaints will be displayed here -->
                </tbody>
            </table>
        </div>
    </div>

    <script>
        document.getElementById("complaintsSearchButton").addEventListener("click", function () {
            let searchQuery = document.getElementById("complaintsSearchBar").value;

            fetch("ViewComplaintsServlet?query=" + encodeURIComponent(searchQuery))
                .then(response => response.json())
                .then(data => {
                    let tableBody = document.getElementById("complaintsTableBody");
                    tableBody.innerHTML = ""; // Clear previous data

                    data.forEach(complaint => {
                        let row = `<tr>
                            <td>${complaint.name}</td>
                            <td>${complaint.complaintId}</td>
                            <td>${complaint.description}</td>
                            <td>${complaint.solution || "Pending"}</td>
                            <td>${complaint.status}</td>
                        </tr>`;
                        tableBody.innerHTML += row;
                    });
                })
                .catch(error => console.error("Error fetching complaints:", error));
        });
    </script>

</body>
</html>
