import URLS from './settings'

export function register(email, password, name) {
    const options = {
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

    fetch(URLS.USER_API, options)
        .then(response => console.log(response))
}
