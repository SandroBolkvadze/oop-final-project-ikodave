// document.addEventListener('DOMContentLoaded', filter);


async function loadDifficulties() {

}

async function filter() {

    let title = 'add-numbers';
    let difficulty = 'MEDIUM';
    let status = 'solved';
    let topics = ['graph', 'dp', 'math'];

    const filters =  {
        title,
        difficulty,
        status,
        topics
    };

    const response = await fetch(``, {
        method: 'POST',
        headers: {'Content-Type': 'applications/json'},
        body: JSON.stringify(filters)
    });

    const data = await response.json();

}

async function loadDifficulties() {
    try {
        const res = await fetch('/api/problems/difficulties');
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);

        const difficulties = await res.json(); // Expecting something like [{id:1, name:"Easy"}, ...]

        const difficultySelect = document.getElementById('filter-difficulty');

        // Clear existing options except "All"
        difficultySelect.querySelectorAll('option:not([value=""])').forEach(o => o.remove());

        // Add new options dynamically
        difficulties.forEach(diff => {
            const option = document.createElement('option');
            option.value = diff.name.toLowerCase();  // or use diff.id if you prefer
            option.textContent = diff.name;
            difficultySelect.appendChild(option);
        });
    } catch (err) {
        console.error("Failed to load difficulties:", err);
    }
}

