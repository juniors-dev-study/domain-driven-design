'use strict';

let USER_API = 'http://localhost:10001'

function register(email, password, name) {
    const options = {
        // credentials: 'include',
        // mode: 'no-cors',
        method: 'POST',
        header: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTP-8'
        },
        body: JSON.stringify({
            name: name,
            email: email,
            password: password
        })
    }

    fetch(USER_API + "/api/v1/sign-up", options)
        .then(response => console.log(response))
}

function register_onclick() {
    register("bearics@gmail")
}
