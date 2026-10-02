import java.util.*;

interface ScoringRule {
    double calculateScore(Score score);
    String getTrackName();
}

class InnovationScoringRule implements ScoringRule {
    public double calculateScore(Score score) {
        return score.getIdea() * 0.50
             + score.getExecution() * 0.30
             + score.getPresentation() * 0.20;
    }

    public String getTrackName() {
        return "Innovation";
    }
}

class OpenScoringRule implements ScoringRule {
    public double calculateScore(Score score) {
        return (score.getIdea() + score.getExecution()
              + score.getPresentation()) / 3.0;
    }

    public String getTrackName() {
        return "Open";
    }
}

class Student {
    private String name;
    private Set<Hackathon> hackathons;

    public Student(String name) {
        this.name = name;
        this.hackathons = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public boolean isRegistered(Hackathon hackathon) {
        return hackathons.contains(hackathon);
    }

    public void addHackathon(Hackathon hackathon) {
        hackathons.add(hackathon);
    }
}

class Team {
    private String name;
    private List<Student> members;
    private ScoringRule scoringRule;
    private Project project;

    public Team(String name, List<Student> members, ScoringRule scoringRule) {
        this.name = name;
        this.members = members;
        this.scoringRule = scoringRule;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return members;
    }

    public ScoringRule getScoringRule() {
        return scoringRule;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

class Project {
    private String name;
    private Team team;
    private Score score;
    private boolean scored;

    public Project(String name, Team team) {
        this.name = name;
        this.team = team;
        this.scored = false;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
        this.scored = true;
    }

    public boolean isScored() {
        return scored;
    }
}

class Judge {
    private String name;

    public Judge(String name) {
        this.name = name;
    }

    public void scoreProject(Project project, int idea, int execution,
                             int presentation, Hackathon hackathon) {

        if (hackathon.isPublished()) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }

        if (idea < 0 || idea > 10 ||
            execution < 0 || execution > 10 ||
            presentation < 0 || presentation > 10) {

            System.out.println("Invalid score. Ratings must be between 0 and 10.");
            return;
        }

        Score score = new Score(idea, execution, presentation);
        project.setScore(score);

        System.out.println("Score recorded for '" + project.getName() + "'.");
    }
}

class Score {
    private int idea;
    private int execution;
    private int presentation;

    public Score(int idea, int execution, int presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    public int getIdea() {
        return idea;
    }

    public int getExecution() {
        return execution;
    }

    public int getPresentation() {
        return presentation;
    }
}

class Hackathon {
    private String name;
    private List<Team> teams;
    private Set<Student> registeredStudents;
    private String state;

    public Hackathon(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.registeredStudents = new HashSet<>();
        this.state = "Open";
    }

    public void registerTeam(Team team) {

        if (!state.equals("Open")) {
            System.out.println("Registration failed: Registration is closed.");
            return;
        }

        int size = team.getMembers().size();

        if (size < 2 || size > 4) {
            System.out.println(
                "Registration failed: A team must have 2 to 4 members."
            );
            return;
        }

        for (Student student : team.getMembers()) {
            if (registeredStudents.contains(student)) {
                System.out.println(
                    "Registration failed: " + student.getName()
                    + " already belongs to another team."
                );
                return;
            }
        }

        teams.add(team);

        for (Student student : team.getMembers()) {
            registeredStudents.add(student);
            student.addHackathon(this);
        }

        System.out.println(
            "Team " + team.getName() + " registered ("
            + size + " members, "
            + team.getScoringRule().getTrackName() + " track)."
        );
    }

    public void submitProject(Team team, String projectName) {

        if (!state.equals("Open")) {
            System.out.println("Submission failed: Submission is closed.");
            return;
        }

        if (!teams.contains(team)) {
            System.out.println("Submission failed: Team is not registered.");
            return;
        }

        if (team.getProject() != null) {
            System.out.println(
                "Submission failed: A team can submit only one project."
            );
            return;
        }

        Project project = new Project(projectName, team);
        team.setProject(project);

        System.out.println(
            "Project '" + projectName + "' submitted by "
            + team.getName() + "."
        );
    }

    public void startJudging() {
        if (state.equals("Open")) {
            state = "Judging";
        }
    }

    public void publishResults() {
        if (!state.equals("Judging")) {
            System.out.println("Results cannot be published at this stage.");
            return;
        }

        state = "Published";

        System.out.println("Results published.");

        for (Team team : teams) {
            Project project = team.getProject();

            if (project != null && project.isScored()) {
                double finalScore =
                    team.getScoringRule().calculateScore(project.getScore());

                System.out.printf(
                    "Final score: %.2f%n",
                    finalScore
                );
            }
        }
    }

    public boolean isPublished() {
        return state.equals("Published");
    }

    public String getState() {
        return state;
    }
}

public class HackathonManagement {

    public static void main(String[] args) {

        Hackathon hackathon =
            new Hackathon("Code Sprint");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        Team byteBusters = new Team(
            "ByteBusters",
            Arrays.asList(asha, ravi, neha),
            new InnovationScoringRule()
        );

        Team soloCoder = new Team(
            "SoloCoder",
            Arrays.asList(kiran),
            new OpenScoringRule()
        );

        hackathon.registerTeam(byteBusters);
        hackathon.registerTeam(soloCoder);

        hackathon.submitProject(
            byteBusters,
            "SmartAttend"
        );

        hackathon.startJudging();

        Judge judge = new Judge("Judge1");

        judge.scoreProject(
            byteBusters.getProject(),
            8,
            7,
            9,
            hackathon
        );

        hackathon.publishResults();

        judge.scoreProject(
            byteBusters.getProject(),
            10,
            7,
            9,
            hackathon
        );
    }
}

