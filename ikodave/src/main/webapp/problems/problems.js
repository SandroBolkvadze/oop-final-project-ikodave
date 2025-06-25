const FETCH_API = 'https://localhost:8080/problems';

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('filter-button').addEventListener('click', applyFilters);
});

async function applyFilters() {
    const title = document.getElementById('name').value.trim();
    const difficulty = document.getElementById('difficulty').value.toUpperCase();
    const status = document.getElementById('status').value.toLowerCase();
    const topicsSelect = document.getElementById('topics');
    const topics = Array.from(topicsSelect.selectedOptions).map(option => option.value.toLowerCase());

    const filters = {
        title: title || null,
        difficulty: difficulty || null,
        status: status || null,
        topics: topics.length > 0 ? topics : null
    };

    try {
        const response = await fetch(FETCH_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(filters)
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const data = await response.json();
        renderProblems(data);
    } catch (error) {
        console.error('[Filter] Error:', error);
        document.getElementById('problems-list').innerHTML = `<div class="text-danger">Failed to load problems</div>`;
    }
}

function renderProblems(problems) {
    const container = document.getElementById('problems-list');
    container.innerHTML = '';

    if (!problems || problems.length === 0) {
        container.innerHTML = `<p>No problems match the filters.</p>`;
        return;
    }

    problems.forEach(problem => {
        const div = document.createElement('div');
        div.className = 'card my-2 p-3';
        div.innerHTML = `
            <h5>${problem.title}</h5>
            <p>Difficulty: ${problem.difficulty}</p>
            <p>Status: ${problem.status}</p>
        `;
        container.appendChild(div);
    });
}
