const FETCH_API = 'https://localhost:8080/problems';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('filter-button').addEventListener("submit", filter);
});

async function filter() {
    let title = "";
    let difficulty = "";
    let status = "";
    let topics = [];

    const filters =  {
        title,
        difficulty,
        status,
        topics
    };

    const response = await fetch(FETCH_API, {
        method: 'POST',
        headers: {'Content-Type': 'applications/json'},
        body: JSON.stringify(filters)
    });

    const data = await response.json();

    let list = document.querySelector('#list');

    data.forEach((problem) => {
        list.append(problem);
    });
}

