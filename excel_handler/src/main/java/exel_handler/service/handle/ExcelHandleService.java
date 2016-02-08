package exel_handler.service.handle;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * @author azelentsov
 */
public class ExcelHandleService implements HandleService {
    public static final String JOB_ID = "jobId";
    public static final String RESOURCE = "resource";

    private JobLauncher jobLauncher;
    private Job job;

    private SecureRandom secureRandom;

    public ExcelHandleService(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.secureRandom = new SecureRandom();
    }

    @Override
    public void handleFile(String filePath) throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String,JobParameter> parameters = new HashMap<>();
        parameters.put(JOB_ID, new JobParameter(secureRandom.nextLong()));
        parameters.put(RESOURCE, new JobParameter(filePath));
        JobParameters jobParameters = new JobParameters(parameters);

        jobLauncher.run(job, jobParameters);
    }
}
