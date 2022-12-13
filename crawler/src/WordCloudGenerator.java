import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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

public class WordCloudGenerator {
    static String localDirPrefix = "E:\\CS209-GithubVisualization\\data\\";
    List<String> repoNames;
    Map<String, Integer> repoIdMap = new HashMap<>();
    Connection connection;
    List<String> corpus = new ArrayList<>();

    class WordCountPair {
        String word;
        int cnt;
        WordCountPair(String word, int cnt) {
            this.word = word;
            this.cnt = cnt;
        }
    }

    WordCloudGenerator(List<String> repoNames, File corpusFile) throws Exception {
        connection = PostgreSQLJDBC.connect();
        connection.setAutoCommit(false);
        this.repoNames = repoNames;
        getRepoInfo();
        Scanner fileReader = new Scanner(corpusFile);
        while(fileReader.hasNextLine()) {
            String word = fileReader.nextLine();
            if(word.length() <= 2 && word != "id" && word != "ui")
                continue;
            if(word == "and" || word == "not" || word == "with")
                continue;
            corpus.add(word);
        }
        System.out.println(corpus.size());
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

    public JSONArray getIssueInfo(String repoName) throws IOException {
        JSONArray jsonArray = getJsonArray(repoName + "-issues");
        return jsonArray;
    }

    public JSONArray getCommitsInfo(String repoName) throws IOException {
        JSONArray jsonArray = getJsonArray(repoName + "-commits");
        return jsonArray;
    }

    public void getRepoInfo() throws SQLException {
        String SQL_QUERY = "select id FROM repos WHERE (repo_name = ?)";
        PreparedStatement stmt_query = connection.prepareStatement(SQL_QUERY);
        for(String repoName: repoNames) {
            stmt_query.setString(1, changeDescriptionToFileName(repoName));
            ResultSet rs = stmt_query.executeQuery();
            int repo_id = -1;
            if (rs.next()) {
                repo_id = rs.getInt("id");
                repoIdMap.put(repoName, repo_id);
                System.out.println(repoName + " " + repo_id);
            }
        }
    }

    public void getWordCloudCorpus() throws SQLException, IOException {
        for (String repoName : repoNames) {
            String description = changeDescriptionToFileName(repoName);
            PrintWriter out = new PrintWriter(localDirPrefix + "cloud_text_" + description + ".txt");
            JSONArray issuesInfo = getIssueInfo(repoName);
            for(int i = 0; i < issuesInfo.length(); i ++) {
                JSONObject issueInfo = issuesInfo.getJSONObject(i);
                try {
                    out.println(issueInfo.getString("title"));
                } catch(Exception e) {};
            }
            out.close();
        }
    }

    public void getWordCloud() throws IOException, SQLException {
        String SQL_INSERT = "insert into word_counts(repo_id, word, cnt) " +
                "values(?,?,?);";
        PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
        for (String repoName : repoNames) {
            int repo_id = repoIdMap.get(repoName);
            JSONArray issuesInfo = getIssueInfo(repoName);
            StringBuilder allIssueTitlesBuilder = new StringBuilder();
            for(int i = 0; i < issuesInfo.length(); i ++) {
                JSONObject issueInfo = issuesInfo.getJSONObject(i);
                String title;
                try {
                    title = issueInfo.getString("title");
                } catch(Exception e) {
                    continue;
                };
                allIssueTitlesBuilder.append(" " + title);
            };
            String allIssueTitles = allIssueTitlesBuilder.toString();
            for (String word : corpus) {
                int cnt = StringUtils.countMatches(allIssueTitles,word);
                //System.out.println(word + " " + cnt);
                if(cnt >= 2) {
                    stmt.setInt(1, repo_id);
                    stmt.setString(2, word);
                    stmt.setInt(3, cnt);
                    stmt.executeUpdate();
                }
            }
        }
        connection.commit();
    }

    public static void main(String[] args) throws Exception {
        List<String> repoNames = new ArrayList<>();
        repoNames.add("gsantner/markor");
        repoNames.add("beemdevelopment/Aegis");
        repoNames.add("LibrePDF/OpenPDF");

        File corpusFile = new File(localDirPrefix + "corpus.txt");
        WordCloudGenerator wordCloudGenerator = new WordCloudGenerator(repoNames, corpusFile);
        //wordCloudGenerator.getWordCloudCorpus();
        wordCloudGenerator.getWordCloud();
    }
}
