import React, { Component } from 'react'
import { Drawer, List, NavBar, Icon, Flex, WingBlank, Card} from 'antd-mobile'
import drawer from './drawer.module.css'
export default class MyDrawer extends Component {

  componentDidUpdate() {
    if (document.getElementsByClassName('am-drawer-open')[0]) {     //遮罩层出现后禁止滑动
      document.getElementsByTagName('body')[0].style.overflow = 'hidden'
      var Height = document.getElementsByClassName('tabcontent')[0].offsetHeight
      console.log(Height);
      /*  document.getElementsByClassName('am-drawer-overlay')[0].style.height = Height + 'px'
       console.log(document.getElementsByClassName('am-drawer-overlay')[0].style.height); */
    } else {
      document.getElementsByTagName('body')[0].style.overflow = 'scroll'
    }


  }

  render() {
    const sidebar = (

      <div className={drawer.box}>
        <Flex className={drawer.avatarbxo}>

          <WingBlank> <img className={drawer.avatar} src="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg" alt="" /></WingBlank>
          <span>here is username</span>
          
        </Flex>
        
      </div>

    );

    return (
      <Drawer
        className="my-drawer"
        style={{ minHeight: document.documentElement.clientHeight }}


        sidebar={sidebar}
        open={this.props.open}
        onOpenChange={this.props.iconClick}
      >

      </Drawer>
    )
  }
}
