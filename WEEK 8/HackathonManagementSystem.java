import java.util.*;

public class HackathonManagementSystem {

    enum HackathonState {
        OPEN, JUDGING, PUBLISHED
    }

    interface ScoringRule {
        double calculate(int idea, int execution, int presentation);
    }

    static class InnovationScoringRule implements ScoringRule {
        public double calculate(int idea, int execution, int presentation) {
            return idea * 0.50 + execution * 0.30 + presentation * 0.20;
        }
    }

    static class OpenScoringRule implements ScoringRule {
        public double calculate(int idea, int execution, int presentation) {
            return (idea + execution + presentation) / 3.0;
        }
    }

    static class Track {
        String name;
        ScoringRule scoringRule;

        Track(String name, ScoringRule scoringRule) {
            this.name = name;
            this.scoringRule = scoringRule;
        }

        double calculateScore(int idea, int execution, int presentation) {
            return scoringRule.calculate(idea, execution, presentation);
        }
    }

    static class Student {
        String name;

        Student(String name) {
            this.name = name;
        }
    }

    static class Judge {
        String name;

        Judge(String name) {
            this.name = name;
        }

        void scoreProject(Project project, int idea, int execution, int presentation) {
            project.recordScore(this, idea, execution, presentation);
        }
    }

    static class Score {
        Judge judge;
        int idea;
        int execution;
        int presentation;

        Score(Judge judge, int idea, int execution, int presentation) {
            this.judge = judge;
            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
        }
    }

    static class Project {
        String name;
        Team team;
        List<Score> scores = new ArrayList<>();

        Project(String name, Team team) {
            this.name = name;
            this.team = team;
        }

        void recordScore(Judge judge, int idea, int execution, int presentation) {
            Hackathon hackathon = team.hackathon;

            if (hackathon.state == HackathonState.PUBLISHED) {
                System.out.println("Rescore rejected: Results have already been published.");
                return;
            }

            if (idea < 0 || idea > 10 ||
                execution < 0 || execution > 10 ||
                presentation < 0 || presentation > 10) {
                System.out.println("Score rejected: Ratings must be between 0 and 10.");
                return;
            }

            for (Score score : scores) {
                if (score.judge == judge) {
                    score.idea = idea;
                    score.execution = execution;
                    score.presentation = presentation;
                    System.out.println("Score updated for '" + name + "'.");
                    return;
                }
            }

            scores.add(new Score(judge, idea, execution, presentation));
            System.out.println("Score recorded for '" + name + "'.");
        }

        double calculateFinalScore() {
            if (scores.isEmpty()) {
                return 0.0;
            }

            double total = 0;

            for (Score score : scores) {
                total += team.track.calculateScore(
                    score.idea,
                    score.execution,
                    score.presentation
                );
            }

            return total / scores.size();
        }
    }

    static class Team {
        String name;
        List<Student> members = new ArrayList<>();
        Track track;
        Hackathon hackathon;
        Project project;

        Team(String name, Track track) {
            this.name = name;
            this.track = track;
        }

        boolean addMember(Student student) {
            if (members.size() >= 4) {
                return false;
            }

            members.add(student);
            return true;
        }

        boolean submitProject(String projectName) {
            if (project != null) {
                System.out.println("Submission failed: A team can submit only one project.");
                return false;
            }

            project = new Project(projectName, this);
            System.out.println(
                "Project '" + projectName + "' submitted by " + name + "."
            );
            return true;
        }
    }

    static class Hackathon {
        String name;
        HackathonState state = HackathonState.OPEN;
        List<Team> teams = new ArrayList<>();
        Set<Student> registeredStudents = new HashSet<>();

        Hackathon(String name) {
            this.name = name;
        }

        boolean registerTeam(Team team, List<Student> students) {

            if (state != HackathonState.OPEN) {
                System.out.println("Registration failed: Registration is closed.");
                return false;
            }

            if (students.size() < 2 || students.size() > 4) {
                System.out.println(
                    "Registration failed: A team must have 2 to 4 members."
                );
                return false;
            }

            for (Student student : students) {
                if (registeredStudents.contains(student)) {
                    System.out.println(
                        "Registration failed: " + student.name +
                        " already belongs to another team."
                    );
                    return false;
                }
            }

            for (Student student : students) {
                team.addMember(student);
                registeredStudents.add(student);
            }

            team.hackathon = this;
            teams.add(team);

            System.out.println(
                "Team " + team.name + " registered (" +
                students.size() + " members, " +
                team.track.name + " track)."
            );

            return true;
        }

        void startJudging() {
            if (state == HackathonState.OPEN) {
                state = HackathonState.JUDGING;
            }
        }

        void publishResults() {
            if (state == HackathonState.PUBLISHED) {
                return;
            }

            state = HackathonState.PUBLISHED;

            for (Team team : teams) {
                if (team.project != null) {
                    System.out.printf(
                        "Final score: %.2f%n",
                        team.project.calculateFinalScore()
                    );
                }
            }

            System.out.println("Results published.");
        }
    }

    public static void main(String[] args) {

        Track innovationTrack =
            new Track("Innovation", new InnovationScoringRule());

        Track openTrack =
            new Track("Open", new OpenScoringRule());

        Hackathon hackathon =
            new Hackathon("Code Sprint Hackathon");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        Team byteBusters =
            new Team("ByteBusters", innovationTrack);

        Team soloCoder =
            new Team("SoloCoder", openTrack);

        hackathon.registerTeam(
            byteBusters,
            Arrays.asList(asha, ravi, neha)
        );

        hackathon.registerTeam(
            soloCoder,
            Arrays.asList(kiran)
        );

        byteBusters.submitProject("SmartAttend");

        hackathon.startJudging();

        Judge judge = new Judge("Judge 1");

        judge.scoreProject(
            byteBusters.project,
            8,
            7,
            9
        );

        hackathon.publishResults();

        judge.scoreProject(
            byteBusters.project,
            10,
            7,
            9
        );
    }
}