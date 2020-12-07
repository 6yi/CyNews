import instance from '../index'

export function toRegister(data) {
    return instance.post('/userServer/register',data)
}