import React, { Component } from 'react'
import {withRouter} from 'react-router-dom'
import { Card, WhiteSpace ,WingBlank,Flex} from 'antd-mobile'
import newsitem from './news-item.module.css'
class NewsItem extends Component {
    constructor() {
        super()
        this.state = {

        }
    }

    render() {
        return (
            <div onClick={() => {this.props.history.push('/content/ssss')}}>
    
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

export default withRouter(NewsItem)   //高阶组件，自动传入history