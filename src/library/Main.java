package library;

import library.exceptions.BorrowLimitExceededException;
import library.exceptions.ItemNotAvailableException;
import library.members.Member;
import library.members.PremiumMember;
import library.members.StandardMember;
import library.members.VIPMember;
import library.models.Book;
import library.models.LibraryItem;
import library.models.Magazine;
import library.models.Thesis;
import library.util.SearchResult;

import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addNewItem();
                    break;
                case "2":
                    registerNewMember();
                    break;
                case "3":
                    borrowItem();
                    break;
                case "4":
                    returnItem();
                    break;
                case "5":
                    searchItems();
                    break;
                case "6":
                    viewMemberReport();
                    break;
                case "7":
                    library.listAllAvailable();
                    break;
                case "8":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Library Management System =====");
        System.out.println("1. Add new item        (Book / Magazine / Thesis)");
        System.out.println("2. Register new member");
        System.out.println("3. Borrow item");
        System.out.println("4. Return item");
        System.out.println("5. Search items        (by title or author)");
        System.out.println("6. View member report");
        System.out.println("7. View all available items");
        System.out.println("8. Exit");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    private static void addNewItem() {
        System.out.print("Enter item type (1-Book, 2-Magazine, 3-Thesis): ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter item ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Item ID cannot be empty.");
            return;
        }
        if (library.hasItem(id)) {
            System.out.println("An item with this ID already exists.");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Author cannot be empty.");
            return;
        }

        LibraryItem item;
        switch (type) {
            case "1":
                item = new Book(id, title, author);
                break;
            case "2":
                item = new Magazine(id, title, author);
                break;
            case "3":
                item = new Thesis(id, title, author);
                break;
            default:
                System.out.println("Invalid item type.");
                return;
        }

        library.addItem(item);
        System.out.println(item.getItemType() + " \"" + title + "\" added successfully.");
    }

    private static void registerNewMember() {
        System.out.print("Enter member tier (1-Standard, 2-Premium, 3-VIP): ");
        String tier = scanner.nextLine().trim();

        System.out.print("Enter member ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Member ID cannot be empty.");
            return;
        }
        if (library.hasMember(id)) {
            System.out.println("A member with this ID already exists.");
            return;
        }

        System.out.print("Enter member name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        Member member;
        switch (tier) {
            case "1":
                member = new StandardMember(id, name);
                break;
            case "2":
                member = new PremiumMember(id, name);
                break;
            case "3":
                member = new VIPMember(id, name);
                break;
            default:
                System.out.println("Invalid tier.");
                return;
        }

        library.registerMember(member);
        System.out.println(member.getTierName() + " member \"" + name + "\" registered. Borrow limit: "
                + member.getMaxBorrowLimit() + " items.");
    }

    private static void borrowItem() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine().trim();

        try {
            library.borrowItem(memberId, itemId);
        } catch (ItemNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (BorrowLimitExceededException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnItem() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();

        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine().trim();

        System.out.print("How many days was the item kept? ");
        String daysStr = scanner.nextLine().trim();

        int daysKept;
        try {
            daysKept = Integer.parseInt(daysStr);
            if (daysKept < 0) {
                System.out.println("Days cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid number of days.");
            return;
        }

        try {
            library.returnItem(memberId, itemId, daysKept);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchItems() {
        System.out.print("Search by (1-Title, 2-Author): ");
        String searchType = scanner.nextLine().trim();

        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty.");
            return;
        }

        SearchResult<LibraryItem> result;
        switch (searchType) {
            case "1":
                result = library.searchByTitle(keyword);
                break;
            case "2":
                result = library.searchByAuthor(keyword);
                break;
            default:
                System.out.println("Invalid search type.");
                return;
        }

        result.display();
    }

    private static void viewMemberReport() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().trim();
        library.getMemberReport(memberId);
    }
}
