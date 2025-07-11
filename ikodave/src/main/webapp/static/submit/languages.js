async function loadLanguages() {
    const response = await fetch('/api/languages');

    if (!response.ok) {
        throw new Error('Failed to fetch languages');

    }

    const languageSelect = document.getElementById('language');
    languageSelect.innerHTML = '<option value="">Loading language...</option>';

    const languages = await response.json();

    languageSelect.innerHTML = `
      <option value="" disabled selected hidden>Select Language</option>
    `;

    languages.forEach(language => {
        const option = document.createElement('option');
        option.textContent = language.language;
        option.value = language.language; // Set value for validation
        languageSelect.appendChild(option);
    });


}

