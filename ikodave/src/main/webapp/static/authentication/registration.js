// registration.js

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById("registration-form");
    const errorMsg = document.getElementById("error-msg");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();

        try {
            const res = await fetch("/registration", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password }),
            });
            const data = await res.json();

            if (data.status === "exists") {
                errorMsg.innerHTML =
                    '<div class="alert alert-danger text-center">Username is already taken.</div>';
            } else if (data.status === "ok") {
                window.location.href = "/profile";
            }
        } catch (err) {
            console.error("Registration error:", err);
            errorMsg.innerHTML =
                '<div class="alert alert-danger text-center">Something went wrong. Try again.</div>';
        }
    });
});
