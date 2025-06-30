async function loadNavbar() {
    const navbarRes = await fetch('/ikodave_war/shared_html/navbar.html');
    const html = await navbarRes.text();
    document.getElementById('navbar-container').innerHTML = html;

    const sessionRes = await fetch('/ikodave_war/user-session');
    const data = await sessionRes.json();

    if (data.loggedIn) {
        document.getElementById("nav-register").style.display = "none";
        document.getElementById("nav-signin").style.display = "none";
    } else {
        document.getElementById("nav-profile").style.display = "none";
    }
}
