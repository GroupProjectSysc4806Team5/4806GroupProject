package Bookstore.Model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
      OwnerTest.class,
      BookstoreTest.class,
      BookTest.class,
      CartTest.class
})

public class TestSuite {
}  