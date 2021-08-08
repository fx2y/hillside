package cyou.ithan.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table("author")
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
}
