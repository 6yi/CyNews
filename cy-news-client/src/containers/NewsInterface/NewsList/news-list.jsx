import React, { Component } from 'react'
import instance from '../../../api/index'
import Swiper from '../../../components/Swiper/swiper'
import NewsItem from '../NewsItem/news-item'

import newslist from './news-list.module.css'
export default class NewsList extends Component {
    constructor() {
        super()
        this.state= {
            img:'',
            type:''
        }
    }

    componentDidMount() {
        console.log(this.props.tab);
    }
    componentDidUpdate() {
        console.log('111111111');
    }
        

    
    render() {
        return (
            <div  style={{height: 'auto', backgroundColor: '#fff' }}>
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