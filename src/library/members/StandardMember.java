package library.members;

public class StandardMember extends Member {

    public StandardMember(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getMaxBorrowLimit() { return 3; }

    @Override
    public double getFineMultiplier() { return 1.0; }

    @Override
    public String getTierName() { return "Standard"; }
}
