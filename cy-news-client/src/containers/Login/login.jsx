import React, { Component } from 'react'
import { List, InputItem,Icon,Button } from 'antd-mobile';
import login from './login.module.css'

export default class Login extends Component {
    constructor() {
        super()

        this.state = {
            user: {
                u_username: '',
                u_password: '',
                
            }
        }
    }

    componentDidMount() {   //获取用户设备高度
        var _box = document.getElementsByClassName('loginbox')[0]
        _box.style.height = _box.clientHeight +'px'
    }

    render() {
        return (
            <div className= {login.background+ ' loginbox'}>
               <div className= {login.cancleicon}>
                <Icon type='cross' size='lg'/>
               </div>
               <div className={login.Formdata}>
                <span className={login.logintitle}>账号密码登录</span>
                <List className={login.list}>
                    <List.Item className={login.listItem}>
                    <input type="text" placeholder='请输入账号' className={login.input + ' l-input'}/>
                    </List.Item>
                    <List.Item className={login.listItem}>
                    <input type="text" placeholder='请输入密码' className={login.input}/>
                    </List.Item>
                    <Button className={login.button}>登录</Button>
                </List>
               </div>
               <div className={login.footer}>
                  
                    <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605717095531&di=8ea976b885c0d7783c402b2053f4ba41&imgtype=0&src=http%3A%2F%2Fimgcdn1.gzhtedu.cn%2Fimages%2Fxiaohui%2F20161124034151-27.jpg" alt=""/>
                   <p>城院新闻</p>
                
               </div>
            </div>
        )
    }
}