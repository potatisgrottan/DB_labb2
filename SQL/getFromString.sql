USE Library;

SELECT * FROM Book
JOIN WrittenBy ON WrittenBy.Book_ISBN = Book.ISBN
JOIN Author ON WrittenBy.Author_SSN = Author.SSN;
