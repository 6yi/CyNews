import instance from '../index'

export function getNewsList(type,startpage,endpage) {
    return instance.get(`/newsServer/news/${type}/${startpage}/${endpage}`)
}

export function getNewscontent(url) {
    return instance.get(url)
}