import instance from '../index'

export function getNewsList(type,startpage,endpage) {
    return instance.get(`/newsServer/news/${type}/${startpage}/${endpage}`)
}

export function getNewscontent(nid) {
    return instance.get(`/newsServer/content/${nid}`)
}

export function getComment(nid,page) {
    return instance.get(`/newsServer/getComments/${nid}/${page}`)
}