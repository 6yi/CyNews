import React,{Component} from 'react'
import {Card} from 'antd-mobile'
import './commentcard.less'
import mlike from '../../../assets/img/like.png'
export default class CommentCard extends Component {




    render() {
        

        return(
           <div>
            <Card>
            <Card.Header
              title={this.props.commentInfo.unickname}
              thumb="https://gw.alipayobjects.com/zos/rmsportal/MRhHctKOineMbKAZslML.jpg"
           extra={<img src={mlike} />}
            />
            <Card.Body>
             <div>{this.props.commentInfo.ccontent}</div>
            </Card.Body>
            <Card.Footer content="footer content" extra={<div>extra footer content</div>} />
          </Card>
          </div> 
        )
    }
}