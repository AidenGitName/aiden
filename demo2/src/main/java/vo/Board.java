package vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Board {
    private Long bno;
    private String title;
    private String content;

    private Timestamp date;

}
