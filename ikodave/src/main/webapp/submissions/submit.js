document.getElementById('submitForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const problemId = document.getElementById('problemId').value;
    const language = document.getElementById('language').value;
    const solutionCode = document.getElementById('solutionCode').value;

    // Create submission object
    const submission = {
        problemId: problemId,
        codeLanguage: language,
        solutionCode: solutionCode
    };

    // Submit to server
    fetch('/submit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(submission)
    })
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById('result');
            resultDiv.className = 'result success';
            resultDiv.innerHTML = `
                    <h3>Submission Result:</h3>
                    <p><strong>Status:</strong> ${data.status}</p>
                    <p><strong>Message:</strong> ${data.message}</p>
                `;
        })
        .catch(error => {
            const resultDiv = document.getElementById('result');
            resultDiv.className = 'result error';
            resultDiv.innerHTML = `
                    <h3>Error:</h3>
                    <p>${error.message}</p>
                `;
        });
});