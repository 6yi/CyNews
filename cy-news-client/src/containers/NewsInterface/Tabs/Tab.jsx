import React,{Component} from 'react'
import { Tabs, WhiteSpace } from 'antd-mobile';
import TabCss from './Tab.module.css'
import NavList from '../NewsList/NewsList'
export default class Demo extends Component {
    
    state = {
      activeTab:'0',
      activeIndex: '0'
    }

render() {
    
    const tabs = [
      { title: '1st Tab' },
      { title: '2nd Tab' },
      { title: '3rd Tab' },
      { title: '4th Tab' },
      { title: '5th Tab' },
      { title: '6th Tab' },
      { title: '7th Tab' },
      { title: '8th Tab' },
      { title: '9th Tab' },
    ];
    

    return (
      <div className={TabCss.content}>
        <WhiteSpace />
        <Tabs tabs={tabs} page={this.state.activeIndex} onTabClick={(e,index) => {this.setState({activeIndex: index})}} renderTabBar={props => <Tabs.DefaultTabBar {...props} page={4} />} >
         {() => <NavList tab={this.state.activeIndex}></NavList>}
        </Tabs>
        <WhiteSpace />
      </div>
    );
  }
}