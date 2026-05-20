package library.members;

import library.models.LibraryItem;

import java.util.ArrayList;
import java.util.List;

public abstract class Member {
    private String memberId;
    private String name;
    private List<LibraryItem> borrowedItems;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    public abstract int getMaxBorrowLimit();
    public abstract double getFineMultiplier();
    public abstract String getTierName();

    public void borrowItem(LibraryItem item) {
        borrowedItems.add(item);
    }

    public void returnItem(LibraryItem item) {
        borrowedItems.remove(item);
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public List<LibraryItem> getBorrowedItems() { return borrowedItems; }

    public int getCurrentBorrowCount() { return borrowedItems.size(); }
}
