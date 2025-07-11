// /static/shared_html/navbar.js

async function loadNavbar() {
    try {
        // Load the navbar HTML into the container
        const navbarHTML = await fetch('/static/shared_html/navbar.html').then(res => res.text());
        document.getElementById('navbar-container').innerHTML = navbarHTML;

        // Get user session info
        const session = await fetch('/api/user/session').then(res => res.json());
        const loggedIn = session.loggedIn === "true" || session.loggedIn === true;
        const role = session.role || null;

        // Show/hide based on session
        toggleElement('nav-register', !loggedIn);
        toggleElement('nav-signin', !loggedIn);
        toggleElement('nav-profile', loggedIn);
        toggleElement('nav-admin', role === "Admin");
        toggleElement('nav-submissions', loggedIn);

        // Attach links
        attachNavbarLinks({
            'nav-home': '/home',
            'nav-register': '/registration',
            'nav-signin': '/signin',
            'nav-profile': '/profile',
            'nav-problems': '/problems',
            'nav-submissions': '/submissions',
            'nav-leaderboard': '/leaderboard',
            'nav-admin': '/AdminPage'
        });

    } catch (err) {
        console.error("Navbar failed to load:", err);
    }
}

function toggleElement(id, show) {
    const el = document.getElementById(id);
    if (el) {
        el.style.display = show ? '' : 'none';
    }
}

function attachNavbarLinks(linkMap) {
    for (const [id, href] of Object.entries(linkMap)) {
        const li = document.getElementById(id);
        if (li) {
            const anchor = li.querySelector('a');
            if (anchor) {
                anchor.addEventListener('click', (e) => {
                    e.preventDefault();
                    window.location.href = href;
                });
            }
        }
    }
}
