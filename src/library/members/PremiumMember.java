package library.members;

public class PremiumMember extends Member {

    public PremiumMember(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getMaxBorrowLimit() { return 5; }

    @Override
    public double getFineMultiplier() { return 0.75; }

    @Override
    public String getTierName() { return "Premium"; }
}
