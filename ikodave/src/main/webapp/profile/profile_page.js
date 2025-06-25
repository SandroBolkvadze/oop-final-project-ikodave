function load_profile(){
    fetch('/ikodave_war/profile')
        .then(res => res.json())
        .then(username => {
            document.getElementById('username-placeholder').textContent = username || "Guest";
        })
        .catch(err => console.error("Failed to load user", err));
}