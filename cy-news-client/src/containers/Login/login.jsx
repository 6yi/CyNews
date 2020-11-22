import React, { Component } from 'react'
import { List, InputItem, Icon, Button } from 'antd-mobile';
import login from './login.module.css'
import { toLogin } from '../../api/Login/login'

export default class Login extends Component {
    constructor() {
        super()

        this.state = {
            user: {
                u_username: '',
                u_password: '',

            },
            buttonActive: '0'
        }
    }

    componentDidMount() {   //获取用户设备高度
        var _box = document.getElementsByClassName('loginbox')[0]
        _box.style.height = _box.clientHeight + 'px'

    }

    loginClick() {
        toLogin('/login').then(res => {
            console.log(res);
        })
    }

    render() {
        return (
            <div className={login.background + ' loginbox'}>
                <div onClick={() => { this.props.history.go(-1) }} className={login.cancleicon}>
                    <Icon type='cross' size='lg' />
                </div>
                <div className={login.Formdata}>
                    <span className={login.logintitle}>账号密码登录</span>
                    <List className={login.list}>
                        <List.Item className={login.listItem}>
                            <input type="text" placeholder='请输入邮箱' className={login.input + ' l-input'} />
                        </List.Item>
                        <List.Item className={login.listItem}>
                            <input type="text" placeholder='请输入密码' className={login.input} />
                        </List.Item>
                        <Button className={login.button} disabled={this.state.buttonActive === '0'} onClick={() => { this.loginClick() }}>登录</Button>
                        <div className={login.toregister} onClick={() => { this.props.history.push('/register') }}>去注册</div>
                    </List>
                </div>
                <div className={login.footer}>

                    <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605717095531&di=8ea976b885c0d7783c402b2053f4ba41&imgtype=0&src=http%3A%2F%2Fimgcdn1.gzhtedu.cn%2Fimages%2Fxiaohui%2F20161124034151-27.jpg" alt="" />
                    <p>城院新闻</p>

                </div>
            </div>
        )
    }
}