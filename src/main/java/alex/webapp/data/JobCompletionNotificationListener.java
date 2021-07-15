package alex.webapp.data;

import alex.webapp.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Run this listener on the job completion.
 * Is called when the job is done.
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    //private final JdbcTemplate jdbcTemplate;

    /** JPA way interacting */
    private final EntityManager em;

    @Autowired
    public JobCompletionNotificationListener(EntityManager em) {
        this.em = em;
    }

/*    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/

    @Override
    @Transactional // need to stick it here
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            // comment after we verified it is worked;
/*            jdbcTemplate.query("SELECT team1, team1, date FROM match",
                    (rs, row) -> "Team 1" + rs.getString(1) + ", Team 2 " + rs.getString(2) + ", Date " + rs.getString(3)
            ).forEach(str -> System.out.println(str));*/


            //).forEach(person -> log.info("Found <" + person + "> in the database."));

            // need to select distinct teams from Match table.
            // The union does work in JPA(does not allow) so we need to have 2 calls means 2 separate queries.
            //select distinct team1 from Match m union select distinct team2 from Match

            Map<String, Team> teamData = new HashMap<>();

            // JPA allows to do query from Object

            // Business logic
            // retrieve data from Match table, do some calculations, set each Team object with data into Map
            // and at the end save to the newly created table team
            em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class)
                .getResultList()
                    .stream()
                    .map(e -> new Team((String) e[0], (long) e[1]))
                    .forEach(team -> teamData.put(((Team) team).getTeamName(), team));


            // Now select unique teams from team2 column and if team is not in teamData map
            em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e ->  {
                        Team team = teamData.get((String)e[0]);
                        // increment total matches
                        team.setTotalMatches(team.getTotalMatches() + (long) e[1]); // add matches in each team object
                        // still here is one small bug, if team is in the second position only
                    });

            em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                    .getResultStream()
                    .forEach(e -> {
                        Team team = teamData.get((String)e[0]);
                        if (team != null) team.setTotalWins((long) e[1]);
                    });

            // go over each record in the Map and save into the team table.
            teamData.values().forEach(team -> em.persist(team));
            teamData.values().forEach(team -> System.out.println(team));

            // after that we have a team table with all calculated values

/*            em.createQuery("select t.id, t.teamName, t.totalMatches, t.totalWins from Team t", Object[].class)
                    .getResultStream()
                    .forEach(e -> System.out.println(e));*/

        }
    }
}