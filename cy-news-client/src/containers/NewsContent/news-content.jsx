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
                content:'',
                newsMessage:{
                    mlike:'',
                    mwatch:''
                }
            },
            commentPage:0
        }
    }
    componentDidMount() {
        console.log(document.getElementsByClassName('newscontent')[0].offsetHeight);
        console.log(this.props);
        getNewscontent(this.props.match.params.newsid).then(res => {
            this.setState({
                newsContent:res.data.data
            })
            console.log(res.data.data);
        })
        
    }

    componentDidUpdate() {
       var h = document.getElementsByClassName('newscontent')[0].offsetTop
       var H = document.getElementsByClassName('newscontent')[0].offsetHeight
       
       document.getElementsByClassName('content')[0].style.height = h+H +'px'
        this.state.Bscroll.refresh()
        console.log(document.getElementsByTagName('pre')[0]);
    }

    inputClick() {
        this.setState({
            showInput: true
        })
    }

    getBscroll(Bscroll) {
        
        this.setState({
            Bscroll
    },() => {console.log(this.state.Bscroll);})}

    inputBlur() {
        this.setState({
            showInput:false
        })
    }

    nextPage() {
        getComment(this.props.match.params.newsid,this.state.commentPage).then(res => {
            console.log(res);
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
                    dangerouslySetInnerHTML={{__html: this.state.newsContent.content}} 
                    ></div>
                    <div className={newscontent.comment}>
                    
                    {i.map((e) => {
                      return  <CommentCard info ={e}></CommentCard>
                    })}
                    </div>
                </div>
                
                </MyScroll>

                {this.state.showInput===true?<CommentInput inputBlur={() => this.inputBlur()}></CommentInput>:null}
                
                <NewsTabBar inputClick={() => {this.inputClick()}}></NewsTabBar>
            </div>
        )
    }
}