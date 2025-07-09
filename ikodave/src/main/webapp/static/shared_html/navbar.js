async function loadNavbar() {
    try {
        // Load and inject the navbar HTML
        const navbarRes = await fetch('/static/shared_html/navbar.html');
        const html = await navbarRes.text();
        document.getElementById('navbar-container').innerHTML = html;

        // Get user session
        const sessionRes = await fetch('/api/user/session');
        const { loggedIn } = await sessionRes.json();

        // Show/hide navbar items based on login status
        toggleNavbarItems(loggedIn);

        // Set up navbar navigation links
        setupNavLinks({
            'nav-home': '/home',
            'nav-register': '/registration',
            'nav-signin': '/signin',
            'nav-profile': '/profile',
            'nav-problems': '/problems',
            'nav-leaderboard': '/leaderboard'
        });

    } catch (err) {
        console.error("Failed to load navbar:", err);
    }
}

function toggleNavbarItems(loggedIn) {
    document.getElementById('nav-register')?.style.setProperty('display', loggedIn ? 'none' : 'block');
    document.getElementById('nav-signin')?.style.setProperty('display', loggedIn ? 'none' : 'block');
    document.getElementById('nav-profile')?.style.setProperty('display', loggedIn ? 'block' : 'none');
}

function setupNavLinks(linkMap) {
    for (const [id, href] of Object.entries(linkMap)) {
        const link = document.querySelector(`#${id} a`);
        if (link) {
            link.addEventListener('click', function (e) {
                e.preventDefault();
                window.location.href = href;
            });
        }
    }
}
