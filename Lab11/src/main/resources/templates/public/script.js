function toPage(page) {
    window.location = page;
}

function setTab(tabName)
{
    let tabs = document.getElementsByClassName('tabs');
    for (let i = 0; i < tabs.length; ++i)
    {
        switch (tabs[i].id) {
            case 'inbox': {
                inbox();
                console.log()
                document.getElementById('outboxTable').querySelector('tbody').innerHTML = "";
                break;
            }
            case 'sent': {
                outbox();
                document.getElementById('inboxTable').querySelector('tbody').innerHTML = "";
                break;
            }
        }
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
    if(option == 'myFriends'){
        getFriends();
    } else if(option == 'popularUsers'){
        getVip();
    }
}

const parseJwt = (token) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
};
