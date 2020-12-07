import React, { Component } from 'react'
import {connect} from 'react-redux'
import BScroll from 'better-scroll'
import {getNewsList} from '../../../api/News/news'
import Swiper from '../../../components/Swiper/swiper'
import MyScroll from '../../../components/BScroll/better-scroll'
import NewsItem from '../NewsItem/news-item'
import newslist from './news-list.module.css'
import './news-list.less'
class NewsList extends Component {
    constructor() {
        super()
        this.state= {
            img:'',
            type:'',
            newslist: [],
            BScroll:''    
        }
    }

    componentDidMount() {
        
        const wrapper = document.querySelector('.wrapper')

        const scroll = new BScroll(wrapper, {
            
            click: true,  // better-scroll 默认会阻止浏览器的原生 click 事件
            scrollY: true, //开启竖向滚动
            pullDownRefresh:{
                threshold: 50,
                stop: 20
            },
            
            tap: true,
        })
        this.setState({         //将s实例后的scroll保存到state中
            BScroll:scroll
        })
        
        getNewsList(this.props.tabs.type,0,10).then(res => {
            this.setState({
                newslist:res.data.data
            },() => {
                console.log(this.state.newslist);
            })
        }).catch(err => {
            console.log(err);
        })
    }
   
        
    

    render() {
        return (
            
            <div className='listbox'  style={{height: 'auto', backgroundColor: '#fff' }}>
                <MyScroll>
                <div className={newslist.swiper+ ' swiperbox'}>
                <Swiper></Swiper>
                </div>
                
                <div onLoad = {() => {this.state.BScroll.refresh()}}>  {/* 监听加载，刷新高度 */}
                {this.state.newslist.map((item) => {
                    return  <NewsItem newsitem={item}></NewsItem>
                })}
                </div>
                </MyScroll>
            </div>
            
        )
    }
}

export default connect(
    state => ({state}),
    {}
)(NewsList)