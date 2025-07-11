function attachCalendarDayClickHandlers(username) {
    document.querySelectorAll('.day-cell').forEach(cell => {
        cell.addEventListener('click', () => {
            const day = parseInt(cell.dataset.day, 10);
            const month = parseInt(cell.dataset.month, 10);
            const year = parseInt(cell.dataset.year, 10);

            const payload = { username, day, month, year };

            fetch('/api/user/profile/submissions-by-date', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            })
                .then(res => res.json())
                            .then(data => {
                renderSubmissionListForDay(data.submissions);
            })
                .catch(err => console.error('Error fetching daily submissions:', err));
        });
    });
}



function renderSubmissionListForDay(submissions) {
    let container = document.getElementById('daily-submissions');

    if (!container) {
        container = document.createElement('div');
        container.id = 'daily-submissions';
        container.className = 'mt-4';
        document.body.appendChild(container);
    }

    container.innerHTML = '';

    const title = document.createElement('h5');
    title.textContent = `Submissions for Selected Day`;
    title.className = 'mb-3';
    container.appendChild(title);

    if (!submissions || !submissions.length) {
        const noData = document.createElement('p');
        noData.textContent = 'No submissions on this day.';
        container.appendChild(noData);
        return;
    }

    submissions.forEach((sub, index) => {
        const card = document.createElement('div');
        card.className = 'card mb-3';
        card.style.padding = '10px';

        const header = document.createElement('h6');
        header.textContent = `#${index + 1} — ${sub.verdict}`;
        card.appendChild(header);

        const details = document.createElement('p');
        details.innerHTML = `
            <strong>Language:</strong> ${sub.codeLanguage} &nbsp;|&nbsp;
            <strong>Time:</strong> ${sub.time} ms &nbsp;|&nbsp;
            <strong>Memory:</strong> ${Math.round(sub.memory / 1024)} KB &nbsp;|&nbsp;
            <strong>Date:</strong> ${sub.submitDate}
        `;
        card.appendChild(details);

        // Toggle button for code
        const toggleCode = document.createElement('button');
        toggleCode.textContent = 'Show Code';
        toggleCode.className = 'btn btn-sm btn-outline-secondary me-2';
        card.appendChild(toggleCode);

        const codePre = document.createElement('pre');
        codePre.style.display = 'none';
        codePre.style.background = '#f0f0f0';
        codePre.style.padding = '10px';
        codePre.textContent = sub.solutionCode;
        card.appendChild(codePre);

        toggleCode.addEventListener('click', () => {
            const isHidden = codePre.style.display === 'none';
            codePre.style.display = isHidden ? 'block' : 'none';
            toggleCode.textContent = isHidden ? 'Hide Code' : 'Show Code';
        });

        // Toggle button for log (if exists)
        if (sub.log && sub.log.trim() !== '') {
            const toggleLog = document.createElement('button');
            toggleLog.textContent = 'Show Log';
            toggleLog.className = 'btn btn-sm btn-outline-info me-2';
            card.appendChild(toggleLog);

            const logPre = document.createElement('pre');
            logPre.style.display = 'none';
            logPre.style.background = '#f8f9fa';
            logPre.style.padding = '10px';
            logPre.style.borderRadius = '4px';
            logPre.style.fontSize = '0.85rem';
            logPre.style.whiteSpace = 'pre-wrap';
            logPre.style.wordWrap = 'break-word';
            logPre.textContent = sub.log;
            card.appendChild(logPre);

            toggleLog.addEventListener('click', () => {
                const isHidden = logPre.style.display === 'none';
                logPre.style.display = isHidden ? 'block' : 'none';
                toggleLog.textContent = isHidden ? 'Hide Log' : 'Show Log';
            });
        }

        container.appendChild(card);
    });
}