import java.util.Scanner;

public class AccessChecker {

static String classifyAccess(String fieldModifier,
                             String accessorContext) {

    // PRIVATE
    if (fieldModifier.equals("private")) {

        if (accessorContext.equals("SAME_CLASS")) {
            return "ALLOWED";
        }
        return "DENIED";
    }

    // DEFAULT
    else if (fieldModifier.equals("default")) {

        if (accessorContext.equals("SAME_CLASS") ||
            accessorContext.equals("SAME_PACKAGE")) {

            return "ALLOWED";
        }

        return "DENIED";
    }

    // PROTECTED
    else if (fieldModifier.equals("protected")) {

        if (accessorContext.equals("SAME_CLASS") ||
            accessorContext.equals("SAME_PACKAGE") ||
            accessorContext.equals(
                "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {

            return "ALLOWED";
        }

        return "DENIED";
    }

    // PUBLIC
    else if (fieldModifier.equals("public")) {
        return "ALLOWED";
    }

    return "DENIED";
}

static String describeContext(String accessorContext) {

    // Split using underscore
    String[] words = accessorContext.split("_");

    String result = "";

    for (int i = 0; i < words.length; i++) {

        // Convert first letter to uppercase
        String formattedWord =
                words[i].substring(0, 1).toUpperCase()
                + words[i].substring(1).toLowerCase();

        result += formattedWord;

        if (i < words.length - 1) {
            result += " ";
        }
    }

    return result;
}

public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.println("Enter Field Modifier:");
    String fieldModifier = sc.nextLine();

    System.out.println("Enter Accessor Context:");
    String accessorContext = sc.nextLine();

    System.out.println("\nContext: "
            + describeContext(accessorContext));

    System.out.println("Access: "
            + classifyAccess(
                fieldModifier,
                accessorContext
            ));

    sc.close();
}

}
