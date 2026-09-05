import java.util.Scanner;

public class LibraryMember {

// Four fields with different access modifiers
private String membershipId;
String branchCode;                 // default access
protected double finesOwed;
public String displayName;

// Parameterized constructor only
public LibraryMember(String membershipId, String branchCode,
                     double finesOwed, String displayName) {

    String id = membershipId == null ? "" : membershipId.trim();

    if (id.isEmpty() || id.length() < 4) {
        throw new IllegalArgumentException(
                "Invalid membership ID: must be at least 4 characters"
        );
    }

    this.membershipId = id;
    this.branchCode = branchCode;
    this.finesOwed = finesOwed;
    this.displayName = displayName;
}

// Check access according to Java visibility rules
static String classifyAccess(String fieldModifier,
                             String accessorContext) {

    if (fieldModifier.equals("private")) {

        if (accessorContext.equals("SAME_CLASS")) {
            return "ALLOWED";
        } else {
            return "DENIED";
        }

    } else if (fieldModifier.equals("default")) {

        if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
            return "ALLOWED";
        } else {
            return "DENIED";
        }

    } else if (fieldModifier.equals("protected")) {

        if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
            return "ALLOWED";
        } else {
            return "DENIED";
        }

    } else if (fieldModifier.equals("public")) {

        return "ALLOWED";
    }

    return "DENIED";
}

// Summarize access attempts modifier-wise
static String summarizeByModifier(String[][] attempts) {

    String[] modifiers = {
            "private", "default", "protected", "public"
    };

    int[] allowed = new int[4];
    int[] denied = new int[4];

    for (int i = 0; i < attempts.length; i++) {

        String modifier = attempts[i][0];
        String context = attempts[i][1];

        String result = classifyAccess(modifier, context);

        int index = -1;

        for (int j = 0; j < modifiers.length; j++) {
            if (modifier.equals(modifiers[j])) {
                index = j;
                break;
            }
        }

        if (result.equals("ALLOWED")) {
            allowed[index]++;
        } else {
            denied[index]++;
        }
    }

    String result = "";

    for (int i = 0; i < modifiers.length; i++) {

        result += modifiers[i] + ": "
                + allowed[i] + " allowed / "
                + denied[i] + " denied";

        if (i < modifiers.length - 1) {
            result += " | ";
        }
    }

    return result;
}

public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Membership ID: ");
    String id = sc.nextLine();

    System.out.print("Enter Branch Code: ");
    String branch = sc.nextLine();

    System.out.print("Enter Fines Owed: ");
    double fines = Double.parseDouble(sc.nextLine());

    System.out.print("Enter Display Name: ");
    String name = sc.nextLine();

    try {

        LibraryMember member =
                new LibraryMember(id, branch, fines, name);

        System.out.println("\nLibrary Member Created Successfully!");
        System.out.println("Membership ID: " + member.membershipId);

    } catch (IllegalArgumentException e) {

        System.out.println("Construction rejected: "
                + e.getMessage());
    }

    String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
    };

    System.out.println("\nAccess Summary:");
    System.out.println(summarizeByModifier(attempts));

    sc.close();
}

}
