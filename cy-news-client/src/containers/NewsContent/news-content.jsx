import React,{Component} from 'react'
import Nav from '../../components/NavBar/nav'
import CommentInput from './CommentInput/comment-input'
import CommentCard from './CommentCard/commentcard'
import MyScroll from '../../components/BScroll/better-scroll'
import newscontent from './news-content.module.css'
import NewsTabBar from './TabBar/tabbar'
import './news-content.less'

export default class NewsContent extends Component {

    constructor() {
        super()
        this.state= {
            showInput: false
        }
    }
    componentDidMount() {
        console.log(this.props);
        
    }

    inputClick() {
        this.setState({
            showInput: true
        })
    }

    inputBlur() {
        this.setState({
            showInput:false
        })
    }

    render() {
        var i = [1,2,3,4,5,6,7]
        return(
            <div className='contentbox'>
                <Nav history={this.props.history}></Nav>
                
                <MyScroll>
                <div className={newscontent.newsbody}>
                    <span className={newscontent.title}>xxxx</span>
                    <div className={newscontent.authorbox}>
                        <img src="" alt=""/>
                        <div className={newscontent.authorinfo}>
                        <span className={newscontent.authorname}>ss</span>
                        <span className={newscontent.releasetime}>ss</span>
                        </div>
                    </div>

                    <div className={newscontent.newscontent}>  
                    </div>
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