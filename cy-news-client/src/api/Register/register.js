import instance from '../index'

export function toRegister(url,data) {
    return instance.post(url,data)
}