document.addEventListener("DOMContentLoaded", () => {
    loadProfile().catch(console.error);
    addMonthPickerListener();
    addCalendarNavListeners();
})

async function loadProfile() {
    const parts = window.location.pathname.split('/').filter(Boolean);
    const username = parts[parts.length - 1];
    loadStats(username).catch(console.error);
    loadLeaderboard(username).catch(console.error);
    setDefaultMonthPicker();
    populateCalendar();
}

async function loadStats(username) {
    const sendData = { username };
    try {
        const response = await fetch('/api/user/profile/submission-stats', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(sendData)
        });

        if (!response.ok) {
            throw new Error('Failed to fetch profile data');
        }

        const stats = await response.json();

        const easySolved = stats.easySolvedProblemsCount;
        const easyTotal = easySolved + stats.easyNotSolvedProblemsCount;
        const easyPercent = easyTotal > 0 ? (easySolved / easyTotal * 100).toFixed(1) : 0;

        const mediumSolved = stats.mediumSolvedProblemsCount;
        const mediumTotal = mediumSolved + stats.mediumNotSolvedProblemsCount;
        const mediumPercent = mediumTotal > 0 ? (mediumSolved / mediumTotal * 100).toFixed(1) : 0;

        const hardSolved = stats.hardSolvedProblemsCount;
        const hardTotal = hardSolved + stats.hardNotSolvedProblemsCount;
        const hardPercent = hardTotal > 0 ? (hardSolved / hardTotal * 100).toFixed(1) : 0;

        const rank = stats.userRank;
        document.getElementById('username').textContent = 'Hello, ' + username;
        // document.getElementById('rank').textContent = `#${rank}`;

        document.getElementById('easy-ratio').textContent = `${easySolved} / ${easyTotal}`;
        document.getElementById('bar-easy').style.width = `${easyPercent}%`;

        document.getElementById('medium-ratio').textContent = `${mediumSolved} / ${mediumTotal}`;
        document.getElementById('bar-medium').style.width = `${mediumPercent}%`;

        document.getElementById('hard-ratio').textContent = `${hardSolved} / ${hardTotal}`;
        document.getElementById('bar-hard').style.width = `${hardPercent}%`;

        document.getElementById('total-submissions').textContent = stats.submissionsTotalCount;
        document.getElementById('failed-submissions').textContent = stats.notAcceptedSubmissionsCount;
        document.getElementById('accepted-today').textContent = stats.acceptedProblemsCountToday;
        document.getElementById('your-rank').textContent = `#${stats.userRank}`;


    } catch (error) {
        console.error('Error loading profile data:', error);
    }
}



