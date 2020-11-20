import React, { Component } from 'react'
import { List, InputItem,Icon,Button } from 'antd-mobile';
import register from './register.module.css'
import '../../assets/css/base.css'
export default class Login extends Component {
    constructor() {
        super()

        this.state = {
            user: {
                u_username: '',
                u_password: '',
                u_affirmpassword: '',
            }
        }
    }
    inputOnChange(e,value) {
        /* this.setState({
            [field]:value
        }) */
        console.log(e,value);
    }

    render() {
        return (
            <div className={register.background}>
               <div className={register.cancleicon}>
                <Icon type='cross' size='lg'/>
               </div>
               <div className={register.Formdata}>
                <span className={register.logintitle}>注册</span>
                <List className={register.list}>
                    <List.Item className={register.listItem}>
                <input type="text" placeholder='请输入账号' className={register.input}  value={this.state.user.u_username} onChange={(e) => {this.inputOnChange(this.user.u_username,e)}}/>
                    </List.Item>
                    <List.Item className='listItem'>
                    <input type="text" placeholder='请输入密码' className={register.input}  value={this.state.user.u_password}/>
                    </List.Item>
                    <List.Item>
                    <input type="text" placeholder='请再次输入密码' className={register.input}  value={this.state.user.u_affirmpassword}/>
                    </List.Item>
                    <Button className={register.button}>登录</Button>
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