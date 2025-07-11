document.addEventListener('DOMContentLoaded', () => {
    addListeners();
    loadProblem().catch(console.error);
});

function addListeners() {
    document.getElementById('submissionsButton').onclick = mySubmissionsProblem;
    document.getElementById('submitSolutionButton').onclick = submitSolutionButton;
}

function mySubmissionsProblem() {
    const title = getProblemTitleFromPath();
    window.location.href = `/problems/submissions/${encodeURIComponent(title)}`;
}

function submitSolutionButton() {
    const title = getProblemTitleFromPath();
    window.location.href = `/problems/submit/${encodeURIComponent(title)}`;
}

async function loadProblem() {
    const title = getProblemTitleFromPath();
    const data = await fetch(`/api/problems/problem`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ problemTitle: title })
    }).then(r => r.json());

    // Title
    document.getElementById('problemTitle').textContent = title;

    // Description
    document.getElementById('descriptionText').textContent = data.problemDescription;

    // Status badge
    const statusTextElem = document.getElementById('statusText');
    const statusValue = data.problemStatus.toLowerCase();
    statusTextElem.textContent = data.problemStatus;
    const statusClass = ['attempted', 'accepted', 'todo', 'solved'].includes(statusValue)
        ? statusValue
        : 'todo';
    statusTextElem.className = 'status ' + statusClass;

    // Difficulty badge
    const diffTextElem = document.getElementById('difficultyText');
    const diffValue = data.problemDifficulty.toLowerCase();
    diffTextElem.textContent = data.problemDifficulty;
    const diffClass = ['easy', 'medium', 'hard'].includes(diffValue)
        ? diffValue
        : 'easy';
    diffTextElem.className = 'difficulty ' + diffClass;

    // Input/Output
    document.getElementById('inputText').textContent  = data.problemInputSpec;
    document.getElementById('outputText').textContent = data.problemOutputSpec;

    // Limits (remove memory limit)
    document.getElementById('problemLimits').innerHTML =
        `Time limit per test: <strong>${data.problemTime} ms</strong>.`;

    // Test cases
    const testCasesContainer = document.getElementById('testCases');
    testCasesContainer.innerHTML = '';
    (data.problemTestCases || []).forEach(tc => {
        const ex = document.createElement('div');
        ex.className = 'example';
        ex.innerHTML = `
            <div><strong>Input:</strong></div>
            <pre>${tc.problemInput}</pre>
            <div><strong>Output:</strong></div>
            <pre>${tc.problemOutput}</pre>
        `;
        testCasesContainer.appendChild(ex);
    });

    // Topics
    const topicsContainer = document.getElementById('problemTopics');
    topicsContainer.innerHTML = '';
    (data.problemTopics || []).forEach(topic => {
        const tag = document.createElement('span');
        tag.className = 'tag';
        tag.textContent = topic;
        topicsContainer.appendChild(tag);
    });
}

function getProblemTitleFromPath() {
    return window.location.pathname.split('/').filter(Boolean).pop();
}
