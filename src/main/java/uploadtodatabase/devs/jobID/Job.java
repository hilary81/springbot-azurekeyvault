package uploadtodatabase.devs.jobID;



import javax.persistence.*;



@Entity
@Table(name = "job2")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="file_duration")
    private Double file_duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFile_duration() {
        return file_duration;
    }

    public void setFile_duration(Double file_duration) {
        this.file_duration = file_duration;
    }
}
