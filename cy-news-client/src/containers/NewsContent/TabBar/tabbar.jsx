import React, { Component } from 'react'
import tabbar from './tabbar.module.css'
import { Badge } from 'antd-mobile'
import editImge from '../../../assets/img/edit.png'
import commentImge from '../../../assets/img/comment.png'
import likeImage from '../../../assets/img/like.png'
import collectImage from '../../../assets/img/collect.png'
import shareImage from '../../../assets/img/share.png' 
import './tabbar.less'

export default class NewsTabBar extends Component {
    constructor() {
        super()
        this.state ={
            newsMessage:{}
        }
    }
    
    componentDidMount() {
        
        this.setState({
            newsMessage:this.props.newsMessage
        })
    }
    
    render() {
        return (
            <div className={tabbar.box}>
                <div className={tabbar.input} onClick={this.props.inputClick}>
                    <img className={tabbar.img} src={editImge} alt="" />
                    <span className={tabbar.pac}>说两句</span>
                </div>

                <div className={tabbar.comment +' comment'}>
                    <img src={commentImge} alt="" />
                </div>

                <div className={tabbar.like}>
                    <Badge text={this.props.newsMessage.mlike}>
                        <img src={likeImage} alt="" />
                    </Badge>
                </div>

                <div className={tabbar.collect}>
                    <img src={collectImage} alt="" />
                </div>

                <div className={tabbar.share}>
                    <img src={shareImage} alt="" />
                </div>
            </div>
        )
    }
}