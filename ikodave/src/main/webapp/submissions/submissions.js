
function loadSubmissions() {
    const problemTitle =
        window.location.pathname.split('/')[2];

    fetch(`/problems/${problemTitle}/submissions`)
        .then(response => response.json())
        .then(submissions => {
            const container = document.getElementById('submissions');

            if (submissions.length === 0) {
                container.innerHTML = '<p>No submissions found</p>';
                return;
            }

            submissions.forEach(submission => {
                const div = document.createElement('div');
                div.innerHTML = submission;
                container.appendChild(div);
            });
        })
        .catch(error => {
            document.getElementById('submissions').innerHTML = '<p>Error loading submissions</p>';
        });
}