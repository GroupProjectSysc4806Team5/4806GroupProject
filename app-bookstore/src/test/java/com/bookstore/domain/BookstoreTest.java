package com.bookstore.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookstoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bookstore.class);
        Bookstore bookstore1 = new Bookstore();
        bookstore1.setId(1L);
        Bookstore bookstore2 = new Bookstore();
        bookstore2.setId(bookstore1.getId());
        assertThat(bookstore1).isEqualTo(bookstore2);
        bookstore2.setId(2L);
        assertThat(bookstore1).isNotEqualTo(bookstore2);
        bookstore1.setId(null);
        assertThat(bookstore1).isNotEqualTo(bookstore2);
    }
}
