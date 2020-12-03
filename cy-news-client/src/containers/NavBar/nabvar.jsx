import React,{Component} from 'react'
import {Icon,SearchBar} from 'antd-mobile';
import MyDrawer from '../Drawer/drawer'
import './Nav.less'
export default class Nav extends Component{
    

    render() {
    return (
        
        <div className='navbar'>
            <div className='top-nav'>
            <span className='left' onClick={this.props.iconClick}>
                <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605717095531&di=8ea976b885c0d7783c402b2053f4ba41&imgtype=0&src=http%3A%2F%2Fimgcdn1.gzhtedu.cn%2Fimages%2Fxiaohui%2F20161124034151-27.jpg" alt=""/>
                
            </span>
             <SearchBar placeholder="自动获取光标" /* ref={ref => this.autoFocusInst = ref} */ />
               
            <span className='right'></span>
            </div>
            
        </div>
       
        
    )
    }
}