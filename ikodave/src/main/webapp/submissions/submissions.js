function loadSubmissions() {
    const parts = window.location.pathname.split('/');
    const problemTitle = parts[parts.length - 1];

    fetch(`/api/problems/submissions/${problemTitle}`)
        .then(res => res.json())
        .then(submissions => {
            const container = document.getElementById('submissions');
            container.innerHTML = '';

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
                    <strong>User:</strong> ${sub.username} &nbsp;|&nbsp;
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
