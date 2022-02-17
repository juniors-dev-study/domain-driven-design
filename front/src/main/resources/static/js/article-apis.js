'use strict';


function getMyArticles() {
    const options = {
        method: 'GET',
    }

    return fetch("http://local-front.ddd.sns.com:10100/article-api/v1/articles", options)
}

function getMyArticle(articleId) {
    const options = {
        method: 'GET',
    }

    return fetch(`http://local-front.ddd.sns.com:10100/article-api/v1/articles/` + articleId, options)
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

function modifyArticle(articleId, imageUrls, body) {
    const options = {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            body: body,
            imageUrls: imageUrls,
        })
    }
    return fetch('http://local-front.ddd.sns.com:10100/article-api/v1/articles/' + articleId, options)
}

function deleteArticle(articleId) {
    const options = {
        method: 'DELETE'
    }

    return fetch('http://local-front.ddd.sns.com:10100/article-api/v1/articles/id/' + articleId, options)
}
