document.addEventListener('DOMContentLoaded', () => {
    loadLanguages()
        .catch((error) => {
            console.log(error)
        });

    document
        .getElementById('submitForm')
        .addEventListener('submit', (e) => submitSolution(e));
});


function submitSolution(e) {
    e.preventDefault();

    const title = document.getElementById('problemTitle').value;
    const language = document.getElementById('language').value;
    const code = document.getElementById('solutionCode').value;

    console.log(title);

    const submission = {
        problemTitle: title,
        solutionCode: code,
        codeLanguage: language
    };

    fetch('/api/problems/submit', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(submission)
    })
        .then(res => res.json())
        .then(json => {
            console.log(json);
            if (json.redirect) {
                window.location.href = json.redirect;
            }
        })
        .catch(console.error);
}

