function login() {
    const form = document.getElementById('login');
    let invalidCredentials = document.getElementById("credentialsNotValid");
    // handle the form data
    const username = form.elements[0];
    const password = form.elements[1];
    if ((/^[a-zA-Z0-9]+$/.test(username.value)) && (username.value.length > 6) && /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/.test(password.value)) {
        fetch("http://localhost:2022/login", {
            method: "POST",
            mode: "cors",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "username": username.value,
                "password": password.value
            })
        }).then(async res => {
            if (res.status === 200) {
                let token = await res.text();
                window.localStorage.setItem("token", token);
                console.log(token);
                toPage('/myAccount');
            } else {
                console.log(res.status);
                invalidCredentials.innerHTML = "incorrect username or password";
                invalidCredentials.removeAttribute("hidden");
            }
        }).catch(err => console.log(err));
    } else {
        invalidCredentials.removeAttribute("hidden");
    }
}