function addMoreTests() {
    const addTestBtn = document.getElementById('add-test-btn');
    const testCasesContainer = document.getElementById('test-cases-container');

    addTestBtn.addEventListener('click', () => {
        const testCaseDiv = document.createElement('div');
        testCaseDiv.classList.add('test-case', 'row', 'g-3', 'align-items-start', 'mb-3');

        testCaseDiv.innerHTML = `
            <div class="col-md-6">
                <label class="form-label">Input</label>
                <textarea name="testInput[]" class="form-control" rows="3" required></textarea>
            </div>
            <div class="col-md-6">
                <label class="form-label">Output</label>
                <textarea name="testOutput[]" class="form-control" rows="3" required></textarea>
            </div>
        `;

        testCasesContainer.appendChild(testCaseDiv);
    });
}

async function addDifficulties() {
    try {
        const response = await fetch('/api/problems/difficulties');
        const difficulties = await response.json();
        const diffDiv = document.getElementById('difficulty-container');
        diffDiv.innerHTML = '';

        difficulties.forEach(d => {
            const inputId = `diff-${d.id}`;
            const radioHTML = `
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="difficulty" id="${inputId}" value="${d.difficulty}" required>
                    <label class="form-check-label" for="${inputId}">${d.difficulty}</label>
                </div>
            `;
            diffDiv.insertAdjacentHTML('beforeend', radioHTML);
        });
    } catch (err) {
        console.error('Failed to load difficulties:', err);
    }
}

async function addTopics() {
    try {
        const response = await fetch('/api/problems/topics');
        const topics = await response.json();
        const dropdown = document.getElementById('topics-dropdown');
        dropdown.innerHTML = '';

        topics.forEach(t => {
            const id = `topic-${t.id}`;
            const label = document.createElement('label');
            label.innerHTML = `
                <input type="checkbox" value="${t.topic}" name="topics" id="${id}"> ${t.topic}
            `;
            dropdown.appendChild(label);
        });

        dropdown.addEventListener('change', () => {
            const checked = dropdown.querySelectorAll('input[type="checkbox"]:checked');
            const labels = Array.from(checked).map(cb => cb.value);
            document.getElementById('topic-select-button').textContent = labels.length > 0
                ? labels.join(', ')
                : 'Select topics';
        });
    } catch (err) {
        console.error('Failed to load topics:', err);
    }
}

function problemSubmitted(event) {
    event.preventDefault();

    const title = document.getElementById('problem-title').value.trim();
    const description = document.getElementById('problem-description').value.trim();
    const inputSpec = document.getElementById('input-spec').value.trim();
    const outputSpec = document.getElementById('output-spec').value.trim();
    const difficultyInput = document.querySelector('input[name="difficulty"]:checked');
    const difficulty = difficultyInput ? difficultyInput.value : null;
    const timeLimit = parseInt(document.getElementById('time-limit').value.trim());

    const testInputs = document.getElementsByName('testInput[]');
    const testOutputs = document.getElementsByName('testOutput[]');
    const testCases = [];

    for (let i = 0; i < testInputs.length; i++) {
        const input = testInputs[i].value.trim();
        const output = testOutputs[i].value.trim();
        if (input && output) {
            testCases.push({ input, output });
        }
    }

    const selectedTopics = Array.from(document.querySelectorAll('#topics-dropdown input[type="checkbox"]:checked'))
        .map(cb => cb.value);

    const problemData = {
        title,
        description,
        inputSpec,
        outputSpec,
        difficulty,
        timeLimit,
        testCases,
        topics: selectedTopics
    };

    fetch('/api/AddProblemServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(problemData)
    }).then(res => {
        if (res.ok) {
            alert('Problem added successfully');
        } else {
            alert('Failed to add problem');
        }
    }).catch(err => console.error(err));
}

document.addEventListener('DOMContentLoaded', () => {
    addMoreTests();
    addDifficulties();
    addTopics();

    document.getElementById('problem-form').addEventListener('submit', problemSubmitted);

    const button = document.getElementById('topic-select-button');
    const dropdown = document.getElementById('topics-dropdown');
    const wrapper = document.getElementById('topics-dropdown-wrapper');

    button.addEventListener('click', () => {
        dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
    });

    document.addEventListener('click', (e) => {
        if (!wrapper.contains(e.target)) {
            dropdown.style.display = 'none';
        }
    });
});
