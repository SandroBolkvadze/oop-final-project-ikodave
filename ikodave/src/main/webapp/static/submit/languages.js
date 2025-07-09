async function loadLanguages() {
    try {
        const response = await fetch('/api/languages');

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

