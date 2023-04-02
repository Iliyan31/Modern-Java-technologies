package bg.sofia.uni.fmi.mjt.escaperoom.team;

import bg.sofia.uni.fmi.mjt.escaperoom.rating.Ratable;

public class Team implements Ratable {

    private final String name;
    private double rating;
    private final TeamMember[] teamMembers;

    public Team(String name, TeamMember[] members) {
        this.name = name;
        this.teamMembers = members;
    }

    public static Team of(String name, TeamMember[] members) {
        return new Team(name, members);
    }

    @Override
    public double getRating() {
        return this.rating;
    }

    public void updateRating(int points) {
        if(points < 0) {
            throw new IllegalArgumentException("No negative points are allowed");
        }

        this.rating += points;
    }

    public String getName() {
        return this.name;
    }

    public TeamMember[] getTeamMembers() {
        return teamMembers;
    }

}
