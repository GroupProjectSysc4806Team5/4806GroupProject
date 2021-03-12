import React from 'react';
import PropTypes from 'prop-types';
import styles from './SearchBar.module.css';
import books from "../../fake_book_data";

const filterBooks = (books, query) => {
    console.log(query);
    if (!query) {
        return books;
    }

    return books.filter((post) => {
        console.log(post.name.toLowerCase());
        return post.name.toLowerCase().includes(query.toLowerCase());
    });
};

function SearchBar() {
    const { search } = window.location;
    const query = new URLSearchParams(search).get('s');
    const [searchQuery, setSearchQuery] = React.useState(query || '');
    const filteredBooks = filterBooks(books, searchQuery);

    return (
        <div>
            <div class="wrapper">
                <form action="/" method="get">
                        <div className="inner-form">
                            <div className="input-field">
                                <button className="btn-search" type="button">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                        <path
                                            d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"></path>
                                    </svg>
                                </button>
                                <input
                                    name="s"
                                    id="search"
                                    type="text"
                                    placeholder="Search Books"
                                    value={searchQuery}
                                    onInput={e => setSearchQuery(e.target.value)}/>
                            </div>
                        </div>
                </form>
            </div>
            <ul>
                <ul>
                    {filteredBooks.map(post => (
                        <li key={post.key}>{post.name}</li>
                    ))}
                </ul>
            </ul>
        </div>
    );
}

SearchBar.propTypes = {};

SearchBar.defaultProps = {};

export default SearchBar;