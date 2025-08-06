// Navbar mobile menu
const navbarMenu = document.querySelector(".navbar .links");
const hamburgerBtn = document.querySelector(".hamburger-btn");
if (navbarMenu && hamburgerBtn) {
    const hideMenuBtn = navbarMenu.querySelector(".close-btn");
    // Show mobile menu
    hamburgerBtn.addEventListener("click", () => {
        navbarMenu.classList.toggle("show-menu");
    });
    // Hide mobile menu
    if (hideMenuBtn) {
        hideMenuBtn.addEventListener("click", () =>  hamburgerBtn.click());
    }
}

// Sidebar toggle for small screens
const sidebarToggle = document.getElementById('sidebarToggle');
const sidebar = document.getElementById('sidebar');
if (sidebarToggle && sidebar) {
    sidebarToggle.addEventListener('click', function() {
        sidebar.classList.toggle('show');
    });
    // Hide sidebar on nav click (mobile)
    document.querySelectorAll('.sidebar .nav-link').forEach(link => {
        link.addEventListener('click', function() {
            if(window.innerWidth < 992) sidebar.classList.remove('show');
        });
    });
}