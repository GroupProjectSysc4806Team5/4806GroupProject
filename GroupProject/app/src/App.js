import React, { Component } from 'react';
import './App.css';
import BookList from "./components/BookList/BookList";
import Home from './components/Home/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/books' exact={true} component={BookList}/>
                </Switch>
            </Router>
        )
    }
}

export default App;
