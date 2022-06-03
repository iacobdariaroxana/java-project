function send() {
    let sendResponse = document.getElementById('send-response');
    let messageType = document.getElementById("messageType");
    let message = document.getElementById("messageText");
    let usernames = document.getElementById("usernames");

    let valid = true;
    if (!messageType.checkValidity()) {
        messageType.reportValidity();
        valid = false;
    }
    if (!message.checkValidity()) {
        message.reportValidity();
        valid = false;
    }
    if (!usernames.checkValidity()) {
        usernames.reportValidity();
        valid = false;
    }

    if (valid) {
        let myJwt = parseJwt(window.localStorage.getItem("token"));

        let url;
        if (messageType.value == 1) {
            url = "http://localhost:2022/messages";
        } else if (messageType.value == 2) {
            url = "http://localhost:2022/messages/multiple";
        }
        fetch(`${url}?fromUsername=${myJwt.sub}&toUsername=${usernames.value}&message=${message.value}&type=${messageType.value}`, {
            method: "POST",
            mode: "cors"
        }).then(async res => {
            sendResponse.innerHTML = await res.text();
            sendResponse.removeAttribute("hidden");
        }).catch(err => console.log(err));
    }
}