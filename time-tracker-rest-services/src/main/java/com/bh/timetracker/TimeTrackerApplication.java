package com.bh.timetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class TimeTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackerApplication.class, args);
	}
	/*@Bean
    CommandLineRunner init(final TaskServiceImpl taskServiceImpl) {
      
      return new CommandLineRunner() {

        @Override
        public void run(String... arg0) throws Exception {
        	taskServiceImpl.saveTask(new Task());
          
        }
        
      };

    }*/
}
