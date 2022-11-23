package com.springboot.batch.writeindb;

import java.util.Date;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.springboot.batch.writeindb.util.CourseUtilsDefaultTrigger;

@SpringBootApplication
public class SpringBootBatchJsonExampleApplication {
    //Read from employee.json file and write to Employee Table
	public static void main(String[] args) throws JobExecutionException, InterruptedException {
		ConfigurableApplicationContext appContext = SpringApplication.run(SpringBootBatchJsonExampleApplication.class, args);
		CourseUtilsDefaultTrigger trigger = appContext.getBean(CourseUtilsDefaultTrigger.class);
		JobParametersBuilder builder = new JobParametersBuilder(); 
		
		builder.addParameter("empPath", new JobParameter("classpath:files/employee.json"));
		builder.addDate("date", new Date());
		//trigger Object Internally Call JobBuilderFactory mentioned in SpringBatchConfig Class file and also JobParameters
		trigger.runJobs(builder.toJobParameters());
	}

}
