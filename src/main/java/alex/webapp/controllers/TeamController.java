package alex.webapp.controllers;

import alex.webapp.model.Team;
import alex.webapp.repository.MatchRepository;
import alex.webapp.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * http://localhost:8080/team/Pune Warriors
 */
@RestController
public class TeamController {

    private final static Logger logger = LoggerFactory.getLogger(TeamController.class);

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    /**
     * run
     * http://localhost:8080/team/Pune Warriors
     *
     * API that accept not a request parameter but a parametarized path variable
     * @param teamName
     * @return
     */
    @GetMapping("/team/{teamName}")
    public Team getTeam(@NonNull @PathVariable String teamName) {

        Team team = teamRepository.findByTeamName(teamName);
        //return teamRepository.findByTeamName(teamName);

        // Cool stuff. Writing sequel queries usin method name!
        // make a call to get matches and set into the team
        // will need to sort it and give just the first N numbers (size) of the whole thing.
        // 1. Will specify sorting in JPA similar like we specified conditions.
        // 2. Now need to tucle to get top N records. Will do with page request passed in.
        //  Tell JPA that you want the result be pagable.
        team.setMatches(this.matchRepository.getByTeam1OrTeam2OrderByDateDesc(teamName, teamName));

        // populate the matches
        //getLatestMatches(teamName);

        return team;
    }
}
