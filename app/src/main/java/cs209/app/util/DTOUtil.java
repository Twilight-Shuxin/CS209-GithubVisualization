package cs209.app.util;

import cs209.app.dto.*;
import cs209.app.model.*;
import org.springframework.core.convert.converter.Converter;

public class DTOUtil {
    public static IssueDTO toIssueDTO(Issue issue) {
        return new IssueDTO(
                issue.getId(),
                issue.getRepo().getId(),
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

    public static ReleaseDTO toReleaseDTO(Release release) {
        return new ReleaseDTO(
                release.getId(),
                release.getRepo().getId(),
                release.getReleaseName(),
                release.getPublishTime(),
                release.getMessage()
        );
    }

    public static ContributorDTO toContributorDTO(Contributor contributor) {
        return new ContributorDTO(
                contributor.getId(),
                contributor.getName(),
                contributor.getFollowerCnt(),
                contributor.getFollowingCnt(),
                contributor.getPublicRepoCnt()
        );
    }

    public static ContributionDTO toContributionDTO(Contribution contribution) {
        return new ContributionDTO(
                contribution.getId(),
                contribution.getRepo().getId(),
                contribution.getContributor().getId(),
                contribution.getCommitCnt()
        );
    }

    public static ContributorInRepoDTO toContributorInRepoDTO(Contribution contribution) {
        Contributor contributor = contribution.getContributor();
        return new ContributorInRepoDTO(
                contributor.getId(),
                contributor.getName(),
                contribution.getRepo().getId(),
                contribution.getCommitCnt(),
                contributor.getFollowerCnt(),
                contributor.getFollowingCnt(),
                contributor.getPublicRepoCnt()
        );
    }

    public static CommitDTO toCommitDTO(Commit commit) {
        return new CommitDTO(
                commit.getId(),
                commit.getRepo().getId(),
                commit.getContributorId(),
                commit.getMessage(),
                commit.getCommitTime()
        );
    }

    public static WordCntDTO toWordCntDTO(WordCnt wordCnt) {
        return new WordCntDTO(
                wordCnt.getId(),
                wordCnt.getRepo().getId(),
                wordCnt.getWord(),
                wordCnt.getCnt()
        );
    }

    public static String getWeekdayName(int weekday) {
        String weekdayStr;
        switch (weekday) {
            case 0:
                weekdayStr = "Monday";
                break;
            case 1:
                weekdayStr = "Tuesday";
                break;
            case 2:
                weekdayStr = "Wednesday";
                break;
            case 3:
                weekdayStr = "Thursday";
                break;
            case 4:
                weekdayStr = "Friday";
                break;
            case 5:
                weekdayStr = "Saturday";
                break;
            default:
                weekdayStr = "Sunday";
        }
        return weekdayStr;
    }
}
