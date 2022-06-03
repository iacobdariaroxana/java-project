function logout(){
    fetch(`http://localhost:2022/myLogout`, {
        method: "GET",
        mode: "cors"
    }).then(res => {
        if(res.status == 200){
            toPage('/');
        }
        console.log(res.status);
    }).catch(err => console.log(err));
}