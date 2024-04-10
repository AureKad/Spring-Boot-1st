package com.devitro.database.repositories;

import com.devitro.database.TestDataUtil;
import com.devitro.database.domain.entities.AuthorEntity;
import com.devitro.database.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository undertest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository undertest) {
        this.undertest = undertest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(author);
        undertest.save(bookEntity);
        Optional<BookEntity> result = undertest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(author);
        undertest.save(bookEntityA);

        BookEntity bookEntityB = TestDataUtil.createTestBookB(author);
        undertest.save(bookEntityB);

        BookEntity bookEntityC = TestDataUtil.createTestBookC(author);
        undertest.save(bookEntityC);

        Iterable<BookEntity> result = undertest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(author);
        undertest.save(bookEntityA);

        bookEntityA.setTitle("UPDATED");
        undertest.save(bookEntityA);

        Optional<BookEntity> result = undertest.findById(bookEntityA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(author);
        undertest.save(bookEntityA);

        undertest.deleteById(bookEntityA.getIsbn());

        Optional<BookEntity> result = undertest.findById(bookEntityA.getIsbn());
        assertThat(result).isNotPresent();
    }
}
