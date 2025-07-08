document.addEventListener('DOMContentLoaded', loadProblem);

async function loadProblem(){
    console.log('loading problem');

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
            let problemDescription = data.problemDescription;
            let problemStatus = data.problemStatus;
            let problemTopics = data.problemTopics;
            let problemDifficulty = data.problemDifficulty;
            let problemTestCases = data.problemTestCases;
            let problemInputSpec = data.problemInputSpec;
            let problemOutputSpec = data.problemOutputSpec;
            let timeLimit = data.problemTime;
            let memoryLimit = data.problemMemory;

            // Set problem title
            document.getElementById('problemTitle').textContent = problemTitle;
            document.title = `Problem ${problemTitle} | Ikodave`;

            // Set problem description
            document.getElementById('descriptionText').textContent = problemDescription;

            // Set problem status
            document.getElementById('statusText').textContent = problemStatus;

            // Set problem difficulty
            document.getElementById('difficultyText').textContent = problemDifficulty;

            document.getElementById('inputText').textContent = problemInputSpec;
            document.getElementById('outputText').textContent = problemOutputSpec;

            // Set time and memory limits
            document.getElementById('problemLimits').innerHTML =
                `Time limit per test: <strong>${timeLimit} ms</strong>;<br>
                 Memory limit per test: <strong>${memoryLimit} mb</strong>.`;

            // Set test cases/examples
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

            // Set problem topics/tags
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