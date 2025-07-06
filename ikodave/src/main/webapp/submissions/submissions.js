

function loadSubmissions() {
    const problemTitle = window.location.pathname.split('/')[2];

    fetch(`/problems/${problemId}/submissions`)
        .then(response => response.json())
        .then(submissions => {
            const container = document.getElementById('submissions');

            if (submissions.length === 0) {
                container.innerHTML = '<p>No submissions found</p>';
                return;
            }

            // Render submissions
            submissions.forEach(sub => {
                const div = document.createElement('div');
                div.innerHTML = `
                            <p>ID: ${sub.id} | User: ${sub.userId} | Verdict: ${sub.verdictId}</p>
                        `;
                container.appendChild(div);
            });
        })
        .catch(error => {
            document.getElementById('submissions').innerHTML = '<p>Error loading submissions</p>';
        });
}