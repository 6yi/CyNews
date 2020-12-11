import React, { Component } from 'react'
import { connect } from 'react-redux'
import { saveList } from '../../../redux/action'
import { getNewsList } from '../../../api/News/news'
import Swiper from '../../../components/Swiper/swiper'
import MyScroll from '../../../components/BScroll/better-scroll'
import NewsItem from '../NewsItem/news-item'
import newslist from './news-list.module.css'
import './news-list.less'
class NewsList extends Component {
    constructor() {
        super()
        this.state = {
            img: '',
            type: '',
            newslist: [],
            swiper: [],
            Bscroll: {},
            item: 0,
            positionY: ''
        }
    }

    componentDidMount() {
        
       /*  const newsList = this.props.newsList[this.props.tabs.type]               
        console.log(this.props.newsList);
        if (newsList) {

            this.setState({
                newslist: newsList.data,
                itme: newsList.data.length + 1
            })
        } */
          
            getNewsList(this.props.tabs.type, this.state.item, this.state.item + 13).then(res => {
                this.setState({
                    newslist: res.data.data,
                    item: this.state.item + 14
                }, () => {

                })
            }).catch(err => {
                console.log(err);
            })
       
    }


/* 
    componentWillUnmount() {

      

        this.props.saveList({

            [this.props.tabs.type]: {
                data: this.state.newslist,
                position: this.state.positionY,
                type: this.props.tabs.type
            }


        })
    } */

    getBscroll(Bscroll) {

        this.setState({
            Bscroll
        })
    }

    scrollPosition(position) {
        this.setState({
            positionY: position.y
        })
    }

    pullingUp() {
        this.nextPage()
    }

    nextPage() {
        getNewsList(this.props.tabs.type, this.state.item, this.state.item + 10).then(res => {
            const newsListArray = this.state.newslist
            newsListArray.push(...res.data.data)
            this.setState({
                newslist: newsListArray,
                item: this.state.item + 11
            }, () => {
                this.state.Bscroll.finishPullUp()
            })
        })
    }


    render() {
        return (

            <div className='listbox' style={{ height: 'auto', backgroundColor: '#fff' }}>
                <MyScroll
                    getBscroll={(Bscroll) => this.getBscroll(Bscroll)}
                    pullingUp={() => this.pullingUp()}
                    scrollPosition={(position) => this.scrollPosition(position)}>
                    <div className={newslist.swiper + ' swiperbox'}>
                        <Swiper></Swiper>
                    </div>

                    <div onLoad={() => {
                        this.state.Bscroll.refresh() 
                        /* const newsList = this.props.newsList[this.props.tabs.type]
                        if (newsList) {
                           if(newsList.position ===undefined||""){
                            this.state.Bscroll.scrollTo(0, 0)
                           } else {
                               
                            this.state.Bscroll.scrollTo(0, newsList.position)
                           }
                            
                        } */
                    }}>  {/* 监听加载，刷新高度 */}
                        {this.state.newslist.map((item) => {
                            return <NewsItem key={item.nId} newsitem={item}></NewsItem>
                        })}
                    </div>
                </MyScroll>
            </div>

        )
    }
}

export default connect(
    state => (/* { newsList: state.newsList } */state),
    { /* saveList */ }
)(NewsList)




