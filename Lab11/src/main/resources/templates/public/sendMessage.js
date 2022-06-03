function sendMessage(){
    let messagge = document.getElementById("messageText");
    console.log(messagge.value)
    // fetch("http://localhost:2022/messagges", {
    //     method: "POST",
    //     mode: "cors",
    //     headers: {
    //         "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify({
    //             "message" : messagge.value
    //     }
    //     )
    // }).then(res => console.log(res)).catch(err => console.log(err));
}