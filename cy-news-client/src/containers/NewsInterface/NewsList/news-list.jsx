import React, { Component } from 'react'
import instance from '../../../api/index'
import Swiper from '../../../components/Swiper/swiper'
import NewsItem from '../NewsItem/news-item'

import newslist from './news-list.module.css'
export default class NewsList extends Component {
    constructor() {
        super()
        this.state= {
            img:''
        }
    }

    componentDidMount() {
        
    }
    
    render() {
        return (
            <div  style={{height: '800px', backgroundColor: '#fff' }}>
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
            </div>
        )
    }
}