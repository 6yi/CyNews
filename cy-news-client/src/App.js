import NewsInterface from './containers/NewsInterface/news-interface'
import UserInterface from './containers/UserInterface/user-interface'
import Login from './containers/Login/login'
import Register from './containers/Register/register'
import {HashRouter,Route,Switch} from 'react-router-dom'
function App() {
  
  return (
    <HashRouter>
      <Switch>
        <Route path="/news" component={NewsInterface}></Route>
        <Route path="/user" component={UserInterface}></Route>
        <Route path="/login" component={Login}></Route>
        <Route path="/register" component={Register}></Route>
      </Switch>
    </HashRouter>
  )
}

export default App;
