import React,{Component} from 'react'
import {Input,Button } from 'antd'
import 'antd/dist/antd.css';
import {Card} from 'antd-mobile'
import './commentInput.less'
import commentInput from './commentinput.module.css'


export default class CommentInput extends Component {
    componentDidMount() {
        document.getElementById('textarea').focus()    /* 一旦渲染就自动聚焦输入框 */
    }   

    render() {
        return(    
        <div>   
        <div className={commentInput.inputbox+ ' inputbox'}>
        <Card>
            <textarea id='textarea' onBlur={this.props.inputBlur} placeholder='为大家提供有帮助的言论吧'/> {/* 失焦调用父函数传递的方法修改显示值 */}
            <div>
                <img src="" alt=""/>
                <Button className={commentInput.submitbutton} type='primary' shape='round'  size='small'>发布</Button>
            </div>
        </Card>
        
        </div>
            <div className={commentInput.masklayer}></div>
        </div> 
    )
    }
}
