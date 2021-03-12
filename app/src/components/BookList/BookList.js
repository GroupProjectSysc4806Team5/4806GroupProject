import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import NavigationBar from '../NavigationBar/NavigationBar';
import { Link } from 'react-router-dom';

class BookList extends Component {

    constructor(props) {
        super(props);
        this.state = {books: [], isLoading: true, searchQuery: ""};
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('/books')
            .then(response => response.json())
            .then(data => this.setState({books: data._embedded.books, isLoading: false}));
    }

    setSearchQuery = (e) => {
        this.setState({searchQuery: e.target.value});
    }

    filterBooks = (books, query) => {
        if (!query) {
            return books;
        }

        return books.filter((book) => {
            return book.bookName.toLowerCase().includes(query.toLowerCase());
        });
    }

    render() {
        const filteredBooks = this.filterBooks(this.state.books, this.state.searchQuery);

        const bookList = filteredBooks.map(book => {
            return <tr key={book.id}>
                <td style={{whiteSpace: 'nowrap'}}>{book.bookName}</td>
                <td>{book.isbn}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/books/" + book.id}>Edit</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <NavigationBar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/new_book">Add Book</Button>
                    </div>
                    <h3>Library</h3>
                    <input
                        name="s"
                        id="search"
                        type="text"
                        placeholder="Search Books"
                        value={this.state.searchQuery}
                        onInput={this.setSearchQuery}
                    />
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">ISBN</th>
                        </tr>
                        </thead>
                        <tbody>
                        {bookList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default BookList;