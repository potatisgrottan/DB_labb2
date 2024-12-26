INSERT INTO Author (SSN, Name) VALUES
('196507310001', 'JK Rowling'),
('196605110002', 'Kentaro Miura'),
('192212280003', 'Stan Lee'),
('190306250004', 'George Orwell'),
('192604280005', 'Harper Lee');

INSERT INTO Book (ISBN, Title, Genre, Grade) VALUES
('978-0747532743', 'Harry Potter and the Philosophers Stone', 'Fantasy', 3),
('978-1591164012', 'Berserk', 'Dark Fantasy', 4),
('978-0785114509', 'Spider-Man: The Night Gwen Stacy Died', 'Comic', 3),
('978-0451524935', '1984', 'Dystopian', 5),
('978-0061120084', 'To Kill a Mockingbird', 'Fiction', 1);

INSERT INTO WrittenBy (Book_ISBN, Author_SSN) VALUES
('978-0747532743', '196507310001'),
('978-1591164012', '196605110002'),
('978-0785114509', '192212280003'),
('978-0451524935', '190306250004'),
('978-0061120084', '192604280005');
