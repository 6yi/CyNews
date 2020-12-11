import React, { Component } from 'react'
import BScroll from 'better-scroll'
import { saveBScroll } from '../../redux/action'
import { connect } from 'react-redux'


// function debounce(fn, ms = 100) {        /* 防抖函数封装 */
//     let timeoutId
//     return function () {
//       clearTimeout(timeoutId)
//       timeoutId = setTimeout(() => {
//           /* console.log(this,arguments); */
//         fn.apply(this, arguments)
//       }, ms)
//     }
// }

export default class MyScroll extends Component {

    constructor(props) {
        super(props)
        this.state = {
            sccrollPosition: () => {},
            BScroll: {}
        }
        // this.scrolling = debounce(this.scrolling,100)    /* ！！！react要在此绑定防抖，不然回调函数不执行 */
    }



    componentDidMount() {
        
        const wrapper = document.querySelector('.wrapper')

        const scroll = new BScroll(wrapper, {

            click: true,  // better-scroll 默认会阻止浏览器的原生 click 事件
            scrollY: true, //开启竖向滚动
            pullUpLoad: {
                threshold: 100, // 当下拉到超过顶部 50px 时，触发 pullingDown 事件
                stop: 50 // 刷新数据的过程中，回弹停留在距离顶部还有 20px 的位置
            },
            
            bounce:true,
            tap: true,
            eventPassthrough:'horizontal',      /* 保留原生横向滚动 */
        })
        this.setState({
            BScroll: scroll,
            sccrollPosition: this.props.sccrollPosition
        }, () => {

            this.state.BScroll.on('pullingUp',this.props.pullingUp)
            // this.state.BScroll.on('scroll',(position) => this.scrolling(position))
            this.props.getBscroll(scroll)   //向父组件传入betterscroll实例

        })
        
    }

   /*  scrolling = (position) => {
        if(this.props.scrollPosition)
        this.props.scrollPosition(position)
    } */

    render() {
        return (
            <div className='wrapper'>
                <div className='content'>
                    {this.props.children}
                </div>
            </div>
        )
    }
}

