import {SAVE_USER,SAVE_LIST} from './action-types'
import {combineReducers} from "redux"
var initUser = {
    userName: '',
    headerIcon: '',
    }

function user(state=initUser,action) {
    
    switch (action.type) {
        case SAVE_USER:
            return action.data
        default:
            return state
    }
    
}

function newsList(state={},action) {
    switch(action.type) {
        case SAVE_LIST:
            console.log(action.data);
            return {state,[action.type.type]:action.data}
        default:
            return state
    }
}


export default combineReducers({
    user,
    newsList
})