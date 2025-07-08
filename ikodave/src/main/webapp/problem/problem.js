document.addEventListener('DOMContentLoaded', loadProblem);

async function loadProblem(){
    const parts = window.location.pathname.split('/');
    const problemTitle = parts[parts.length - 1];

    const sendData = {
        problemTitle : problemTitle
    }

    await fetch(`/problems/problem`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(sendData)
    })
        .then(response => response.json())
        .then(data => {
            let problemDescription = data.problemDescription;
            let problemStatus = data.problemStatus;
            let problemTopics = data.problemTopics;
            let problemDifficulty = data.problemDifficulty;
            let problemTestCases = data.problemTestCases;
            let problemTime = data.problemTime;
            let problemMemory = data.problemMemory;

            document.getElementById('problemTitle').textContent = problemTitle;
            document.title = `Problem ${problemTitle} | Ikodave`;

            document.getElementById('descriptionText').textContent = problemDescription;

            document.getElementById('statusText').textContent = problemStatus;

            document.getElementById('difficultyText').textContent = problemDifficulty;

            document.getElementById('problemLimits').innerHTML =
                `Time limit per test: <strong>${problemTime} ms</strong>;<br>
                 Memory limit per test: <strong>${problemMemory} mb</strong>.`;

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
            // Handle error - maybe show a message to the user
            document.getElementById('problemTitle').textContent = 'Error loading problem';
            document.getElementById('descriptionText').textContent = 'Failed to load problem data. Please try again.';
        });
}