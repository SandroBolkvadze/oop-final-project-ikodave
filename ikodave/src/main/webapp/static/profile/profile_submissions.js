async function attachCalendarDayClickHandlers(username) {
    let loggedIn = false;
    try {
        const sessionRes = await fetch('/api/user/session');
        if (sessionRes.ok) {
            const sessionData = await sessionRes.json();
            loggedIn = sessionData.loggedIn;
        }
    } catch (e) {
        console.error('Error checking session:', e);
    }

    document.querySelectorAll('.day-cell').forEach(cell => {
        cell.addEventListener('click', () => {

            if (loggedIn === 'false') {
                let container = document.getElementById('daily-submissions');
                if (!container) {
                    container = document.createElement('div');
                    container.id = 'daily-submissions';
                    container.className = 'mt-4';
                    document.body.appendChild(container);
                }

                container.innerHTML = `
                <div class="card text-center p-4">
                    <p class="mb-3">Please log in to view submissions for this day.</p>
                    <a href="/signin" class="btn btn-primary mx-auto" style="width: fit-content;">
                        Log in to view submissions
                    </a>
                </div>
                `;

                return;
            }


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
                .then(data => renderSubmissionListForDay(data.submissions))
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
    title.textContent = 'Submissions for Selected Day';
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
        const verdict = sub.verdict.toLowerCase();
        header.classList.add('mb-2');
        if (verdict === 'accepted') {
            header.classList.add('text-success');
        } else if (verdict === 'running') {
            header.classList.add('text-primary');
        } else {
            header.classList.add('text-danger');
        }
        card.appendChild(header);

        const details = document.createElement('p');
        details.innerHTML = `
            <strong>Problem:</strong> 
            <a href="/problems/${encodeURIComponent(sub.problemTitle)}" class="link-secondary" style="opacity: 0.7; text-decoration: none;">
              ${sub.problemTitle.replace(/-/g, ' ')}
            </a>
            &nbsp;|&nbsp;
            <strong>Language:</strong> ${sub.codeLanguage}
            &nbsp;|&nbsp;
            <strong>Time:</strong> ${sub.time} ms
            &nbsp;|&nbsp;
            <strong>Date:</strong> ${sub.submitDate}
        `;
        card.appendChild(details);

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
