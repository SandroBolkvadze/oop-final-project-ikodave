// Fetches leaderboard data from the server
async function fetchLeaderboardData() {
    const response = await fetch('/api/leaderboard');
    if (!response.ok) throw new Error('Failed to fetch leaderboard');
    return response.json();
}

// Creates a single leaderboard table row element
function createLeaderboardRow(entry, index) {
    const tr = document.createElement('tr');

    tr.innerHTML = `
        <th scope="row">${index + 1}</th>
        <td>${entry.username}</td>
        <td>${entry.score}</td>
        <td>
            <a href="/profile/${entry.username}"
               class="btn btn-outline-primary btn-sm"
               role="button">
                View Profile
            </a>
        </td>
    `;
    return tr;
}

// Populates the leaderboard table body
function renderLeaderboard(data) {
    const tableBody = document.querySelector('#leaderboard-table tbody');
    tableBody.innerHTML = ''; // Clear previous content

    data.forEach((entry, index) => {
        const row = createLeaderboardRow(entry, index);
        tableBody.appendChild(row);
    });
}

// Main entry point: fetch and render leaderboard
async function loadLeaderboard() {
    try {
        console.log('Loading leaderboard...');
        const data = await fetchLeaderboardData();
        renderLeaderboard(data);
        console.log('Leaderboard loaded.');
    } catch (error) {
        console.error('Error loading leaderboard:', error);
    }
}

// Run when DOM is ready
document.addEventListener('DOMContentLoaded', loadLeaderboard);
