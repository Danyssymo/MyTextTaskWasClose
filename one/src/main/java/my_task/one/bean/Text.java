package my_task.one.bean;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "text_table")
@Data
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String value;
    @Enumerated(EnumType.STRING)
    private Lang lang;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "fixed_text")
    private String fixedValue;
    public Text(){}

}
