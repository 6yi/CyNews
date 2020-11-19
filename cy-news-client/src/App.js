import NewsInterface from './containers/NewsInterface/news-interface'
import UserInterface from './containers/UserInterface/user-interface'
import {HashRouter,Route,Switch} from 'react-router-dom'
function App() {
  return (
    <HashRouter>
      <Switch>
        <Route path="/news" component={NewsInterface}></Route>
        <Route path="/user" component={UserInterface}></Route>
      </Switch>
    </HashRouter>
  )
}

export default App;
