package library.models;

import library.members.Member;

public class Magazine extends LibraryItem implements Borrowable {
    private static final double FINE_PER_DAY = 0.50;

    public Magazine(String id, String title, String author) {
        super(id, title, author);
    }

    @Override
    public String getItemType() { return "Magazine"; }

    @Override
    public int getMaxLoanDays() { return 7; }

    @Override
    public void borrow(Member member) {
        setAvailable(false);
    }

    @Override
    public void returnItem(Member member) {
        setAvailable(true);
    }

    @Override
    public double calculateFine(int overdueDays) {
        if (overdueDays <= 0) return 0;
        return overdueDays * FINE_PER_DAY;
    }
}
