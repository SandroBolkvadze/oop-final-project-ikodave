document.addEventListener('DOMContentLoaded', (e) => {
    loadUserSubmissions();
});

function loadUserSubmissions() {
    const parts = window.location.pathname.split('/');

    fetch(`/api/submissions`)
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById('submissions');
            container.innerHTML = '';

            const submissions = data.submissions || [];

            if (!submissions.length) {
                container.innerHTML += '<p>No submissions</p>';
                return;
            }

            submissions.forEach((sub, index) => {
                const card = document.createElement('div');
                card.className = 'card mb-3';
                card.style.padding = '10px';

                const problemTitle = sub.problemTitle;

                const cardTitle = document.createElement('h6');
                cardTitle.className = 'mb-2';

                const link = document.createElement('a');
                link.href = `/problems/${encodeURIComponent(problemTitle)}`;
                link.textContent = problemTitle.replace(/-/g, ' ');
                link.className = 'text-decoration-none text-secondary';

                cardTitle.appendChild(document.createTextNode('Problem: '));
                cardTitle.appendChild(link);

                card.appendChild(cardTitle);

                const header = document.createElement('h5');
                header.textContent = `#${index + 1} — ${sub.verdict}`;
                card.appendChild(header);

                const details = document.createElement('p');
                details.innerHTML = `
                    <strong>User:</strong> 
                      <a href="/profile/${encodeURIComponent(sub.username)}">${sub.username}</a>
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

                const toggleLog = document.createElement('button');
                toggleLog.textContent = 'Show Logs';
                toggleLog.className = 'btn btn-sm btn-outline-secondary mb-2';
                card.appendChild(toggleLog);

                const logPre = document.createElement('pre');
                logPre.style.display = 'none';
                logPre.style.background = '#f9f9f9';
                logPre.style.padding = '10px';
                logPre.style.marginTop = '5px';
                logPre.textContent = sub.log || 'No logs available.';
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
            document.getElementById('submissions').textContent = 'Error loading submissions.';
        });
}