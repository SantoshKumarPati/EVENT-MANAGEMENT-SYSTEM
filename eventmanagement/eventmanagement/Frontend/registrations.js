const API = "http://localhost:8081/registrations";
const EVENTS_API = "http://localhost:8081/events";

let registrations = [];

document.addEventListener("DOMContentLoaded", () => {

    loadEvents();
    loadRegistrations();

    registrationForm.addEventListener(
        "submit",
        registerUser
    );
});

function loadEvents() {

    fetch(EVENTS_API)
        .then(response => response.json())
        .then(events => {

            eventFilter.innerHTML =
                `<option value="">All Events</option>`;

            events.forEach(e => {

                eventFilter.innerHTML += `
                    <option value="${e.id}">
                        ${e.eventName}
                    </option>
                `;
            });
        });
}

function loadRegistrations() {

    fetch(API)
        .then(response => response.json())
        .then(data => {

            registrations = data;

            displayRegistrations(data);
        });
}

function displayRegistrations(data) {

    registrationTableBody.innerHTML = data.length
        ? data.map(r => `

            <tr>

                <td>${r.id}</td>
                <td>${r.name}</td>
                <td>${r.email}</td>
                <td>${r.phone}</td>
                <td>${r.event?.eventName || "Unknown"}</td>
                <td>${formatDate(r.registrationDate)}</td>
                <td>${r.status}</td>

                <td>
                    ${
                        r.status !== "Cancelled"
                        ? `
                            <button
                                class="cancel-btn"
                                onclick="cancelRegistration(${r.id})">
                                Cancel
                            </button>
                        `
                        : ""
                    }
                </td>

            </tr>

        `).join("")
        : `<tr><td colspan="8">No registrations found</td></tr>`;
}

function showForm() {

    registrationForm.reset();

    loadAvailableEvents();

    registrationFormContainer.classList.remove("hidden");
}

function loadAvailableEvents() {

    fetch(EVENTS_API)
        .then(response => response.json())
        .then(events => {

            registrationEvent.innerHTML =
                `<option value="">Select Event</option>`;

            events
                .filter(e =>
                    e.status === "Upcoming" &&
                    e.availableSeats > 0
                )
                .forEach(e => {

                    registrationEvent.innerHTML += `
                        <option value="${e.id}">
                            ${e.eventName}
                            - ${e.availableSeats} seats
                        </option>
                    `;
                });
        });
}

function registerUser(e) {

    e.preventDefault();

    const data = {
        name: registrationName.value.trim(),
        email: registrationEmail.value.trim(),
        phone: registrationPhone.value.trim(),
        event: {
            id: Number(registrationEvent.value)
        }
    };

    if (
        !data.name ||
        !data.email ||
        !data.phone ||
        !data.event.id
    ) {

        showMessage(
            "Please fill all fields",
            "error"
        );

        return;
    }

    fetch(API, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(data)

    })
    .then(response => {

        if (!response.ok)
            return response.text()
                .then(message => {
                    throw new Error(message);
                });

        return response.json();
    })
    .then(result => {

        hideForm();

        showMessage(
            `Registration successful. ID: ${result.id}`,
            "success"
        );

        loadRegistrations();
        loadEvents();
    })
    .catch(error =>
        showMessage(error.message, "error")
    );
}

function cancelRegistration(id) {

    if (!confirm("Cancel this registration?"))
        return;

    fetch(`${API}/${id}/cancel`, {
        method: "PUT"
    })
    .then(response => {

        if (!response.ok)
            throw new Error("Cancellation failed");

        return response.text();
    })
    .then(message => {

        showMessage(message, "success");

        loadRegistrations();
        loadEvents();
    })
    .catch(error =>
        showMessage(error.message, "error")
    );
}

function filterRegistrations() {

    const search =
        searchRegistration.value
            .trim()
            .toLowerCase();

    const eventId = eventFilter.value;

    let result = registrations;

    if (search) {

        result = result.filter(r =>
            r.name.toLowerCase().includes(search) ||
            r.email.toLowerCase().includes(search)
        );
    }

    if (eventId) {

        result = result.filter(r =>
            r.event?.id == eventId
        );
    }

    displayRegistrations(result);
}

function clearFilters() {

    searchRegistration.value = "";
    eventFilter.value = "";

    displayRegistrations(registrations);
}

function hideForm() {

    registrationFormContainer.classList.add("hidden");
}

function formatDate(date) {

    return date
        ? new Date(date).toLocaleString()
        : "";
}

function showMessage(text, type) {

    const message = document.querySelector("#message");

    message.textContent = text;
    message.className = type;

    setTimeout(() => {
        message.textContent = "";
    }, 4000);
}