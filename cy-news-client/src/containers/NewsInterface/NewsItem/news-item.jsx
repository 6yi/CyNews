import React, { Component } from 'react'
import { Card, WhiteSpace ,WingBlank,Flex} from 'antd-mobile'
import newsitem from './news-item.module.css'
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
        title={'name here'}
        thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
        thumbStyle={{
          width:35+'px',
          borderRadius:100+'%'
        }}
      />
      <Card.Body>
        <span className={newsitem.title}>here is title</span>
        <Flex className={newsitem.img}>
          <img src="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg" alt=""/> 
          <WingBlank size='sm' />
          <img src="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg" alt=""/> 
          <WingBlank size='sm' />
          <img src="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg" alt=""/> 
          <WingBlank size='sm' />
        </Flex>
      </Card.Body>
      <Card.Footer content="footer content" extra={<div>extra footer content</div>} />
    </Card>
    
    </div>
        )
    }
}