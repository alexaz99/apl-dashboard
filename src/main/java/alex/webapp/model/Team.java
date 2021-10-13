package alex.webapp.model;

import javax.persistence.*;
import java.util.List;

/**
 * Entity class for using JPA
 * @Entity - This is a marker annotation which indicates that this class is an entity. This annotation must be placed on the class name.
 * @Id - This annotation is placed on a specific field that holds the persistent identifying properties. This field is treated as a primary key in database.
 */
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // need to tell String that this is a unuque identifier
    private long id;
    private String teamName;
    private long totalMatches;
    private long totalWins;

    // Also add Matches.
    @Transient
    private List<Match> matches;

    /** Need a default constructor for JPA */
    public Team() { }

    public Team(String teamName, long totalMatches) {
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(long totalMatches) {
        this.totalMatches = totalMatches;
    }

    public long getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(long totalWins) {
        this.totalWins = totalWins;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", totalMatches=" + totalMatches +
                ", totalWins=" + totalWins +
                '}';
    }
}
