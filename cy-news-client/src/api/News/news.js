import instance from '../index'

export function getNewsList(url) {
    return instance.get(url)
}

export function getNewscontent(url) {
    return instance.get(url)
}