import java.util.*;

public class ElectiveSeatRush {

    interface CreditPolicy {
        int getCreditLimit();
        String getType();
    }

    static class RegularPolicy implements CreditPolicy {
        public int getCreditLimit() {
            return 24;
        }

        public String getType() {
            return "Regular";
        }
    }

    static class HonorsPolicy implements CreditPolicy {
        public int getCreditLimit() {
            return 28;
        }

        public String getType() {
            return "Honors";
        }
    }

    static class ExchangePolicy implements CreditPolicy {
        public int getCreditLimit() {
            return 20;
        }

        public String getType() {
            return "Exchange";
        }
    }

    static class Student {
        String name;
        int currentCredits;
        CreditPolicy policy;

        Student(String name, int currentCredits, CreditPolicy policy) {
            this.name = name;
            this.currentCredits = currentCredits;
            this.policy = policy;
        }

        int getCreditLimit() {
            return policy.getCreditLimit();
        }

        String getType() {
            return policy.getType();
        }

        boolean canAddCredits(int credits) {
            return currentCredits + credits <= getCreditLimit();
        }
    }

    static class Enrollment {
        Student student;
        Elective elective;

        Enrollment(Student student, Elective elective) {
            this.student = student;
            this.elective = elective;
        }
    }

    static class Elective {
        String name;
        int credits;
        int capacity;

        List<Enrollment> enrollments = new ArrayList<>();
        Queue<Student> waitlist = new LinkedList<>();

        Elective(String name, int credits, int capacity) {
            this.name = name;
            this.credits = credits;
            this.capacity = capacity;
        }

        boolean isEnrolled(Student student) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.student == student) {
                    return true;
                }
            }
            return false;
        }

        boolean isWaiting(Student student) {
            return waitlist.contains(student);
        }

        boolean hasStudent(Student student) {
            return isEnrolled(student) || isWaiting(student);
        }

        boolean isFull() {
            return enrollments.size() >= capacity;
        }

        boolean addEnrollment(Student student) {

            if (isFull()) {
                return false;
            }

            if (!student.canAddCredits(credits)) {
                return false;
            }

            enrollments.add(
                new Enrollment(student, this)
            );

            student.currentCredits += credits;

            return true;
        }

        boolean removeEnrollment(Student student) {

            for (int i = 0; i < enrollments.size(); i++) {

                if (enrollments.get(i).student == student) {

                    enrollments.remove(i);
                    student.currentCredits -= credits;

                    return true;
                }
            }

            return false;
        }

        int getWaitlistPosition(Student student) {

            int position = 1;

            for (Student waiting : waitlist) {

                if (waiting == student) {
                    return position;
                }

                position++;
            }

            return -1;
        }

        Student removeFirstWaitlisted() {
            return waitlist.poll();
        }

        void addToWaitlist(Student student) {
            waitlist.offer(student);
        }
    }

    static class EnrollmentService {

        void enroll(Student student, Elective elective) {

            if (elective.hasStudent(student)) {
                System.out.println(
                    "Enrollment failed: " +
                    student.name +
                    " is already enrolled or waitlisted for " +
                    elective.name + "."
                );
                return;
            }

            if (!student.canAddCredits(elective.credits)) {

                System.out.println(
                    "Enrollment failed: " +
                    student.name +
                    " would exceed the " +
                    student.getType() +
                    " credit limit (" +
                    (student.currentCredits + elective.credits) +
                    "/" +
                    student.getCreditLimit() +
                    ")."
                );

                return;
            }

            if (elective.isFull()) {

                System.out.println(
                    elective.name + " is full."
                );

                elective.addToWaitlist(student);

                System.out.println(
                    student.name +
                    " added to waitlist (position " +
                    elective.getWaitlistPosition(student) +
                    ")."
                );

                return;
            }

            elective.addEnrollment(student);

            System.out.println(
                student.name +
                " enrolled in " +
                elective.name +
                " (credits: " +
                student.currentCredits +
                "/" +
                student.getCreditLimit() +
                ")."
            );
        }

        void drop(Student student, Elective elective) {

            if (!elective.isEnrolled(student)) {

                System.out.println(
                    "Drop failed: " +
                    student.name +
                    " is not enrolled in " +
                    elective.name + "."
                );

                return;
            }

            elective.removeEnrollment(student);

            System.out.println(
                student.name +
                " dropped " +
                elective.name +
                " (credits: " +
                student.currentCredits +
                "/" +
                student.getCreditLimit() +
                ")."
            );

            promoteNext(elective);
        }

        private void promoteNext(Elective elective) {

            while (!elective.isFull() &&
                   !elective.waitlist.isEmpty()) {

                Student student =
                    elective.removeFirstWaitlisted();

                if (student.canAddCredits(elective.credits)) {

                    elective.addEnrollment(student);

                    System.out.println(
                        student.name +
                        " promoted from waitlist and enrolled in " +
                        elective.name +
                        " (credits: " +
                        student.currentCredits +
                        "/" +
                        student.getCreditLimit() +
                        ")."
                    );

                } else {

                    System.out.println(
                        student.name +
                        " cannot be promoted because the " +
                        student.getType() +
                        " credit limit would be exceeded."
                    );
                }
            }
        }
    }

    public static void main(String[] args) {

        EnrollmentService service =
            new EnrollmentService();

        Elective cloudComputing =
            new Elective(
                "Cloud Computing",
                4,
                2
            );

        Student asha =
            new Student(
                "Asha",
                20,
                new RegularPolicy()
            );

        Student ravi =
            new Student(
                "Ravi",
                22,
                new HonorsPolicy()
            );

        Student neha =
            new Student(
                "Neha",
                12,
                new ExchangePolicy()
            );

        Student kiran =
            new Student(
                "Kiran",
                22,
                new RegularPolicy()
            );

        service.enroll(
            asha,
            cloudComputing
        );

        service.enroll(
            ravi,
            cloudComputing
        );

        service.enroll(
            neha,
            cloudComputing
        );

        service.enroll(
            kiran,
            cloudComputing
        );

        service.drop(
            asha,
            cloudComputing
        );
    }
}