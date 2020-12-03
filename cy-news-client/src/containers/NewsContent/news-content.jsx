import React,{Component} from 'react'
import Nav from '../../components/NavBar/nav'
import newscontent from './news-content.module.css'
import NewsTabBar from './TabBar/tabbar'
export default class NewsContent extends Component {

    componentDidMount() {
        console.log(this.props);
    }


    render() {
        return(
            <div>
                <Nav history={this.props.history}></Nav>
                <div className={newscontent.newsbody}>
                    <span className={newscontent.title}>xxxx</span>
                    <div className={newscontent.authorbox}>
                        <img src="" alt=""/>
                        <div className={newscontent.authorinfo}>
                        <span className={newscontent.authorname}></span>
                        <span className={newscontent.releasetime}></span>
                        </div>
                        
                        <div className={NewsContent.newscontent}>

                        </div>
                    </div>
                </div>
                <NewsTabBar></NewsTabBar>
            </div>
        )
    }
}