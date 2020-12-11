import {SAVE_USER,SAVE_LIST} from './action-types'
import {combineReducers} from "redux"


function user(state=null,action) {
    
    switch (action.type) {
        case SAVE_USER:
            return action.data
        default:
            return state
    }
    
}

/* function newsList(state={},action) {
    switch(action.type) {
        
        case SAVE_LIST:
            console.log(action.data);
            return {...state,...(action.data)}
        default:
            return state
    }
}
 */

export default combineReducers({
    user,
/*     newsList */
})