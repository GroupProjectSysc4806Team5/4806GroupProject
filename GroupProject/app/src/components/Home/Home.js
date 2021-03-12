import React, { Component } from 'react';
import '../../App.css';
import NavigationBar from '../NavigationBar/NavigationBar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <NavigationBar/>
                <Container fluid>
                    <Button color="link"><Link to="/books">Manage Books</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;