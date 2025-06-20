const FETCH_API = 'https://localhost:8080/problems';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('filter-button').addEventListener("submit", filter);
});

async function filter() {

    const filters = getFilters();

    const response = await fetch(FETCH_API, {
        method: 'POST',
        headers: {'Content-Type': 'applications/json'},
        body: JSON.stringify(filters)
    });

    const data = await response.json();
    console.log(data);
}

function getFilters() {
    name = document.getElementById('name').value;
    return name;
}
