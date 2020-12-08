
import {SAVE_USER,SAVE_LIST} from './action-types'

export const saveUser = (user) => ({type: SAVE_USER,data:user})

export const saveList = (newslist) => ({type: SAVE_LIST,data:newslist})

/* newslist: {
    type: 'index',
    data: []
} */