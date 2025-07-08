function addListeners() {

}


async function filter() {

    let title = 'add-numbers';
    let difficulty = 'MEDIUM';
    let status = 'solved';
    let topics = ['graph', 'dp', 'math'];

    const filters = {
        title,
        difficulty,
        status,
        topics
    };

    const response = await fetch(``, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(filters)
    });

    const data = await response.json();

}

async function loadDifficulties() {
    try {
        const res = await fetch('/api/problems/difficulties');
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);

        const difficulties = await res.json();

        const difficultySelect = document.getElementById('filter-difficulty');

        difficulties.forEach(diff => {
            const option = document.createElement('option');
            option.value = diff.difficulty;
            option.textContent = diff.difficulty;
            difficultySelect.appendChild(option);
        });

    } catch (err) {
        console.error("Failed to load difficulties:", err);
    }
}

async function loadStatuses() {
    try {
        const res = await fetch('/api/problems/statuses');
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);

        const statuses = await res.json();

        const statusSelect = document.getElementById('filter-status');

        statuses.forEach(stat => {
            console.log("shoairstoairestaoirsn");
            const option = document.createElement('option');
            option.value = stat.status;
            option.textContent = stat.status;
            statusSelect.appendChild(option);
        });
    } catch (err) {
        console.error("Failed to load difficulties:", err);
    }
}
