package com.bookstore.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bookstore.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookstoreDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookstoreDTO.class);
        BookstoreDTO bookstoreDTO1 = new BookstoreDTO();
        bookstoreDTO1.setId(1L);
        BookstoreDTO bookstoreDTO2 = new BookstoreDTO();
        assertThat(bookstoreDTO1).isNotEqualTo(bookstoreDTO2);
        bookstoreDTO2.setId(bookstoreDTO1.getId());
        assertThat(bookstoreDTO1).isEqualTo(bookstoreDTO2);
        bookstoreDTO2.setId(2L);
        assertThat(bookstoreDTO1).isNotEqualTo(bookstoreDTO2);
        bookstoreDTO1.setId(null);
        assertThat(bookstoreDTO1).isNotEqualTo(bookstoreDTO2);
    }
}
