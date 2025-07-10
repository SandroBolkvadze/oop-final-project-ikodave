document.addEventListener("DOMContentLoaded", () => {
    load_profile();
})

function load_profile(){
    const parts = window.location.pathname.split('/').filter(Boolean);
    const username = parts[parts.length - 1];

    const sendData = {
        username: username,
    }

    fetch('/api/user/profile', {
        method: 'Post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(sendData)
    })
        .then(res => res.json())
        .then(stats => {
            console.log(stats);
        })
        .catch(err => console.error("Failed to load user", err));
}
