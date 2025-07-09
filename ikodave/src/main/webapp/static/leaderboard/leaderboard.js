async function loadLeaderboard() {
    try {
        console.log(988);

        const response = await fetch('/api/leaderboard'); // Servlet path
        if (!response.ok) throw new Error('Failed to fetch leaderboard');

        const data = await response.json();
        console.log(999);
        const tableBody = document.querySelector('#leaderboard-table tbody');
        tableBody.innerHTML = ''; // Clear existing rows

        data.forEach((entry, index) => {

            console.log(entry);
            console.log(index);
            const tr = document.createElement('tr');

            tr.innerHTML = `
                <td>${index + 1}</td>
                <td>${entry.user.username}</td>
                <td>${entry.score}</td>
            `;

            tableBody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error loading leaderboard:', error);
    }
}

document.addEventListener('DOMContentLoaded', loadLeaderboard);
