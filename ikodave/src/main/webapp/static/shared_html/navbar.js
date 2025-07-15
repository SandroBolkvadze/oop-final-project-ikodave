loadNavbar().catch(console.error);

async function loadNavbar() {
    await initNavBar();
    await addNavs();
}

async function initNavBar() {
    const navbar = document.getElementById('navbar-container')

    navbar.innerHTML = await fetch('/static/shared_html/navbar.html')
            .then(res => res.text())
            .catch(console.error);
}


async function addNavs() {
    const data = await fetch('/api/user/session')
    const session = await data.json();

    if (!session.loggedIn) {
        addNotLoggedInNavs();
    }
    else if (!session.verified) {
        addLoggedInNotVerifiedNavs();
    }
    else if (session.role === 'admin') {
        addLoggedInUserNavs();
    }
    else {
        addLoggedInAdminNavs();
    }

}

function addNotLoggedInNavs() {
    const navbar = document.getElementById('navbar-container');

    let homeNav = `<li class="nav-item" id="nav-home"><a class="nav-link" href="/home">Home</a></li>`;
    let verifyNav = `<li class="nav-item" id="nav-verify"><a class="nav-link" href="/verify">Verify</a></li>`;
    let profileNav = `<li class="nav-item" id="nav-profile"><a class="nav-link" href="/profile">Profile</a></li>`
    let problemsNav = `<li class="nav-item" id="nav-problems"><a class="nav-link" href="/problems">Problems</a></li>`
    let leaderboardNav = `<li class="nav-item" id="nav-leaderboard"><a class="nav-link" href="/leaderboard">Leaderboard</a></li>`

    navbar.append(homeNav);
    navbar.append(verifyNav);
    navbar.append(profileNav);
    navbar.append(problemsNav);
    navbar.append(leaderboardNav);
}

function addLoggedInUserNavs() {
    const navbar = document.getElementById('navbar-container');

    let homeNav = `<li class="nav-item" id="nav-home"><a class="nav-link" href="/home">Home</a></li>`;
    let profileNav = `<li class="nav-item" id="nav-profile"><a class="nav-link" href="/profile">Profile</a></li>`
    let problemsNav = `<li class="nav-item" id="nav-problems"><a class="nav-link" href="/problems">Problems</a></li>`
    let submissionsNav = `<li class="nav-item" id="nav-submissions"><a class="nav-link" href="/submissions-async">Submissions</a></li>`
    let leaderboardNav = `<li class="nav-item" id="nav-leaderboard"><a class="nav-link" href="/leaderboard">Leaderboard</a></li>`

    navbar.append(homeNav);
    navbar.append(profileNav);
    navbar.append(problemsNav);
    navbar.append(submissionsNav);
    navbar.append(leaderboardNav);
}

function addLoggedInAdminNavs() {
    const navbar = document.getElementById('navbar-container');

    let homeNav = `<li class="nav-item" id="nav-home"><a class="nav-link" href="/home">Home</a></li>`;
    let profileNav = `<li class="nav-item" id="nav-profile"><a class="nav-link" href="/profile">Profile</a></li>`
    let adminNav = `<li class="nav-item" id="nav-admin"><a class="nav-link" href="/admin">Admin</a></li>`;
    let problemsNav = `<li class="nav-item" id="nav-problems"><a class="nav-link" href="/problems">Problems</a></li>`
    let submissionsNav = `<li class="nav-item" id="nav-submissions"><a class="nav-link" href="/submissions-async">Submissions</a></li>`
    let leaderboardNav = `<li class="nav-item" id="nav-leaderboard"><a class="nav-link" href="/leaderboard">Leaderboard</a></li>`

    navbar.append(homeNav);
    navbar.append(profileNav);
    navbar.append(adminNav);
    navbar.append(problemsNav);
    navbar.append(submissionsNav);
    navbar.append(leaderboardNav);
}


function addLoggedInNotVerifiedNavs() {
    const navbar = document.getElementById('navbar-container');

    let homeNav = `<li class="nav-item" id="nav-home"><a class="nav-link" href="/home">Home</a></li>`;
    let registerNav = `<li class="nav-item" id="nav-register"><a class="nav-link" href="/registration">Register</a></li>`
    let signInNav = `<li class="nav-item" id="nav-signin"><a class="nav-link" href="/signin">Sign In</a></li>`
    let problemsNav = `<li class="nav-item" id="nav-problems"><a class="nav-link" href="/problems">Problems</a></li>`
    let leaderboardNav = `<li class="nav-item" id="nav-leaderboard"><a class="nav-link" href="/leaderboard">Leaderboard</a></li>`

    navbar.append(homeNav);
    navbar.append(registerNav);
    navbar.append(signInNav);
    navbar.append(problemsNav);
    navbar.append(leaderboardNav);
}



// toggleElement('nav-register', !loggedIn);
// toggleElement('nav-signin', !loggedIn);
// toggleElement('nav-profile', loggedIn);
// toggleElement('nav-admin', role === "Admin");
// toggleElement('nav-submissions', loggedIn);
//
// // Attach links
// attachNavbarLinks({
//     'nav-home': '/home',
//     'nav-register': '/registration',
//     'nav-signin': '/signin',
//     'nav-profile': '/profile',
//     'nav-problems': '/problems',
//     'nav-submissions': '/submissions-async',
//     'nav-leaderboard': '/leaderboard',
//     'nav-admin': '/AdminPage'
// });

// function toggleElement(id, show) {
//     const el = document.getElementById(id);
//     if (el) {
//         el.style.display = show ? '' : 'none';
//     }
// }
//
// function attachNavbarLinks(linkMap) {
//     for (const [id, href] of Object.entries(linkMap)) {
//         const li = document.getElementById(id);
//         if (li) {
//             const anchor = li.querySelector('a');
//             if (anchor) {
//                 anchor.addEventListener('click', (e) => {
//                     e.preventDefault();
//                     window.location.href = href;
//                 });
//             }
//         }
//     }
// }
