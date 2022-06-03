function outbox() {
    let user = parseJwt(window.localStorage.getItem("token"));
    console.log(user);
    fetch("http://localhost:2022/messages/useroutbox/" + user.id, {
        method: "GET",
        mode: "cors",
        headers: {
            "Content-Type": "application/json",
        }
    }).then(async res => {
        let messages = (await res.text()).split("###");
        console.log("OUTBOX: " + messages);
        for (let i = 0; i < messages.length; ++i) {
            const newRow = document.createElement("tr");
            const newColumn1 = document.createElement("td");
            newColumn1.innerText = JSON.parse(messages[i]).to;
            const newColumn2 = document.createElement("td");
            newColumn2.innerText = JSON.parse(messages[i]).message;
            newRow.append(newColumn1);
            newRow.append(newColumn2);
            document.getElementById("outboxTable").querySelector('tbody').appendChild(newRow);
        }
        document.getElementById("outboxTable")
            .querySelector('thead').querySelector('tr')
            .querySelector('th').innerText = "To";
    }).catch(err => {
        console.log(err);
    })
}