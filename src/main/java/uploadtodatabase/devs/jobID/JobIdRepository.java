package uploadtodatabase.devs.jobID;

import org.springframework.data.jpa.repository.JpaRepository;
public interface JobIdRepository extends JpaRepository<Job, Long> {
}
