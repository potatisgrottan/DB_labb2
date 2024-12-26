USE Library;

SET @title_search = '%19%';
SET @grade_search = '5';

SELECT Book.*, Author.*
FROM Book
JOIN WrittenBy ON WrittenBy.Book_ISBN = Book.ISBN
JOIN Author ON WrittenBy.Author_SSN = Author.SSN
WHERE (
    Book.Title LIKE @title_search
    OR Book.ISBN LIKE @title_search
    OR Author.Name LIKE @title_search
    OR Book.Genre LIKE @title_search
)
-- AND Book.Grade = @grade_search; -- This should be optional if no grade was given
