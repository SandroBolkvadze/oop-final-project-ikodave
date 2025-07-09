async function loadLeaderboard() {
    try {
        const response = await fetch('/leaderboard'); // Servlet path
        if (!response.ok) throw new Error('Failed to fetch leaderboard');

        const data = await response.json();
        const tableBody = document.querySelector('#leaderboard-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach((entry, index) => {
            const tr = document.createElement('tr');

            tr.innerHTML = `
                <td>${index + 1}</td>
                <td>${entry.user.username}</td>
                <td>${entry.rank}</td>
            `;

            tableBody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error loading leaderboard:', error);
    }
}

document.addEventListener('DOMContentLoaded', loadLeaderboard);
