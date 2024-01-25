document.addEventListener('DOMContentLoaded', function() {
    var element = document.getElementsByClassName("chat-messages")[0];
    element.scrollTop = element.scrollHeight;
    var input = document.getElementById('chat-input');
    input.focus();

    var UUIDExists = checkIfCookieExists('UUID');
    if (!UUIDExists) {
        var UUID = generateUUID();
        document.cookie = "UUID=" + UUID;
    }
});

function generateUUID() {
    var queryParams = new URLSearchParams(window.location.search);
    var UUID = queryParams.get('user') + '-' + new Date().getTime();
    return UUID;
}

function checkIfCookieExists(cookieName) {
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.startsWith(cookieName + '=')) {
            return true;
        }
    }
    return false;
}

function getCookieValue(cookieName) {
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].trim();
        if (cookie.startsWith(cookieName + '=')) {
            return cookie.substring(cookieName.length + 1);
        }
    }
    return null;
}

function handleInputChange(event) {
    if (event.key === 'Enter') {
        var inputValue = document.getElementById('chat-input').value;
        var UUID = getCookieValue('UUID');
        var user = UUID.split('-')[0];
        window.location.href = `/add-message?s=${inputValue}&user=${user}`
    }
}