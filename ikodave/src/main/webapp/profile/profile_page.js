function load_profile() {
    fetch("/profile")
        .then(res => {
            if (!res.ok) throw new Error("Not logged in");
            return res.json();
        })
        .then(data => {
            document.getElementById("username-placeholder").textContent = data.username;
            document.getElementById("user-id").textContent = data.userId;
        })
        .catch(err => {
            document.getElementById("error").textContent = "Please log in to view your profile. Redirecting to login page...";
            document.getElementById("profile-info").style.display = "none";
            setTimeout(() => {
                window.location.href = "/authentication/signin.html";
            }, 3000);
        });
}
