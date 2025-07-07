async function loadNavbar() {
    const navbarRes = await fetch('/shared_html/navbar.html');
    const html = await navbarRes.text();
    document.getElementById('navbar-container').innerHTML = html;

    const sessionRes = await fetch('/user-session');
    const data = await sessionRes.json();

    if (data.loggedIn) {
        document.getElementById("nav-register").style.display = "none";
        document.getElementById("nav-signin").style.display = "none";
    } else {
        document.getElementById("nav-profile").style.display = "none";
    }

    const homeLink = document.querySelector('#nav-home a');
    if (homeLink) {
        homeLink.addEventListener('click', function (e) {
            e.preventDefault();
            window.location.href = "/ikodave_war/home";
        });
    }

    // ✅ Fix the registration link to route through servlet
    const regLink = document.querySelector('#nav-register a');
    if (regLink) {
        regLink.addEventListener('click', function (e) {
            e.preventDefault();
            window.location.href = "/ikodave_war/registration";
        });
    }

    const signLink = document.querySelector('#nav-signin a');
    if(signLink){
        signLink.addEventListener('click', function (e){
            e.preventDefault();
            window.location.href = "/ikodave_war/signin";
        });
    }

    const profileLink = document.querySelector('#nav-profile a');
    if(profileLink){
        profileLink.addEventListener('click', function(e){
            e.preventDefault();
            window.location.href = "/ikodave_war/profile-page"
        });
    }
    const problemLink = document.querySelector('#nav-problems a');
    if(problemLink){
        problemLink.addEventListener('click', function(e){
            e.preventDefault();
            window.location.href = "/ikodave_war/problems"
        });
    }
}
