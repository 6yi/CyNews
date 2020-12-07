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
            Bscroll:{},
            item:0      
        }
    }

    componentDidMount() {
       
        
        getNewsList(this.props.tabs.type,this.state.item,this.state.item+10).then(res => {
           
            this.setState({
                newslist: res.data.data,
                item:this.state.item+10
            },() => {
                
            })
        }).catch(err => {
            console.log(err);
        })
    }
   
    getBscroll(Bscroll) {
        
        this.setState({
            Bscroll
    },() => {console.log(this.state.Bscroll);})}
    
    pullingUp() {
       this.nextPage()
    }

    nextPage() {
        getNewsList(this.props.tabs.type,this.state.item,this.state.item+10).then(res => {
            const newsListArray = this.state.newslist
            newsListArray.push(...res.data.data)
            this.setState({
                newslist:newsListArray,
                item:this.state.item+10
            },() => {
                this.state.Bscroll.finishPullUp()
            })
        })
    }


    render() {
        return (
            
            <div className='listbox'  style={{height: 'auto', backgroundColor: '#fff' }}>
                <MyScroll 
                getBscroll={(Bscroll) => this.getBscroll(Bscroll)} 
                pullingUp={() =>this.pullingUp()}>
                <div className={newslist.swiper+ ' swiperbox'}>
                <Swiper></Swiper>
                </div>
                
                <div onLoad = {() => {this.state.Bscroll.refresh()}}>  {/* 监听加载，刷新高度 */}
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