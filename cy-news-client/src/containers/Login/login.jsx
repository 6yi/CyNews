import React, { Component } from 'react'
import { List, InputItem,Icon,Button } from 'antd-mobile';
import './login.less'
export default class Login extends Component {
    constructor() {
        super()

        this.state = {
            user: {
                u_username: '',
                u_password: '',
                u_nickname: '',
            }
        }
    }

    render() {
        return (
            <div className='background'>
               <div className='cancleicon'>
                <Icon type='cross' size='lg'/>
               </div>
               <div className='Formdata'>
                <span className='logintitle'>账号密码登录</span>
                <List className='list'>
                    <List.Item className='listItem'>
                    <input type="text" placeholder='请输入账号' className='input'/>
                    </List.Item>
                    <List.Item className='listItem'>
                    <input type="text" placeholder='请输入密码' className='input'/>
                    </List.Item>
                    <Button className="button">登录</Button>
                </List>
               </div>
               <div className="footer">
                  
                    <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605717095531&di=8ea976b885c0d7783c402b2053f4ba41&imgtype=0&src=http%3A%2F%2Fimgcdn1.gzhtedu.cn%2Fimages%2Fxiaohui%2F20161124034151-27.jpg" alt=""/>
                   <p>城院新闻</p>
                
               </div>
            </div>
        )
    }
}