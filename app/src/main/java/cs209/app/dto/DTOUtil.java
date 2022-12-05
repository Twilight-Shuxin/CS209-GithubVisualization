package cs209.app.dto;

import cs209.app.model.Issue;
import org.springframework.core.convert.converter.Converter;


public class DTOUtil {
    public static IssueDTO toIssueDTO(Issue issue) {
        return new IssueDTO(
            issue.getTitle(),
            issue.getUserName(),
            issue.getCreateTime(),
            issue.getClosedTime(),
            issue.isStateClosed(),
            issue.getCommentCnt(),
            issue.getMessage(),
            issue.getUrl()
        );
    }
}
