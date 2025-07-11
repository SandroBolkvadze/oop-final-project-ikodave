async function loadLeaderboard(username) {
    try {
        const response = await fetch('/api/leaderboard');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const leaderboard = await response.json();

        const loggedUserResponse = await fetch('/api/user/session');
        if(!loggedUserResponse.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const loggedUser = await loggedUserResponse.json();

        console.log(loggedUser);

        const leaderboardList = document.getElementById('leaderboard-context');
        leaderboardList.innerHTML = '';

        leaderboard.forEach((user, index) => {
            const li = document.createElement('li');
            li.className = 'list-group-item p-0'; // Remove padding from li, add to a

            const a = document.createElement('a');
            a.href = `/profile/${encodeURIComponent(user.username)}`;
            a.className = 'd-flex justify-content-between align-items-center leaderboard-link px-3 py-2 text-decoration-none w-100 h-100';
            a.innerHTML = `
        <span class="leaderboard-rank">${`#${index + 1}`}</span>
        <span class="leaderboard-username">${user.username}</span>
    `;

            if (loggedUser.loggedIn === true && user.username === loggedUser.username) {
                a.classList.add('active');
            }

            li.appendChild(a);
            leaderboardList.appendChild(li);
        });
    } catch (error) {
        console.error('Failed to load leaderboard:', error);
    }
}
