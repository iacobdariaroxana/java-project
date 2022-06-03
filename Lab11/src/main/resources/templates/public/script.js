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
                const token = await res.text();
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

function register() {
    const form = document.getElementById('login');
    let invalidCredentials = document.getElementById("credentialsNotValid");
    // handle the form data
    const username = form.elements[0];
    const password = form.elements[1];
    const confirmPassword = form.elements[2];
    console.log(username.value + " " + password.value + " " + confirmPassword.value)
    let valid = (/^[a-zA-Z0-9]+$/.test(username.value)) && (username.value.length > 6) && /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/.test(password.value);
    if (valid && (confirmPassword.value === password.value)) {
        fetch("http://localhost:2022/users/register", {
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
            if (res.status === 201) {
                toPage('/');
            } else if (res.status === 400) {
                invalidCredentials.innerHTML = await res.text();
                invalidCredentials.removeAttribute('hidden');
            }
            res = await res.json();
            console.log(res);
        }).catch(err => console.log(err));
    } else {
        if (confirmPassword.value !== password.value && valid) {
            invalidCredentials.innerHTML = "Passwords don't match!";
        } else {
            invalidCredentials.innerHTML = "Username must have at least 7 alphanumeric characters. Password must have at least 8 chars, with one special char, one upper char and one numeric char.";
        }
        if (invalidCredentials.hasAttribute("hidden")) {
            invalidCredentials.removeAttribute("hidden");
        }
    }
}

function toPage(page) {
    window.location = page;
}

function setTab(tabName)
{
    let tabs = document.getElementsByClassName('tabs');
    for (let i = 0; i < tabs.length; ++i)
    {
        if (tabs[i].id != tabName && !tabs[i].hasAttribute('hidden'))
        {
            tabs[i].setAttribute('hidden', 'true');
        }
        if (tabs[i].id == tabName) {
            tabs[i].removeAttribute('hidden');
        }
    }
}

function showFriendsOption(option)
{
    let options = document.getElementsByClassName('friendsOptions');
    for (let i = 0; i < options.length; ++i)
    {
        if (options[i].id !== option && !options[i].hasAttribute('hidden'))
        {
            options[i].setAttribute('hidden', 'true');
        }
        if (options[i].id === option) {
            options[i].removeAttribute('hidden');
        }
    }
}