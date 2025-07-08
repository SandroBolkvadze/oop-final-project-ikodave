

function addListeners() {

}

async function loadDifficulties() {

}

async function loadStatuses() {

}

async function filter() {

    let title = 'add-numbers';
    let difficulty = 'MEDIUM';
    let status = 'solved';
    let topics = ['graph', 'dp', 'math'];

    const filters =  {
        title,
        difficulty,
        status,
        topics
    };

    const response = await fetch(``, {
        method: 'POST',
        headers: {'Content-Type': 'applications/json'},
        body: JSON.stringify(filters)
    });

    const data = await response.json();

}

