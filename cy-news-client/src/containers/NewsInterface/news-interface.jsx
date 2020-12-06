import React,{Component} from 'react'
import Nav from '../NavBar/nabvar'
import Tabs from './Tabs/Tab'
import MyDrawer from '../Drawer/drawer'
import NewsContent from '../NewsContent/news-content'
import {HashRouter,Switch,Route} from 'react-router-dom'

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