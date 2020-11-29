import React,{Component} from 'react'
import {Drawer, List, NavBar, Icon} from 'antd-mobile'

export default class MyDrawer extends Component {
    
    componentDidUpdate() {
       if(document.getElementsByClassName('am-drawer-open')[0]) {     //遮罩层出现后禁止滑动
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
        const sidebar = (<List>
            {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15].map((i, index) => {
              if (index === 0) {
                return (<List.Item key={index}
                  thumb="https://zos.alipayobjects.com/rmsportal/eOZidTabPoEbPeU.png"
                  multipleLine
                >Category</List.Item>);
              }
              return (<List.Item key={index}
                thumb="https://zos.alipayobjects.com/rmsportal/eOZidTabPoEbPeU.png"
              >Category{index}</List.Item>);
            })}
          </List>);

        return(
            <Drawer
            className="my-drawer"
            style={{ minHeight: document.documentElement.clientHeight}}
            

            sidebar={sidebar}
            open={this.props.open}
            onOpenChange={this.props.iconClick}
          >
           
          </Drawer> 
        )
    }
}
