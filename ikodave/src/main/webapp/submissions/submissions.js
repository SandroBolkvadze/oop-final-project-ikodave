function loadSubmissions() {
    const parts = window.location.pathname.split('/');
    const problemTitle = parts[parts.length - 1];

    console.log(problemTitle);
    fetch(`/problems/submissions/${problemTitle}`)
        .then(res => res.json())
        .then(submissions => {
            const container = document.getElementById('submissions');
            container.innerHTML = '';

            if (!submissions.length) {
                container.innerHTML = '<p>No submissions yet.</p>';
                return;
            }

            submissions.forEach(sub => {
                const card = document.createElement('div');
                card.className = 'card mb-3';
                card.style.padding = '10px';

                const header = document.createElement('h5');
                header.textContent = `#${sub.id} — ${sub.verdictId}`;
                card.appendChild(header);

                const details = document.createElement('p');
                details.innerHTML = `
                      <strong>Time:</strong> ${sub.time} ms &nbsp;|&nbsp;
                      <strong>Memory:</strong> ${Math.round(sub.memory/1024)} KB &nbsp;|&nbsp;
                      <strong>Date:</strong> ${sub.submitDate}`;
                card.appendChild(details);

                const toggle = document.createElement('button');
                toggle.textContent = 'Show Code';
                toggle.className = 'btn btn-sm btn-outline-secondary mb-2';
                card.appendChild(toggle);

                const codePre = document.createElement('pre');
                codePre.style.display = 'none';
                codePre.style.background = '#f0f0f0';
                codePre.style.padding = '10px';
                codePre.textContent = sub.solutionCode;
                card.appendChild(codePre);

                toggle.addEventListener('click', () => {
                    const isHidden = codePre.style.display === 'none';
                    codePre.style.display = isHidden ? 'block' : 'none';
                    toggle.textContent = isHidden ? 'Hide Code' : 'Show Code';
                });

                container.appendChild(card);
            });
        })
        .catch((error) => {
            console.log(error);
            document.getElementById('submissions')
                .textContent = 'Error loading submissions.';
        });
}