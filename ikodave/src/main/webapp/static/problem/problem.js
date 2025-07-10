document.addEventListener('DOMContentLoaded', (e) => {
    addListeners(e);

    loadProblem(e)
        .catch(error => console.log(error));
});

function addListeners(e) {
    document.getElementById('submissionsButton').onclick = mySubmissionsProblem;
    document.getElementById('submitSolutionButton').onclick = submitSolutionButton;
}

function mySubmissionsProblem() {
    const parts = window.location.pathname.split('/').filter(Boolean);
    const title = parts[parts.length - 1];
    window.location.href = `/problems/submissions/${encodeURIComponent(title)}`;
}

function submitSolutionButton() {
    const parts = window.location.pathname.split('/').filter(Boolean);
    const title = parts[parts.length - 1];
    window.location.href = `/problems/submit/${encodeURIComponent(title)}`;
}

async function loadProblem(e){
    const parts = window.location.pathname.split('/');
    const problemTitle = parts[parts.length - 1];

    const sendData = {
        problemTitle : problemTitle
    }

    await fetch(`/api/problems/problem`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(sendData)
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('problemTitle').textContent = problemTitle;

            document.title = `Problem ${problemTitle} | Ikodave`;

            const problemDescription = data.problemDescription;
            document.getElementById('descriptionText').textContent = problemDescription;

            const problemStatus = data.problemStatus;
            document.getElementById('statusText').textContent = problemStatus;

            const problemDifficulty = data.problemDifficulty;
            document.getElementById('difficultyText').textContent = problemDifficulty;


            const problemInputSpec = data.problemInputSpec;
            document.getElementById('inputText').textContent = problemInputSpec;

            const problemOutputSpec = data.problemOutputSpec;
            document.getElementById('outputText').textContent = problemOutputSpec;


            const timeLimit = data.problemTime;
            const memoryLimit = data.problemMemory;
            document.getElementById('problemLimits').innerHTML =
                `Time limit per test: <strong>${timeLimit} ms</strong>;<br>
                 Memory limit per test: <strong>${memoryLimit} mb</strong>.`;


            const problemTestCases = data.problemTestCases;
            const testCasesContainer = document.getElementById('testCases');
            if (problemTestCases && problemTestCases.length > 0) {
                problemTestCases.forEach((testCase, index) => {
                    const exampleDiv = document.createElement('div');
                    exampleDiv.className = 'example';
                    exampleDiv.innerHTML = `
                        <div><strong>Input:</strong></div>
                        <pre>${testCase.problemInput}</pre>
                        <div><strong>Output:</strong></div>
                        <pre>${testCase.problemOutput}</pre>
                    `;
                    testCasesContainer.appendChild(exampleDiv);
                });
            }

            const problemTopics = data.problemTopics;
            const topicsContainer = document.getElementById('problemTopics');
            if (problemTopics && problemTopics.length > 0) {
                problemTopics.forEach(topic => {
                    const tagSpan = document.createElement('span');
                    tagSpan.className = 'tag';
                    tagSpan.textContent = topic;
                    topicsContainer.appendChild(tagSpan);
                });
            }
        })
        .catch(error => {
            console.error('Error loading problem:', error);

            document.getElementById('problemTitle').textContent = 'Error loading problem';
            document.getElementById('descriptionText').textContent = 'Failed to load problem data. Please try again.';
        });
}