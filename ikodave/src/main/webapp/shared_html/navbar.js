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
        })
    }
}
