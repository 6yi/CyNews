import React, { Component } from 'react'
import {withRouter} from 'react-router-dom'
import { Card, WhiteSpace ,WingBlank,Flex} from 'antd-mobile'
import newsitem from './news-item.module.css'
class NewsItem extends Component {
    constructor() {
        super()
        this.state = {
          newsitem:{}
        }
    }

    componentDidMount() {
      this.setState({
        newsitem: this.props.newsitem
      })
    }

    render() {
        return (
            <div onClick={() => {this.props.history.push(`/content/${this.props.newsitem.nId}`)}}>
    
    <Card>
      <Card.Header
        extra={this.state.newsitem.tname}  
      />
      <Card.Body>
        <span className={newsitem.title}>{this.state.newsitem.ntitle}</span>
        <Flex className={newsitem.img}>
          { this.state.newsitem.imgSrc == null? null:this.state.newsitem.imgSrc.map((src) => {
            return  <img src={src} alt=""/>
          }) }
        </Flex>
      </Card.Body>
      <Card.Footer content={'时间'} extra={<div>extra footer content</div>} />
    </Card>
    
    </div>
        )
    }
}

export default withRouter(NewsItem)   //高阶组件，自动传入history