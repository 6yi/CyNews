import instance from '../index'

export function toLogin(url,data) {
    return  instance.post(url,data) 
}