package library.members;

public class VIPMember extends Member {

    public VIPMember(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getMaxBorrowLimit() { return 7; }

    @Override
    public double getFineMultiplier() { return 0.5; }

    @Override
    public String getTierName() { return "VIP"; }
}
