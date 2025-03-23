// Toggle Sidebar
function toggleSidebar() {
    var sidebar = document.getElementById("sidebar");
    var content = document.getElementById("content");
    sidebar.classList.toggle("active");
    if (sidebar.classList.contains("active")) {
        content.style.marginLeft = "260px";
    } else {
        content.style.marginLeft = "20px";
    }
}

// Load Selected Operation
function loadPage(page) {
    document.querySelectorAll('.operation').forEach(op => op.classList.remove('active'));
    document.getElementById(page).classList.add('active');
}

// Search Customer
document.getElementById("searchButton").addEventListener("click", function() {
    let searchQuery = document.getElementById("searchBar").value;
    let filter = document.getElementById("filterOptions").value;
    
    fetch("SearchConsumerServlet?query=" + searchQuery + "&filter=" + filter)
        .then(response => response.text())
        .then(data => {
			console.log(data);
            document.getElementById("customerTableBody").innerHTML = data;
        })
        .catch(error => console.error("Error fetching customer data:", error));
});






