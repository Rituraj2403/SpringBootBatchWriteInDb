package com.springboot.batch.writeindb.config;

import com.com.springboot.batch.writeindb.model.Employee;
import com.com.springboot.batch.writeindb.model.EmployeeDTO;
import com.springboot.batch.writeindb.listener.JobListener;
import com.springboot.batch.writeindb.processor.EmployeeProcessor;
import com.springboot.batch.writeindb.util.CourseUtils;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

	
    @Bean
    @StepScope
    public JsonItemReader<Employee> reader(@Value("#{jobParameters['empPath']}") String empPath) {
        Resource empResource = CourseUtils.getFileResource(empPath);

        return new JsonItemReaderBuilder<Employee>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Employee.class))
                .resource(empResource)
                .name("jsonItemReader")
                .build();
    }
    @Bean
    public EmployeeProcessor processor() {
        return new EmployeeProcessor(); 
    }

    @Bean
    public JdbcBatchItemWriter<EmployeeDTO> writer() {
        JdbcBatchItemWriter<EmployeeDTO> writer = new JdbcBatchItemWriter<EmployeeDTO>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO employee (firstName,lastName,companyName) " +
                "VALUES (:firstName, :lastName,:companyName)");
        writer.setDataSource(dataSource); 
        return writer;
    }

    @Bean
    public Job importUserJob(JobListener listener) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Employee, EmployeeDTO> chunk(10) 
                .reader(reader(""))   // read from JSON File (employee.json) and write to Employee POJO
                .processor(processor()) // read from Employee POJO  and write To Employee DTO using above chunk
                .writer(writer())      // read from EmployeeDTO and write to  Database Table Employee
                .build();
    }

}
