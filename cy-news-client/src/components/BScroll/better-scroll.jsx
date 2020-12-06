import React,{Component} from 'react'
import BScroll from 'better-scroll'
import {saveBScroll} from '../../redux/action'
import {connect} from 'react-redux'
export default class MyScroll extends Component {

    constructor() {
        super()
        this.state = {
            BScroll:{}
        }
    }

    componentDidMount() {
        const wrapper = document.querySelector('.wrapper')

        const scroll = new BScroll(wrapper, {
            
            click: true,  // better-scroll 默认会阻止浏览器的原生 click 事件
            scrollY: true, //开启竖向滚动
            
            
            tap: true,
        })
        this.setState({         //将s实例后的scroll保存到state中
            BScroll:scroll
        })}

   

    render() {
        return(
            <div className='wrapper'>
                <div className='content'>
                    {this.props.children}
                </div>
            </div>
        )
    }
}

