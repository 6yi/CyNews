import React,{Component} from 'react'
import Nav from '../../components/NavBar/Nav'
import Tabs from './Tabs/tab'
export default class NewInterface extends Component {
    
    
    render() {
        return(
            <div>
                <Nav></Nav>
                <Tabs></Tabs>
            </div>
        )
    }
}