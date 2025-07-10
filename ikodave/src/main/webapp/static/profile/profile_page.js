document.addEventListener("DOMContentLoaded", () => {
    load_profile();
})

async function load_profile() {
    const parts = window.location.pathname.split('/').filter(Boolean);
    const username = parts[parts.length - 1];

    const sendData = { username };

    try {
        const response = await fetch('/api/user/profile', {
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

        populateCalendar(stats.submissionDates);

    } catch (error) {
        console.error('Error loading profile data:', error);
    }
}

function populateCalendar(submissionTimestamps) {
    const calendarGrid = document.getElementById('calendar-grid');
    const monthLabel = document.querySelector('.month-label');
    calendarGrid.innerHTML = '';

    const now = new Date(); // current month and year
    const year = now.getFullYear();
    const month = now.getMonth(); // 0-indexed

    const monthNames = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];
    monthLabel.textContent = `${monthNames[month]} ${year}`;

    const daysInMonth = new Date(year, month + 1, 0).getDate();

    const submittedSet = new Set(
        submissionTimestamps.map(ts => {
            const d = new Date(ts);
            return d.toISOString().split('T')[0];
        })
    );

    console.log(submittedSet);

    for (let day = 1; day <= daysInMonth; day++) {
        const paddedMonth = String(month + 1).padStart(2, '0');
        const paddedDay = String(day).padStart(2, '0');
        const dateStr = `${year}-${paddedMonth}-${paddedDay}`;

        const cell = document.createElement('div');
        cell.classList.add('day-cell');

        // ✅ Mark this cell green if it is in the submittedSet
        if (submittedSet.has(dateStr)) {
            cell.classList.add('submitted');
        }

        const dayDiv = document.createElement('div');
        dayDiv.className = 'day';
        dayDiv.textContent = day;

        const monDiv = document.createElement('div');
        monDiv.className = 'mon';
        monDiv.textContent = monthNames[month].slice(0, 3); // e.g. "Jul"

        cell.appendChild(dayDiv);
        cell.appendChild(monDiv);
        calendarGrid.appendChild(cell);
    }
}
