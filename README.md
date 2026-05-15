# Library Management System

## Author: Rokan 2550332

This is a project for the final in DATA STRUCTURES AND OBJECT ORIENTED PROGRAMMING.

This project mimics a library.

This code is a replication of a library management system. It defines classes for items, users, and a library, plus utility classes for validation and constants. Each class has fields and methods that match real world concepts - students, teachers, and admins belong to the library and can borrow items, items can be books, DVDs, or magazines and each has a status of available, borrowed, or lost, borrowing checks against user limits and item availability so exceptions are thrown when something goes wrong. IDs are auto generated so each object is uniquely identifiable. When a user borrows an item, the item status updates and the item is added to the user's borrowed list - when an item is returned, the status resets back to available.

This model can be used as the backend for a simple library tool. As an example you can run the main method to load items and users from CSV files, search for items by title, borrow and return items, mark items as lost, generate an admin report grouped by status, and backup all data back to CSV.
