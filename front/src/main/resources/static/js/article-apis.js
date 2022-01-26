'use strict';


function getMyArticles() {
    const options = {
        method: 'GET',
    }

    return fetch("http://local-front.ddd.sns.com:10100/article-api/v1/articles", options)
}

function writeArticle(imageUrls, body) {
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            body: body,
            imageUrls: imageUrls,
        })
    }

    return fetch("http://local-front.ddd.sns.com:10100/article-api/v1/articles", options)
}
