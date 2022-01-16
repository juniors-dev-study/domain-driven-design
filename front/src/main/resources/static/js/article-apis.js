'use strict';


function getTest() {
    const options = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        }
    }

    return fetch("http://local-front.ddd.sns.com:10100/article-api/v1/test", options)
}
