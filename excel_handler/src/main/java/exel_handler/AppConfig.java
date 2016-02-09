package exel_handler;

import exel_handler.excel.mapping.EntryRowMapper;
import exel_handler.excel.mapping.RowMapper;
import exel_handler.excel.reader.PoiItemReader;
import exel_handler.model.Entry;
import exel_handler.model.EntryRepository;
import exel_handler.service.handle.ExcelHandleService;
import exel_handler.service.handle.HandleService;
import exel_handler.service.upload.ExcelUploadService;
import exel_handler.service.upload.UploadService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

/**
 * @author azelentsov
 */
@Configuration
@ComponentScan
public class AppConfig {
    @Bean
    @StepScope
    public PoiItemReader<Entry> excelReader(@Value("#{jobParameters['resource']}") String resource) {
        PoiItemReader<Entry> reader = new PoiItemReader<>();
        reader.setRowMapper(rowMapper());
        if(resource != null) {
            reader.setResource(new PathResource(resource));
        }
        return reader;
    }

    @Bean
    public RowMapper<Entry> rowMapper() {
        return new EntryRowMapper();
    }

    @Bean
    public ItemWriter<Entry> entryWriter(EntryRepository entryRepository){
        RepositoryItemWriter<Entry> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(entryRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Job importEntryJob(JobBuilderFactory jobs, Step step) {
        return jobs.get("importEntryJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory, ItemReader<Entry> reader,
                     ItemWriter<Entry> writer) {
        return stepBuilderFactory.get("step")
                .<Entry, Entry> chunk(10)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        return jobLauncher;
    }

    @Bean
    public UploadService uploadService(HandleService handleService) {
        return new ExcelUploadService(handleService);
    }

    @Bean
    public HandleService handleService(JobLauncher jobLauncher, Job job) {
        return new ExcelHandleService(jobLauncher, job);
    }

}
