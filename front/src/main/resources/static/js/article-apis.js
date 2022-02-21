'use strict';

function getFeeds() {
    const options = {
        method: 'GET',
    }

    return fetch("http://local-front.ddd.sns.com:10100/article-api/v1/feeds", options)
}


function getMyArticles() {
    const options = {
        method: 'GET',
    }

    return fetch("http://local-front.ddd.sns.com:10100/article-api/v1/articles", options)
}

function getMyArticle(articleId) {
    return fetch(`http://local-front.ddd.sns.com:10100/article-api/v1/articles/` + articleId, options)
}

function getArticleScopes() {
    const options = {
        method: 'GET',
    }

    return fetch("http://local-front.ddd.sns.com:10100/article-api/v1/articles/scopes", options)
}

function writeArticle(imageUrls, body, scope) {
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            body: body,
            imageUrls: imageUrls,
            scope: scope,
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

function addComment(articleId, contents) {
    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            rootType: 'ARTICLE',
            rootId: articleId,
            contents: contents
        })
    }
    return fetch('http://local-front.ddd.sns.com:10100/article-api/v1/comments', options)
}
