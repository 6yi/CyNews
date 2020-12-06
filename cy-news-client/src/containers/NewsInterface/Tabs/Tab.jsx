import React,{Component} from 'react'
import { Tabs, WhiteSpace } from 'antd-mobile';
import TabCss from './Tab.module.css'

import NavList from '../NewsList/news-list'
export default class Demo extends Component {
    
 constructor() {
   super()
   this.state = {
     ActTab:''
   }
 }
  
  


render() {
    
    const tabs = [
      { title: '推荐' },
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
      <div className={TabCss.content + ' tabcontent'}>
        
        <Tabs tabs={tabs} destroyInactiveTab={true}  prerenderingSiblingsNumber={false}  swipeable={false}  renderTabBar={props => <Tabs.DefaultTabBar {...props} page={4} />} >
         {e => <NavList tabs={e}></NavList>}
        </Tabs>
        
      </div>
    );
  }
}