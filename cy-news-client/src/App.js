import NewsInterface from './containers/NewsInterface/news-interface'
import UserInterface from './containers/UserInterface/user-interface'
import Login from './containers/Login/login'
import Register from './containers/Register/register'
import {HashRouter,Route,Switch} from 'react-router-dom'
import store from './redux/store'
import {Provider} from 'react-redux'
function App() {
  
  return (
    <Provider store={store}>
    <HashRouter>
      <Switch>
        <Route path="/news" component={NewsInterface}></Route>
        <Route path="/user" component={UserInterface}></Route>
        <Route path="/login" component={Login}></Route>
        <Route path="/register" component={Register}></Route>
      </Switch>
    </HashRouter>
    </Provider>
  )
}

export default App;
