import React,{Component} from 'react'
import {Card} from 'antd-mobile'
import './commentcard.less'
export default class CommentCard extends Component {




    render() {
        

        return(
           <div>
            <Card>
            <Card.Header
              title="This is title"
              thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
           extra={<span>sss</span>}
            />
            <Card.Body>
           <div>sss</div>
            </Card.Body>
            <Card.Footer content="footer content" extra={<div>extra footer content</div>} />
          </Card>
          </div> 
        )
    }
}