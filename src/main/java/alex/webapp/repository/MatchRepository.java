package alex.webapp.repository;

import alex.webapp.model.Match;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    // Hey GPA Get me all the matches where team1 is equals to teamName1 or team2 equals to teamName2.

    // Cool stuff. Writing sequel queries usin method name!
    // make a call to get matches and set into the team
    // 0. Get team1 Or team2.. Write method name like query.
    // will need to sort it and give just the first N numbers (size) of the whole thing.
    // 1. Will specify sorting in JPA similar like we specified conditions.
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2);

}
