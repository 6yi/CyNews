import React, { Component } from 'react'
import { Card, WhiteSpace ,win} from 'antd-mobile'
import './news-item.css'
export default class NewsItem extends Component {
    constructor() {
        super()
        this.state = {

        }
    }

    render() {
        return (
            <div>
    
    <Card>
      <Card.Header
        title="This is title"
        thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
        extra={<span>this is extra</span>}
      />
      <Card.Body>
        <div>This is content of `Card`</div>
      </Card.Body>
      <Card.Footer content="footer content" extra={<div>extra footer content</div>} />
    </Card>
    
    </div>
        )
    }
}