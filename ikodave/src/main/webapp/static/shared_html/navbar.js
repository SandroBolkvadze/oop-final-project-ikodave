async function loadNavbar() {
    try {
        // Inject navbar HTML
        const navbarHTML = await fetch('/static/shared_html/navbar.html').then(res => res.text());
        document.getElementById('navbar-container').innerHTML = navbarHTML;

        // Get session info
        const session = await fetch('/api/user/session').then(res => res.json());
        const loggedIn = session.loggedIn === "true"; // or === true if you fix servlet
        const role = session.role || null;

        // Apply navbar visibility logic
        toggleElement('nav-register', !loggedIn);
        toggleElement('nav-signin', !loggedIn);
        toggleElement('nav-profile', loggedIn);
        toggleElement('nav-admin', role === "Admin");
        toggleElement('nav-submissions', loggedIn);

        // Attach page navigation
        attachNavbarLinks({
            'nav-home': '/home',
            'nav-register': '/registration',
            'nav-signin': '/signin',
            'nav-profile': '/profile',
            'nav-problems': '/problems',
            'nav-leaderboard': '/leaderboard',
            'nav-admin': '/AdminPage',
            'nav-submissions': '/submissions'
        });

    } catch (err) {
        console.error("Failed to load navbar:", err);
    }
}

function toggleElement(id, show) {
    const el = document.getElementById(id);
    if (el) el.style.display = show ? 'block' : 'none';
}

function attachNavbarLinks(linkMap) {
    for (const [id, href] of Object.entries(linkMap)) {
        const wrapper = document.getElementById(id);
        const anchor = wrapper?.querySelector('a');
        if (anchor) {
            anchor.addEventListener('click', e => {
                e.preventDefault();
                window.location.href = href;
            });
        }
    }
}
