function loadProblemTitle() {
    const parts = window.location.pathname.split('/').filter(Boolean);
    const title = parts[parts.length - 1];

    const inputEl = document.getElementById('problemTitle');
    const wrapper = inputEl.parentElement;



    const textEl = document.createElement('span');
    textEl.id = 'problemTitle';
    textEl.textContent = title;
    textEl.style.fontWeight = 'bold';

    wrapper.replaceChild(textEl, inputEl)
}

let editor;

document.addEventListener('DOMContentLoaded', () => {
    loadProblemTitle();
    loadLanguages().catch(console.error);

    require.config({ paths: { 'vs': 'https://cdn.jsdelivr.net/npm/monaco-editor@0.47.0/min/vs' }});
    require(['vs/editor/editor.main'], () => {
        // create the editor
        editor = monaco.editor.create(document.getElementById('editor'), {
            value: "// Write your solution here...\n",
            language: 'plaintext',  // will be set by dropdown
            theme: 'vs-light',
            automaticLayout: true,
            fontFamily: 'Fira Mono, Menlo, Monaco, Consolas, monospace',
            fontSize: 16,
            minimap: { enabled: true },
            lineNumbers: 'on',
            wordWrap: 'on',
            tabSize: 4,
            scrollbar: { vertical: 'auto', horizontal: 'auto' },
        });

        // switch language when user selects
        document.getElementById('language').addEventListener('change', e => {
            const lang = e.target.value || 'plaintext';
            monaco.editor.setModelLanguage(editor.getModel(), lang);
        });
    });


    document.getElementById('submitForm').addEventListener('submit', e => {
        e.preventDefault();
        const title    = document.getElementById('problemTitle').textContent.trim();
        const language = document.getElementById('language').value;
        const code     = editor.getValue();

        fetch('/api/problems/submit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ problemTitle: title, solutionCode: code, codeLanguage: language })
        })
            .then(res => res.json())
            .then(json => {
                if (json.redirect) window.location.href = json.redirect;
            })
            .catch(console.error);
    });
});

