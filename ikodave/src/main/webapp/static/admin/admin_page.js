document.addEventListener('DOMContentLoaded', () => {
    const btn = document.getElementById('add-problem-btn');
    if (btn) {
        btn.addEventListener('click', () => {
            window.location.href = '/AddProblemPage';
        });
    }
});
