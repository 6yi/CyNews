import React,{Component} from 'react'
import Nav from '../../components/NavBar/nav'
import CommentInput from './CommentInput/comment-input'
import CommentCard from './CommentCard/commentcard'
import MyScroll from '../../components/BScroll/better-scroll'
import newscontent from './news-content.module.css'
import NewsTabBar from './TabBar/tabbar'
import {getNewscontent,getComment} from '../../api/News/news'
import './news-content.less'

export default class NewsContent extends Component {

    constructor() {
        super()
        this.state= {
            showInput: false,
            Bscroll:{},
            newsContent:{
                news:{},
                newsMessage:{
                    mlike:'',
                    mwatch:''
                }
            },
            commentPage:0,
            commentList:[]
        }
    }
    componentDidMount() {
        
        getNewscontent(this.props.match.params.newsid).then(res => {
            console.log(res.data.data);
            this.setState({
                newsContent:res.data.data
            })
           
        })
        
    }

    componentDidUpdate() {
       
        this.state.Bscroll.refresh()
        
    }

    inputClick() {
        this.setState({
            showInput: true
        })
    }

    getBscroll(Bscroll) {
        
        this.setState({
            Bscroll
    })}

    inputBlur() {
        this.setState({
            showInput:false
        })
    }

    nextPage() {
        getComment(this.props.match.params.newsid,this.state.commentPage).then(res => {
            console.log(res);
            this.setState({
                commentList:res.data.data
            })
            this.state.Bscroll.finishPullUp()
        })
    }

    pullingUp() {
        this.nextPage()
    }

    

    render() {
        var i = [1,2,3,4,5,6,7]
        return(
            <div className='contentbox'>
                <Nav history={this.props.history}></Nav>
                
                <MyScroll 
                getBscroll={(Bscroll) => this.getBscroll(Bscroll)}
                pullingUp={() => this.pullingUp()}>
                <div className={newscontent.newsbody}>
                    <span className={newscontent.title}>xxxx</span>
                    <div className={newscontent.authorbox + ' authorbox'}>
                        <img src="" alt=""/>
                        <div className={newscontent.authorinfo}>
                        <span className={newscontent.authorname}>ss</span>
                        <span className={newscontent.releasetime}>ss</span>
                        </div>
                    </div>

                    <div className={newscontent.newscontent +' newscontent'} 
                    dangerouslySetInnerHTML={{__html: this.state.newsContent.news.ncontent}} 
                    ></div>
                    <div className={newscontent.comment}>
                    
                    {this.state.commentList.map((commentInfo) => {
                      return  <CommentCard commentInfo ={commentInfo}></CommentCard>
                    })}
                    </div>
                </div>
                
                </MyScroll>

                {this.state.showInput===true?<CommentInput inputBlur={() => this.inputBlur()}></CommentInput>:null}
               
                <NewsTabBar inputClick={() => {this.inputClick()}} newsMessage={this.state.newsContent.newsMessage}></NewsTabBar>
            </div>
        )
    }
}