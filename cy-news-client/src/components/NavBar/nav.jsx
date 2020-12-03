import React from 'react'
import {NavBar,Icon} from 'antd-mobile'
import './nav.css'
export default function Nav(props) {
    
    return(
        <NavBar
      mode="light"
      leftContent= {<Icon type="left" onClick={() => {console.log(props);;props.history.goBack()}}></Icon>}
      rightContent={[
        <Icon key="0" type="search"  style={{ marginRight: '16px' }} />,
        <Icon key="1" type="ellipsis" />,
      ]}
    >{<img src={props.src}></img>}</NavBar>
  
    )
}