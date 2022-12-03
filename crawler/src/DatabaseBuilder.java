import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseBuilder {
    static String localDirPrefix = "E:\\CS209-GithubVisualization\\data\\";
    List<String> repoNames;
    Map<String, Integer> repoIdMap = new HashMap<>();
    Connection connection;

    DatabaseBuilder(List<String> repoNames) throws Exception {
        connection = PostgreSQLJDBC.connect();
        connection.setAutoCommit(false);
        this.repoNames = repoNames;
    }

    public JSONArray getJsonArray(String description) throws IOException {
        description = changeDescriptionToFileName(description);
        Path path = Path.of(localDirPrefix + description + ".txt");
        String str = Files.readString(path);
        return new JSONArray(str);
    }

    public JSONObject getJsonObject(String description) throws IOException {
        description = changeDescriptionToFileName(description);
        Path path = Path.of(localDirPrefix + description + ".txt");
        String str = Files.readString(path);
        return new JSONObject(str);
    }
    public static String changeDescriptionToFileName(String description) {
        return description.replaceAll("/", "-");
    }

    // Checked!
    public void insertRepoInfo() throws SQLException {
        String sql = "INSERT INTO repos (repo_name) values (?);";
        PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        for (String repoName : repoNames) {
            stmt.setString(1, repoName);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                repoIdMap.put(repoName, id);
            }
        }
    }

    public void simpleInsertTimeStamptz(PreparedStatement pstmt) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(pstmt.toString());
    }
    public void insertIssuesInfo(String repoName, JSONArray issuesInfo) throws SQLException {
        int repo_id = repoIdMap.get(repoName);
        String SQL_INSERT = "insert into issues(repo_id, title, user_name, created_at, closed_at, state, comments, message, url) " +
                "values (?,?,?,?,?,?,?,?,?);";
        PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
        for (int i = 0; i < issuesInfo.length(); i++) {
            JSONObject issueInfo = issuesInfo.getJSONObject(i);
            String issueURL = issueInfo.getString("url");
            String title = issueInfo.getString("title");
            String state = issueInfo.getString("state");
            String message;
            try {
                message = issueInfo.getString("body");
            } catch (Exception e) {
                message = null;
            }
            String created_at = issueInfo.getString("created_at");
            String closed_at = state.equals("closed") ? issueInfo.getString("closed_at") : null;
            String user_name = issueInfo.getJSONObject("user").getString("login");
            int comments = issueInfo.getInt("comments");

            stmt.setInt(1, repo_id);
            stmt.setString(2, title);
            stmt.setString(3, user_name);
            stmt.setString(4, created_at);
            stmt.setString(5, closed_at);
            stmt.setBoolean(6, state.equals("open") ? false : true);
            stmt.setInt(7, comments);
            stmt.setString(8, message);
            stmt.setString(9, issueURL);

            // stmt.executeUpdate();
            simpleInsertTimeStamptz(stmt);
        }
    }

    public void insertReleaseInfo(String repoName, JSONArray releaseInfoList) throws SQLException {
        int repo_id = repoIdMap.get(repoName);
        String SQL_INSERT = "insert into releases(repo_id, release_name, published_at, message) values (?, ?, ?, ?);";
        PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
        for (int i = 0; i < releaseInfoList.length(); i++) {
            JSONObject releaseInfo = releaseInfoList.getJSONObject(i);
            String published_at = releaseInfo.getString("published_at");
            String release_name = releaseInfo.getString("name");
            String message = releaseInfo.getString("body");
            stmt.setInt(1, repo_id);
            stmt.setString(2, release_name);
            stmt.setString(3, published_at);
            stmt.setString(4, message);
            // stmt.executeUpdate();
            simpleInsertTimeStamptz(stmt);
        }
    }

    public void insertContributionsInfo(String repoName, JSONArray contributionInfoList) throws SQLException {
        int repo_id = repoIdMap.get(repoName);
        String SQL_INSERT = "insert into contributions(contributor_id, repo_id, commits) " +
                "values(?,?,?);";
        String SQL_QUERY = "select id FROM contributors WHERE name=?";
        PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
        PreparedStatement stmt_query = connection.prepareStatement(SQL_QUERY);
        for (int i = 0; i < contributionInfoList.length(); i++) {
            JSONObject contributionInfo = contributionInfoList.getJSONObject(i);
            String user_name = contributionInfo.getString("login");
            stmt_query.setString(1, user_name);
            ResultSet rs = stmt_query.executeQuery();
            int contributor_id = -1;
            if (rs.next()) {
                contributor_id = rs.getInt("id");
            }
            else continue;
            int commits = contributionInfo.getInt("contributions");

            stmt.setInt(1, contributor_id);
            stmt.setInt(2, repo_id);
            stmt.setInt(3, commits);
            stmt.executeUpdate();
        }
    }

    public void insertCommitsInfo(String repoName, JSONArray commitInfoList) throws SQLException {
        int repo_id = repoIdMap.get(repoName);
        String SQL_INSERT = "insert into commits(repo_id, user_name, message, commit_time) " +
                "values (?,?,?,?);";
        PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
        for (int i = 0; i < commitInfoList.length(); i++) {
            JSONObject commitInfo = commitInfoList.getJSONObject(i);
            String user_name;
            try {
                user_name = commitInfo.getJSONObject("author").getString("login");
            } catch (Exception e) {
                user_name = null;
            }
            String commit_time = commitInfo.getJSONObject("commit")
                    .getJSONObject("committer").getString("date");
            String message = commitInfo.getJSONObject("commit").getString("message");
            stmt.setInt(1, repo_id);
            stmt.setString(2, user_name);
            stmt.setString(3, message);
            stmt.setString(4, commit_time);
            //stmt.executeUpdate();
            simpleInsertTimeStamptz(stmt);
        }
    }


    public void insertContributorsInfo(JSONArray contributorInfoList) throws SQLException {
        String SQL_INSERT = "insert into contributors(name, followers, followings, public_repos) " +
                "values(?,?,?,?);";
        PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
        for (int i = 0; i < contributorInfoList.length(); i++) {
            JSONObject contributorInfo = contributorInfoList.getJSONObject(i);
            String user_name = contributorInfo.getString("login");
            int followers = contributorInfo.getInt("followers");
            int following = contributorInfo.getInt("following");
            int public_repos = contributorInfo.getInt("public_repos");
            stmt.setString(1, user_name);
            stmt.setInt(2, followers);
            stmt.setInt(3, following);
            stmt.setInt(4, public_repos);
            stmt.executeUpdate();
        }
    }

    public JSONArray getIssueInfo(String repoName) throws IOException {
        JSONArray jsonArray = getJsonArray(repoName + "-issues");
        return jsonArray;
    }

    public JSONArray getCommitsInfo(String repoName) throws IOException {
        JSONArray jsonArray = getJsonArray(repoName + "-commits");
        return jsonArray;
    }

    public JSONArray getContributionsInfo(String repoName) throws IOException {
        JSONArray jsonArray = getJsonArray(repoName + "-contributions");
        return jsonArray;
    }

    public JSONArray getReleaseInfo(String repoName) throws IOException {
        JSONArray jsonArray = getJsonArray(repoName + "-releases");
        return jsonArray;
    }

    public void getRepoInfo() throws SQLException {
        String SQL_QUERY = "select id FROM repos WHERE (repo_name = ?)";
        PreparedStatement stmt_query = connection.prepareStatement(SQL_QUERY);
        for(String repoName: repoNames) {
            stmt_query.setString(1, repoName);
            ResultSet rs = stmt_query.executeQuery();
            int repo_id = -1;
            if (rs.next()) {
                repo_id = rs.getInt("id");
                repoIdMap.put(repoName, repo_id);
                System.out.println(repoName + " " + repo_id);
            }
        }
    }

    public void buildDatabase() throws IOException, SQLException {
        //insertRepoInfo();
        getRepoInfo();
        insertContributorsInfo(getTopContributorsInfo());
        for (String repoName : repoNames) {
//            JSONArray releases = getReleaseInfo(repoName);
//            insertReleaseInfo(repoName, releases);
            JSONArray contributions = getContributionsInfo(repoName);
            insertContributionsInfo(repoName, contributions);
//            JSONArray issuesInfo = getIssueInfo(repoName);
//            insertIssuesInfo(repoName, issuesInfo);
//            JSONArray commitsInfo = getCommitsInfo(repoName);
//            insertCommitsInfo(repoName, commitsInfo);
        }
        connection.commit();
    }

    public List<String> getTopContributorNames() throws IOException {
        return Crawler.getTopContributors(repoNames).stream()
                .flatMap(record -> Stream.of(record.getName())).collect(Collectors.toList());
    }

    public JSONArray getTopContributorsInfo() throws IOException {
        List<String> contributorNames = getTopContributorNames();
        JSONArray contributorsInfo = new JSONArray();
        for(String name: contributorNames) {
            JSONObject contributorInfo = getJsonObject(name);
            contributorsInfo.put(contributorInfo);
        }
        return contributorsInfo;
    }

    public static void main(String[] args) throws Exception {
        List<String> repoNames = new ArrayList<>();
        repoNames.add("gsantner/markor");
        repoNames.add("beemdevelopment/Aegis");
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(repoNames);
        databaseBuilder.buildDatabase();
    }
}
