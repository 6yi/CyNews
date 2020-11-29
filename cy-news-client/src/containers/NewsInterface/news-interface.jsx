import React,{Component} from 'react'
import Nav from '../NavBar/Nav'
import Tabs from './Tabs/tab'
import MyDrawer from '../Drawer/drawer'
export default class NewInterface extends Component {
    
    state= {
        open:false
    }


    iconClick() {
        
        this.setState({
            open: !this.state.open
        })
    }
    
    render() {
        return(
            <div className='newsinterface'>
                <Nav iconClick={() => {this.iconClick()}}></Nav>
                <MyDrawer open={this.state.open} iconClick={() => {this.iconClick()}}></MyDrawer>
                <Tabs></Tabs>
            </div>
        )
    }
}