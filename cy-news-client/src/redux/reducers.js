import {SAVE_USER,ERROR_MSG} from './action-types'

var initUser = {
    userName: '',
    headerIcon: '',
    }

function user(state=initUser,action) {
    console.log(action);
    switch (action.type) {
        case SAVE_USER:
            return action.data
        default:
            return state
    }
    
}

export default user