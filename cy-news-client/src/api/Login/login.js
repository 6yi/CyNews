import instance from '../index'

export function toLogin(data) {
    return  instance.post('/userServer/login',data) 
}