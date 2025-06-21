const FETCH_API = 'https://localhost:8080/problems';

document.addEventListener('DOMContentLoaded', filter);

async function filter() {
    console.log('here');

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

    const response = await fetch(FETCH_API, {
        method: 'POST',
        headers: {'Content-Type': 'applications/json'},
        body: JSON.stringify(filters)
    });

    const data = await response.json();

}

