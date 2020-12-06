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
            
        }
    }

    componentDidMount() {
        
        getNewsList('https://yapi.baidu.com/mock/14577/cy.news.news-server/news/sb/0/10').then(res => {
            console.log(res);
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
                
                <div>
                
                    <NewsItem></NewsItem>
                    <NewsItem></NewsItem>
                    <NewsItem></NewsItem>
                    <NewsItem></NewsItem>
                    <NewsItem></NewsItem>
                
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