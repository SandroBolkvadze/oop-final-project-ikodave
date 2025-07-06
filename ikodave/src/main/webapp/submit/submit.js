document.addEventListener('DOMContentLoaded', () => {
    loadLanguages()
        .catch((error) => {
            console(error)
        });

    document
        .getElementById('submitForm')
        .addEventListener('submit', (e) => submitSolution(e));
});


function submitSolution(e) {
    e.preventDefault();

    const problemId = document.getElementById('problemId').value;
    const language = document.getElementById('language').value;
    const solutionCode = document.getElementById('solutionCode').value;

    const submission = {
        problemId: problemId,
        codeLanguage: language,
        solutionCode: solutionCode
    };

    fetch('/submit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(submission)
    }).catch(error => {
        console.log(error);
    });
}

async function loadLanguages() {
    try {
        const response = await fetch('/languages');

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


