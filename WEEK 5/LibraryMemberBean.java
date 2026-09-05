public class LibraryMemberBean {

    private String membershipId;
    private String name;
    private boolean premiumMember;

    // Write-only property
    private String securityAnswer;

    // No-argument constructor
    public LibraryMemberBean() {
        this(null, null);
    }

    // Name-only constructor
    public LibraryMemberBean(String name) {
        this(null, name);
    }

    // Main constructor
    public LibraryMemberBean(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }

    // Getter for membership ID
    public String getMembershipId() {
        return membershipId;
    }

    // Write-once membership ID
    public void setMembershipId(String id) {

        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Boolean getter uses isX()
    public boolean isPremiumMember() {
        return premiumMember;
    }

    // Setter for premium member
    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Write-only security answer
    public void setSecurityAnswer(String answer) {

        if (answer != null) {
            // Deterministic one-way transformation
            this.securityAnswer =
                    Integer.toHexString(answer.hashCode());
        }
    }

    public static void main(String[] args) {

        // Test name-only constructor
        LibraryMemberBean member1 =
                new LibraryMemberBean("Priya Nair");

        System.out.println(
                "Membership ID: " + member1.getMembershipId()
        );

        // Test ID + name constructor
        LibraryMemberBean member2 =
                new LibraryMemberBean(
                        "LIB-8841",
                        "Priya Nair"
                );

        System.out.println(
                "Membership ID: " + member2.getMembershipId()
        );

        // Test write-once membership ID
        LibraryMemberBean member3 =
                new LibraryMemberBean();

        member3.setMembershipId("LIB-8841");

        // This will be ignored
        member3.setMembershipId("FAKE-0000");

        System.out.println(
                "Final Membership ID: "
                        + member3.getMembershipId()
        );

        // Test boolean property
        member3.setPremiumMember(true);

        System.out.println(
                "Premium Member: "
                        + member3.isPremiumMember()
        );

        // Security answer can be set
        member3.setSecurityAnswer("Blue");

        // There is intentionally NO getSecurityAnswer() method
    }
}