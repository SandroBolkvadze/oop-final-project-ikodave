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


        })


}