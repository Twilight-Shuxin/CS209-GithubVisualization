package cs209.app.dto;


import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class IssueDTO implements Serializable {
    String title;
    String userName;
    Timestamp createTime;
    Timestamp closedTime;
    boolean stateClosed;
    int commentCnt;
    String message;
    String url;
    IssueDTO(
             String title,
             String userName,
             Timestamp createTime,
             Timestamp closedTime,
             boolean stateClosed,
             int commentCnt,
             String message,
             String url
    ) {
        this.title = title;
        this.userName = userName;
        this.createTime = createTime;
        this.closedTime = closedTime;
        this.stateClosed = stateClosed;
        this.commentCnt = commentCnt;
        this.message = message;
        this.url = url;
    }

}
