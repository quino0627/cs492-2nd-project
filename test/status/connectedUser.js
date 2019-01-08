var hashMap = require('hashmap');
var users = new hashMap();

function login(user_id) {
    if(users.search(user_id)!=null){
        users.set(user_id, 0);
        console.log("login success")
    } else
        console.log("already connected")
    
}

function logout(user_id) {
    users.remove(user_id);
}



function lastestPage(user_id) {
    users.get(user_id) = 0;

    return 0;
}
function nextIndex(user_id) {
    users.get(user_id)++;

    return users.get(user_id);
}

module.exports = currentUsers;