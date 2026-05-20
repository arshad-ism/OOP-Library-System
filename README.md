# Library Management System

A console-based Library Management System built in Java, demonstrating core Object-Oriented Programming principles. This project manages books, magazines, theses, and library members with borrowing operations, fine calculation, and search functionality.

**Variation:** `student_number mod 3 = 0` (Standard / Premium / VIP)

## Features

- Add library items (Book, Magazine, Thesis) with unique IDs
- Register members across three tiers with different borrow limits
- Borrow and return items with availability tracking
- Overdue fine calculation with tier-based multipliers
- Search items by title or author
- View detailed member reports
- Input validation and error handling throughout

### Member Tiers

| Tier     | Max Borrow Limit | Fine Multiplier | Fine Per Overdue Day |
|----------|------------------|-----------------|----------------------|
| Standard | 3 items          | 1.0x            | 0.50 AZN             |
| Premium  | 5 items          | 0.75x           | 0.375 AZN            |
| VIP      | 7 items          | 0.5x            | 0.25 AZN             |

### Item Types

| Type     | Max Loan Days |
|----------|---------------|
| Book     | 14 days       |
| Magazine | 7 days        |
| Thesis   | 21 days       |

## How to Compile and Run

```bash
# Compile
javac -d out -sourcepath src src/library/Main.java

# Run
java -cp out library.Main
```

## Project Structure

```
src/library/
├── Main.java                             # Entry point, console menu
├── Library.java                          # Central manager class
├── models/
│   ├── LibraryItem.java                  # Abstract base class for all items
│   ├── Book.java                         # Book (14-day loan)
│   ├── Magazine.java                     # Magazine (7-day loan)
│   └── Thesis.java  
├──interface/
|   ├── Borrowable.java                   # Interface for borrowable items                   # Thesis (21-day loan)
├── members/
│   ├── Member.java                       # Abstract base class for members
│   ├── StandardMember.java               # Limit: 3, multiplier: 1.0
│   ├── PremiumMember.java                # Limit: 5, multiplier: 0.75
│   └── VIPMember.java                    # Limit: 7, multiplier: 0.5
├── exceptions/
│   ├── ItemNotAvailableException.java    # Thrown when item is already borrowed
│   └── BorrowLimitExceededException.java # Thrown when member exceeds limit
└── util/
    └── SearchResult.java                 # Generic search result container
```

## OOP Concepts Used

- **Abstract Classes:** `LibraryItem` and `Member` define shared fields and behavior for their subclasses
- **Inheritance:** `Book`, `Magazine`, `Thesis` extend `LibraryItem`; `StandardMember`, `PremiumMember`, `VIPMember` extend `Member`
- **Interfaces:** `Borrowable` defines the borrow/return/fine contract
- **Polymorphism:** Different item types return different loan days; different member tiers return different limits and multipliers
- **Encapsulation:** All fields are private with controlled access through getters and validation
- **Collections:** `Map<String, LibraryItem>` for the catalog, `Map<String, Member>` for members, `List<LibraryItem>` for borrowed items
- **Generics:** `SearchResult<T extends LibraryItem>` provides type-safe search results
- **Exception Handling:** Custom `ItemNotAvailableException` and `BorrowLimitExceededException` thrown and caught at appropriate levels
