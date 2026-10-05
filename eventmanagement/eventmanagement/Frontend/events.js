const API = "http://localhost:8081/events";

document.addEventListener("DOMContentLoaded", loadEvents);

document.querySelector("#eventForm").addEventListener("submit", saveEvent);

function loadEvents() {

    const name = searchName.value.trim();
    const status = statusFilter.value;
    const date = dateFilter.value;

    let url = API;

    if (name)
        url += `?name=${encodeURIComponent(name)}`;
    else if (status)
        url += `?status=${status}`;
    else if (date)
        url += `?date=${date}`;

    fetch(url)
        .then(response => response.json())
        .then(displayEvents)
        .catch(error => showMessage(error.message, "error"));
}

function displayEvents(events) {

    eventTableBody.innerHTML = events.length
        ? events.map(e => `
            <tr>
                <td>${e.id}</td>
                <td>${e.eventName}</td>
                <td>${e.description}</td>
                <td>${e.eventDate}</td>
                <td>${e.eventTime}</td>
                <td>${e.location}</td>
                <td>${e.totalSeats}</td>
                <td>${e.availableSeats}</td>
                <td>${e.status}</td>
                <td>
                    <button onclick="editEvent(${e.id})">
                        Edit
                    </button>

                    <button
                        class="delete-btn"
                        onclick="deleteEvent(${e.id})">
                        Delete
                    </button>
                </td>
            </tr>
        `).join("")
        : `<tr><td colspan="10">No events found</td></tr>`;
}

function showAddForm() {

    eventForm.reset();

    eventId.value = "";

    formTitle.textContent = "Add Event";

    eventFormContainer.classList.remove("hidden");
}

function saveEvent(e) {

    e.preventDefault();

    const data = {
        eventName: eventName.value.trim(),
        description: description.value.trim(),
        eventDate: eventDate.value,
        eventTime: eventTime.value,
        location: location.value.trim(),
        totalSeats: Number(totalSeats.value),
        status: status.value
    };

    const id = eventId.value;

    if (id)
        data.availableSeats = Number(totalSeats.value);

    fetch(id ? `${API}/${id}` : API, {
        method: id ? "PUT" : "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {

        if (!response.ok)
            throw new Error("Failed to save event");

        return response.json();
    })
    .then(() => {

        hideForm();

        showMessage(
            id
                ? "Event updated successfully"
                : "Event created successfully",
            "success"
        );

        loadEvents();
    })
    .catch(error =>
        showMessage(error.message, "error")
    );
}

function editEvent(id) {

    fetch(`${API}/${id}`)
        .then(response => response.json())
        .then(e => {

            eventId.value = e.id;
            eventName.value = e.eventName;
            description.value = e.description;
            eventDate.value = e.eventDate;
            eventTime.value = e.eventTime;
            location.value = e.location;
            totalSeats.value = e.totalSeats;
            status.value = e.status;

            formTitle.textContent = "Edit Event";

            eventFormContainer.classList.remove("hidden");
        });
}

function deleteEvent(id) {

    if (!confirm("Delete this event?"))
        return;

    fetch(`${API}/${id}`, {
        method: "DELETE"
    })
    .then(response => {

        if (!response.ok)
            throw new Error();

        return response.text();
    })
    .then(message => {

        showMessage(message, "success");

        loadEvents();
    })
    .catch(() =>
        showMessage(
            "Cannot delete event. It may have registrations.",
            "error"
        )
    );
}

function clearFilters() {

    searchName.value = "";
    statusFilter.value = "";
    dateFilter.value = "";

    loadEvents();
}

function hideForm() {

    eventFormContainer.classList.add("hidden");
}
function showMessage(text, type) {

    const message = document.querySelector("#message");

    message.textContent = text;
    message.className = type;

    setTimeout(() => {
        message.textContent = "";
    }, 3000);
}