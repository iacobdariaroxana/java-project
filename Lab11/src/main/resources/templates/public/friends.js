let myJwt = parseJwt(window.localStorage.getItem("token"));
function getFriends() {
    let myFriendsList = document.getElementById('myFriendsList');
    myFriendsList.removeAttribute('hidden');
    myFriendsList.replaceChildren();
    let myJwt = parseJwt(window.localStorage.getItem("token"));
    fetch(`http://localhost:2022/relationships/${myJwt.id}`, {
        method: "GET",
        mode: "cors"
    }).then(res => res.json()).then(data => {
        // createElements(data, myFriendsList);
        console.log(data);
        createElements(data, myFriendsList);
    }).catch(err => console.log(err));
}

function addFriend(){
    let responseAddFriend = document.getElementById("response-insert-friend");
    let addFriend = document.getElementById('addFriend');
    let friendName = document.getElementById('friendName').value;
    fetch(`http://localhost:2022/relationships/insert?user1=${myJwt.id}&user2=${friendName}`, {
        method: "POST",
        mode: "cors"
    }).then(async res =>{
        responseAddFriend.innerHTML = await res.text();
        getFriends();
    }).catch(err => console.log(err));
}

function getVip(){
    let vip = document.getElementById('vip');
    vip.replaceChildren();
    fetch(`http://localhost:2022/relationships/vip?k=3`, {
        method: "GET",
        mode: "cors"
    }).then(res => res.json()).then(data => {
        console.log(data);
        createElements(data, vip);
    }).catch(err => console.log(err));
}

function createElements(data, container){
    for(let i = 0; i < data.length; i++){
        let friend = document.createElement('li');
        friend.classList.add("item");
        let name = document.createElement('h1');
        name.innerHTML = data[i];
        name.classList.add('headline');
        friend.appendChild(name);
        container.appendChild(friend);
    }
}