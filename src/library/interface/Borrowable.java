package library.models;

import library.members.Member;

public interface Borrowable {
    void borrow(Member member);
    void returnItem(Member member);
    double calculateFine(int overdueDays);
}
