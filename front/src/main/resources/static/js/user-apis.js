'use strict';

const USER_API = 'http://localhost:10001'

function register(email, password, name) {
    const options = {
        method: 'POST',
        body: JSON.stringify({
            name: name,
            email: email,
            password: password
        })
    }
    return fetch("http://local-front.ddd.sns.com:10100/user-api/v1/sign-up", options)
}

function getUser() {
    const options = {
        method: 'GET',
    }
    return fetch("http://local-front.ddd.sns.com:10100/user-api/v1/users", options)
}

function getProfile() {
    const options = {
        method: 'GET',
    }
    return fetch("http://local-front.ddd.sns.com:10100/user-api/v1/profiles", options)
}

function updateProfile(nickName, iconImageUrl, intro, hobbies) {
    const options = {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            'nickName': nickName,
            'iconImageUrl': iconImageUrl,
            'intro': intro,
            'hobbies': hobbies
        })
    }
    return fetch("http://local-front.ddd.sns.com:10100/user-api/v1/profiles", options)
}
