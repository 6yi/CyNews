import {SAVE_USER,ERROR_MSG,SAVE_BSCROLL} from './action-types'
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



export default combineReducers({
    user
    
})