'use strict';

let USER_API = 'http://localhost:10001'

function register(email, password, name) {
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name,
            email: email,
            password: password
        })
    }
    return fetch(USER_API + "/api/v1/sign-up", options)
}

function getProfile() {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }
    return fetch("http://local-front.ddd.sns.com:10100/api/profile", options)
}
