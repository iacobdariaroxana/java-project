function inbox()
{
    fetch("http://localhost:2022/messages/userinbox/", {
        method: "GET",
        mode: "cors",
        headers: {
            "Content-Type": "application/json",
        }
    }).then((res) => {

    }).else(err )
}