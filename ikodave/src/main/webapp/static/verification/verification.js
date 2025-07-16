let secondsLeft = 0;
let timerInterval = null;

function fetchTimeLeft() {
    fetch('/api/verification/time')
        .then(response => {
            if (!response.ok) throw new Error("Failed to fetch time left");
            return response.json();
        })
        .then(data => {
            const expiry = new Date(data.expiry);
            const now = new Date();
            secondsLeft = Math.floor((expiry - now) / 1000);
            updateTimer();
            if (timerInterval) clearInterval(timerInterval);
            timerInterval = setInterval(tick, 1000);
        })
        .catch(err => {
            document.getElementById('timer').textContent = "Could not load timer.";
        });
}

function updateTimer() {
    if (secondsLeft <= 0) {
        document.getElementById('timer').textContent = "Verification expired. Please register again.";
        document.getElementById('resend-btn').disabled = true;
        clearInterval(timerInterval);
        return;
    }
    const minutes = Math.floor(secondsLeft / 60);
    const seconds = secondsLeft % 60;
    document.getElementById('timer').textContent = `Time left: ${minutes}:${seconds.toString().padStart(2, '0')}`;
}

function tick() {
    secondsLeft--;
    updateTimer();
    if (secondsLeft <= 0) {
        clearInterval(timerInterval);
    }
}

document.getElementById('resend-btn').addEventListener('click', function() {
    fetch('/verification/resend', { method: 'POST' })
        .then(response => {
            if (!response.ok) throw new Error("Failed to resend email");
            return response.json();
        })
        .then(data => {
            document.getElementById('message').textContent = "Verification email resent!";
            document.getElementById('error').textContent = "";
            fetchTimeLeft();
        })
        .catch(err => {
            document.getElementById('error').textContent = "Could not resend email. Try again later.";
            document.getElementById('message').textContent = "";
        });
});

fetchTimeLeft();