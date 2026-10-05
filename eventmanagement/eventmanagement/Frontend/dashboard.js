const API_URL = "http://localhost:8081";

function loadDashboard() {

    fetch(`${API_URL}/dashboard`)
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to load dashboard");
            }

            return response.json();
        })
        .then(data => {

            const app = document.querySelector("#app");

            app.innerHTML = `
                <h1>Dashboard</h1>

                <div class="dashboard">

                    <div class="card">
                        <h3>Total Events</h3>
                        <p>${data.totalEvents}</p>
                    </div>

                    <div class="card">
                        <h3>Upcoming Events</h3>
                        <p>${data.upcomingEvents}</p>
                    </div>

                    <div class="card">
                        <h3>Total Registrations</h3>
                        <p>${data.totalRegistrations}</p>
                    </div>

                    <div class="card">
                        <h3>Available Seats</h3>
                        <p>${data.availableSeats}</p>
                    </div>

                    <div class="card">
                        <h3>Cancelled Registrations</h3>
                        <p>${data.cancelledRegistrations}</p>
                    </div>

                </div>
            `;
        })
        .catch(error => {

            document.querySelector("#app").innerHTML = `
                <h1>Dashboard</h1>
                <p class="error">${error.message}</p>
            `;
        });
}

loadDashboard();