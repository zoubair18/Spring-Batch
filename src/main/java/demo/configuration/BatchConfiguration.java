package demo.configuration;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import demo.mapper.PersonRowMapper;
import demo.model.Person;
import demo.processor.PersonItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
//	private static final Logger logger = LogManager.getLogger(BatchConfiguration.class);

	@Autowired
	private JobRegistry jobRegistry;
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Bean
	public Job importUserJob() {
		return jobs.get("GeanarateData").incrementer(new RunIdIncrementer()).start(step1()).build();
	}

	@Bean
	public Step step1() {
		return steps.get("StepGenerateData").<Person, Person>chunk(10).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	@Bean(destroyMethod = "")
	public ItemStreamReader<Person> reader() {
		// logger.info("Select data");
		JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<Person>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT first_name, last_name FROM person where person_id in (1,2,3)");
		reader.setRowMapper(new PersonRowMapper());

		return reader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {

		// logger.info(new PersonItemProcessor().toString());
		return new PersonItemProcessor();
	}

	@Bean
	public ItemWriter<Person> writer() {
		// logger.info("write data");
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
		writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
		writer.setDataSource(dataSource);
		return writer;
	}

}
