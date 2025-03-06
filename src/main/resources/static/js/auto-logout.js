let timeout;

function logoutUser() {
    window.location.href = "/logout"; // Redirect to logout page after inactivity
}

function resetTimer() {
    clearTimeout(timeout);
    timeout = setTimeout(logoutUser, 15 * 60 * 1000); // 15 minutes (600,000 milliseconds)
}

// Detect user activity on the page
window.onload = resetTimer;
document.onmousemove = resetTimer;
document.onkeydown = resetTimer;
document.onclick = resetTimer;
