import React, { Component } from 'react'
import { List, InputItem,Icon,Button, Toast } from 'antd-mobile';
import register from './register.module.css'
import '../../assets/css/base.css'
import {toRegister} from '../../api/Register/register'
export default class Login extends Component {
    constructor() {
        super()
        const buttonRef = React.createRef()
        this.state = {
            user: {
                userName:'',
                nickName:'',
                email: '',
                passWord: '',
               /*  u_affirmpassword: '', */
            },
            buttonActive: '1'
        }
    }
    inputOnChange(name,value) {
        this.setState(
            {
            user: Object.assign({}, this.state.user, { [name]: value })
            }
        )
        
    }

    registerClick() {
        console.log(this.state.user);
        toRegister('/cy.news.user-server/register',this.state.user).then((res) => {
            Toast.success('注册成功，请查看邮箱以激活',2)
            setTimeout(() => {
                this.props.history.push('/login')
            },2000)
        })
    }

    componentDidMount() {   //获取用户设备高度
        var _box = document.getElementsByClassName('registerbox')[0]
        _box.style.height = _box.clientHeight +'px'
    }

    render() {
        return (
            <div className={register.background + ' registerbox'}>
               <div onClick={() => {this.props.history.go(-1)}} className={register.cancleicon}>
                <Icon type='cross' size='lg'/>
               </div>
               <div className={register.Formdata}>
                <span className={register.logintitle}>注册</span>
                <List className={register.list}>
                    <List.Item className={register.listItem}>
                <input type="text" placeholder='请输入邮箱' className={register.input}    onChange={(e) => {this.inputOnChange('email',e.target.value)}}/>
                    </List.Item>
                    <List.Item className={register.listItem}>
                    <input type="text" placeholder='请输入密码' className={register.input}   onChange={(e) => {this.inputOnChange('passWord',e.target.value)}}/>
                    </List.Item>
                    <List.Item>
                    <input type="text" placeholder='请输入昵称' className={register.input}   onChange={(e) => {this.inputOnChange('nickName',e.target.value)}}/>
                    </List.Item>
                    <List.Item>
                    <input type="text" placeholder='请输入用户名' className={register.input}   onChange={(e) => {this.inputOnChange('userName',e.target.value)}}/>
                    </List.Item>
                    <Button ref={this.buttonRef} className={register.button} disabled={this.state.buttonActive === '0'} onClick={() => {this.registerClick()}}>登录</Button>
                </List>
               </div>
               <div className={register.footer}>
                  
                    <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1605717095531&di=8ea976b885c0d7783c402b2053f4ba41&imgtype=0&src=http%3A%2F%2Fimgcdn1.gzhtedu.cn%2Fimages%2Fxiaohui%2F20161124034151-27.jpg" alt=""/>
                   <p>城院新闻</p>


                   <p className={register.form}>注册即视为同意《城院新闻服务及隐私条款》</p>
                
               </div>
            </div>
        )
    }
}