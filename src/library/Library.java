package library;

import library.exceptions.BorrowLimitExceededException;
import library.exceptions.ItemNotAvailableException;
import library.members.Member;
import library.models.Borrowable;
import library.models.LibraryItem;
import library.util.SearchResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Library {
    private Map<String, LibraryItem> catalog;
    private Map<String, Member> members;

    public Library() {
        this.catalog = new LinkedHashMap<>();
        this.members = new LinkedHashMap<>();
    }

    public void addItem(LibraryItem item) {
        catalog.put(item.getId(), item);
    }

    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public void borrowItem(String memberId, String itemId) {
        Member member = members.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found: " + memberId);
        }

        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item not found: " + itemId);
        }

        if (!item.isAvailable()) {
            throw new ItemNotAvailableException("Item \"" + item.getTitle() + "\" is currently not available.");
        }

        if (member.getCurrentBorrowCount() >= member.getMaxBorrowLimit()) {
            throw new BorrowLimitExceededException(
                    member.getName() + " (" + member.getTierName() + ") has reached the borrow limit of "
                            + member.getMaxBorrowLimit() + " items.");
        }

        if (item instanceof Borrowable) {
            ((Borrowable) item).borrow(member);
            member.borrowItem(item);
            System.out.println("Success! \"" + item.getTitle() + "\" borrowed by " + member.getName()
                    + ". Max loan: " + item.getMaxLoanDays() + " days.");
        }
    }

    public void returnItem(String memberId, String itemId, int daysKept) {
        Member member = members.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found: " + memberId);
        }

        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item not found: " + itemId);
        }

        if (!member.getBorrowedItems().contains(item)) {
            System.out.println("This member has not borrowed this item.");
            return;
        }

        if (item instanceof Borrowable) {
            Borrowable borrowable = (Borrowable) item;
            borrowable.returnItem(member);
            member.returnItem(item);

            int overdueDays = daysKept - item.getMaxLoanDays();
            if (overdueDays > 0) {
                double baseFine = borrowable.calculateFine(overdueDays);
                double finalFine = baseFine * member.getFineMultiplier();
                System.out.printf("Item returned. Overdue by %d day(s). Fine: %.2f AZN%n",
                        overdueDays, finalFine);
            } else {
                System.out.println("Item \"" + item.getTitle() + "\" returned on time. No fine.");
            }
        }
    }

    public SearchResult<LibraryItem> searchByTitle(String keyword) {
        List<LibraryItem> results = catalog.values().stream()
                .filter(item -> item.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return new SearchResult<>(results, keyword);
    }

    public SearchResult<LibraryItem> searchByAuthor(String keyword) {
        List<LibraryItem> results = catalog.values().stream()
                .filter(item -> item.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return new SearchResult<>(results, keyword);
    }

    public void listAllAvailable() {
        List<LibraryItem> available = catalog.values().stream()
                .filter(LibraryItem::isAvailable)
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            System.out.println("No items are currently available.");
            return;
        }

        System.out.println("Available items (" + available.size() + "):");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-6s | %-10s | %-25s | %-20s | %s%n",
                "ID", "Type", "Title", "Author", "Status");
        System.out.println("----------------------------------------------------------------------");
        available.forEach(LibraryItem::displayInfo);
    }

    public void getMemberReport(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("Member not found: " + memberId);
            return;
        }

        System.out.println("====== Member Report ======");
        System.out.println("ID:    " + member.getMemberId());
        System.out.println("Name:  " + member.getName());
        System.out.println("Tier:  " + member.getTierName());
        System.out.println("Borrow Limit: " + member.getMaxBorrowLimit());
        System.out.println("Fine Multiplier: " + member.getFineMultiplier());
        System.out.println("Currently Borrowed: " + member.getCurrentBorrowCount() + " item(s)");

        if (!member.getBorrowedItems().isEmpty()) {
            System.out.println("----------------------------------------------------------------------");
            System.out.printf("%-6s | %-10s | %-25s | %-20s%n", "ID", "Type", "Title", "Author");
            System.out.println("----------------------------------------------------------------------");
            for (LibraryItem item : member.getBorrowedItems()) {
                System.out.printf("%-6s | %-10s | %-25s | %-20s%n",
                        item.getId(), item.getItemType(), item.getTitle(), item.getAuthor());
            }
        }
        System.out.println("===========================");
    }

    public boolean hasMember(String memberId) {
        return members.containsKey(memberId);
    }

    public boolean hasItem(String itemId) {
        return catalog.containsKey(itemId);
    }
}
