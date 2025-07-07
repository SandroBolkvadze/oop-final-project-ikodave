const FETCH_API = 'https://localhost:8080/problem';

document.addEventListener('DOMContentLoaded', getTitle);

async function getTitle(){
    let title = "Ants";
    const Title = {
        title
    };
    const response = await fetch(FETCH_API, {
        method : 'POST',
        headers: {'Content-Type': 'applications/json'},
        body : JSON.stringify(Title)
    });
    const data = await response.json();
}