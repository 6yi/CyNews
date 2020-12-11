import React,{Component} from 'react'
import {Icon,SearchBar} from 'antd-mobile';
import {connect} from 'react-redux'
import MyDrawer from '../Drawer/drawer'
import Spider from '../../assets/img/Spider-man.png'
import NOLogin from '../../assets/img/not-log-in.png'
import './Nav.less'
import { withRouter } from 'react-router-dom';
class Nav extends Component{
    
    headimg = () => {
        if(this.props.user) {
            if(this.props.user.uAvatar) {
                return <img src={this.props.user.uAvatar} style={{width:'45%'}} alt="" onClick={this.props.iconClick}/>
            } else {
                return <img src={Spider} style={{width:'61%'}} onClick={this.props.iconClick}/>
            }
        } else {
            return(
                <div style={{textAlign:'center'}} onClick={() => {this.props.history.push('/login')}}>
                    <img style={{width:'45%'}} src={NOLogin} alt=""/>
                </div>
            )
               
        }
    }

    render() {
    return (
        
        <div className='navbar'>
            <div className='top-nav'>
            <span className='left'>
                {this.headimg()}
               
            </span>
             <SearchBar placeholder="自动获取光标" /* ref={ref => this.autoFocusInst = ref} */ />
               
            <span className='right'></span>
            </div>
            
        </div>
       
        
    )
    }
}


export default withRouter(connect(
    state => (state),
    { /* saveList */ }
)(Nav))
