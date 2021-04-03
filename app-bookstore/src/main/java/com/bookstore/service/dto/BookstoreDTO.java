package com.bookstore.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bookstore.domain.Bookstore} entity.
 */
public class BookstoreDTO implements Serializable {

    private Long id;

    private String name;

    private OwnerDTO owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookstoreDTO)) {
            return false;
        }

        BookstoreDTO bookstoreDTO = (BookstoreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookstoreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookstoreDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", owner=" + getOwner() +
            "}";
    }
}
