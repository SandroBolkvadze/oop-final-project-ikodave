document.addEventListener('DOMContentLoaded', () => {
    document
        .getElementById('submitForm')
        .addEventListener('submit', (e) => submitSolution(e));
});

loadLanguages()
    .catch((error) => {
        console.log(error)
    });

function submitSolution(e) {
    e.preventDefault();

    const title = document.getElementById('problemTitle').value;
    const language = document.getElementById('language').value;
    const code = document.getElementById('solutionCode').value;

    const submission = {
        problemTitle: title,
        solutionCode: code,
        codeLanguage: language
    };

    fetch('/problems/submit', {
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

async function loadLanguages() {
    try {
        const response = await fetch('/code-languages');

        if (!response.ok) {
            throw new Error('Failed to fetch languages');
        }

        const languages = await response.json();
        const languageSelect = document.getElementById('language');

        languageSelect.innerHTML = '<option value="">Choose language</option>';

        languages.forEach(language => {
            const option = document.createElement('option');
            option.textContent = language.language;
            languageSelect.appendChild(option);
        });

    } catch (error) {
        console.error('Error loading languages:', error);
    }
}


