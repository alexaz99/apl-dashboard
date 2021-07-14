package alex.webapp.config;

import alex.webapp.data.JobCompletionNotificationListener;
import alex.webapp.data.MatchDataProcessor;
import alex.webapp.data.MatchInput;
import alex.webapp.model.Match;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final static String[] FIELD_NAMES = new String[] {
            "id","city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner","toss_decision","winner","result","result_margin","eliminator","method","umpire1","umpire2"
    };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    /**
     * Define input bean what and where to read from
     * input class MatchInput
     */
    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("personItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(FIELD_NAMES) // read all of them
                //.names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
                    {
                    setTargetType(MatchInput.class);
                }
            }).build();
    }

    /**
     * Define processor bean
     * @return
     */
    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    /**
     * Define a bean which writes to our HSQL database
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO match (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2) VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)")
                .dataSource(dataSource)
                .build();
    }

    /**
     * Create a job
     * @param listener
     * @param step1
     * @return
     */
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
    //public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)// the flow of the job is one step
                .end()
                .build();
    }

    /**
     * THis job has only one step
     * @param writer
     * @return
     */
    @Bean
    public Step step1(JdbcBatchItemWriter<Match> writer) {
        return stepBuilderFactory.get("step1")
                .<MatchInput, Match> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }



}

