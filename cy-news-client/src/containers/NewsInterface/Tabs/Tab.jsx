import React,{Component} from 'react'
import { Tabs, WhiteSpace } from 'antd-mobile';
import TabCss from './Tab.module.css'
import NavList from '../NewsList/news-list'
export default class Demo extends Component {
    
    state = {
      activeTab:'0',
      activeIndex: '0',
      
    }

    componentDidMount() {
      
    }

render() {
    
    const tabs = [
      { title: '时事' },
      { title: '政治' },
      { title: '体育' },
      { title: '娱乐' },
      { title: '5th Tab' },
      { title: '6th Tab' },
      { title: '7th Tab' },
      { title: '8th Tab' },
      { title: '9th Tab' },
    ];
    

    return (
      <div className={TabCss.content}>
        
        <Tabs tabs={tabs}  swipeable={false}  renderTabBar={props => <Tabs.DefaultTabBar {...props} page={4} />} >
         {(e) => <NavList  tab={e}></NavList>}
        </Tabs>
        
      </div>
    );
  }
}