document.addEventListener('DOMContentLoaded', (e) => {
    addButtonListeners();
    loadUserSubmissions();
});

function addButtonListeners() {
    document.getElementById('backToProblemButton').onclick = backToProblemButton;
}

function backToProblemButton() {
    const parts = window.location.pathname.split('/').filter(Boolean);
    const title = parts[parts.length - 1];
    window.location.href = `/problems/${encodeURIComponent(title)}`;
}

function loadUserSubmissions() {
    const parts = window.location.pathname.split('/');
    const problemTitle = parts[parts.length - 1];

    fetch(`/api/problems/submissions/${problemTitle}`)
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById('submissions');
            container.innerHTML = '';

            if (!data || typeof data !== 'object' || !('submissions' in data)) {
                container.innerHTML = `<a href="/signin">Sign in to view Submissions</a>`;
                return;
            }

            let submissions = data.submissions;

            if (!submissions.length) {
                container.innerHTML = '<p>No submissions yet.</p>';
                return;
            }

            submissions.forEach((sub, index) => {
                const card = document.createElement('div');
                card.className = 'card mb-3';
                card.style.padding = '10px';

                const header = document.createElement('h5');
                header.textContent = `#${index + 1} — ${sub.verdict}`;
                card.appendChild(header);

                const details = document.createElement('p');
                details.innerHTML = `
                    <strong>User:</strong> 
                      <a href="/profile/${encodeURIComponent(sub.username)}">${sub.username}</a> &nbsp;|&nbsp;
                    <strong>Language:</strong> ${sub.codeLanguage} &nbsp;|&nbsp;
                    <strong>Time:</strong> ${sub.time} ms &nbsp;|&nbsp;
                    <strong>Memory:</strong> ${Math.round(sub.memory / 1024)} KB &nbsp;|&nbsp;
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

                const toggleLog = document.createElement('button');
                toggleLog.textContent = 'Show Logs';
                toggleLog.className = 'btn btn-sm btn-outline-secondary mb-2';
                card.appendChild(toggleLog);

                const log = sub.log;
                const logPre = document.createElement('pre');
                logPre.style.display = 'none';
                logPre.style.background = '#f9f9f9';
                logPre.style.padding = '10px';
                logPre.style.marginTop = '5px';
                logPre.textContent = log || 'No logs available.';
                card.appendChild(logPre);

                toggleLog.addEventListener('click', () => {
                    const isHidden = logPre.style.display === 'none';
                    logPre.style.display = isHidden ? 'block' : 'none';
                    toggleLog.textContent = isHidden ? 'Hide Logs' : 'Show Logs';
                });

                container.appendChild(card);
            });
        })
        .catch((error) => {
            console.error(error);
            document.getElementById('submissions')
                .textContent = 'Error loading submissions.';
        });
}
