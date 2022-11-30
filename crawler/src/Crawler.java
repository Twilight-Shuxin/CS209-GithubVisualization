import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.*;

public class Crawler {
	Map<String, String> userMap = new HashMap<>();
	static String githubBaseUrl = "https://api.github.com";
	List<String> repoNames = new ArrayList<>();
	Map<String, Integer> repoIdMap = new HashMap<>();
	Connection connection;
	Map<String, String> contributorMap = new HashMap<>();

	Crawler() throws Exception {
		connection = PostgreSQLJDBC.connect();
		connection.setAutoCommit(false);
	}

	public static JSONArray getJsonArray(URL url) throws IOException {
		String json = IOUtils.toString(url, Charset.forName("UTF-8"));
		return new JSONArray(json);
	}

	public static JSONObject getJsonObject(URL url) throws IOException {
		String json = IOUtils.toString(url, Charset.forName("UTF-8"));
		return new JSONObject(json);
	}

	public static JSONArray extendJsonArray(JSONArray jsonArray1, JSONArray jsonArray2) {
		for(int i = 0; i < jsonArray2.length(); i ++) {
			JSONObject object = jsonArray2.getJSONObject(i);
			jsonArray1.put(object);
		}
		return jsonArray1;
	}

	public JSONArray getInfo(String baseURL) throws IOException {
		int id = 1;
		JSONArray currentJSONArray;
		JSONArray resultJSONArray = new JSONArray();
		do {
			URL url = new URL(baseURL + "?per_page=100&page=" + id
			);
			currentJSONArray = getJsonArray(url);
			resultJSONArray = extendJsonArray(resultJSONArray, currentJSONArray);
			id += 1;
		} while(currentJSONArray.length() == 100);
		return resultJSONArray;
	}

	public void InsertRepoInfo() throws SQLException {
		Statement stmt = null;
		for(String repoName : repoNames) {
			stmt = connection.createStatement();
			String sql = "INSERT INTO repos (repo_name) "
					+ "VALUES (" + repoName + ");";
			stmt.executeQuery(sql);
			sql = "SELECT FROM repos (id) "
					+ "WHERE repo_name == " + repoName + ";";
			ResultSet rs = stmt.executeQuery( sql );
			while ( rs.next() ) {
				int id = rs.getInt("id");
				repoIdMap.put(repoName, id);
				break;
			}
		}
		return;
	}

	public void InsertIssueInfo(String repoName, JSONArray issueInfoList) throws SQLException {
		int repo_id = repoIdMap.get(repoName);
		String SQL_INSERT = "insert into issues(repo_id, title, user_name, created_at, closed_at, state, comments, message, url) " +
				"values (?,?,?,?,?,?,?,?,?);";
		PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
		for(int i = 0; i < issueInfoList.length(); i ++) {
			JSONObject issueInfo = issueInfoList.getJSONObject(i);
			String issueURL = issueInfo.getString("url");
			String title = issueInfo.getString("title");
			String state = issueInfo.getString("state");
			String message = issueInfo.getString("body");
			String created_at = issueInfo.getString("created_at");
			String closed_at = issueInfo.getString("closed_at");
			String user_name = issueInfo.getJSONObject("user").getString("login");
			int comments = issueInfo.getInt("comments");

			stmt.setInt(1, repo_id);
			stmt.setString(2, title);
			stmt.setString(3, user_name);
			stmt.setString(4, created_at);
			stmt.setString(5, closed_at);
			stmt.setString(6,state);
			stmt.setInt(7, comments);
			stmt.setString(8, message);
			stmt.setString(9, issueURL);

			stmt.executeQuery();
		}
	}

	public void InsertReleaseInfo(String repoName, JSONArray releaseInfoList) throws SQLException {
		int repo_id = repoIdMap.get(repoName);
		String SQL_INSERT = "insert into releases(repo_id, release_name, published_at, message) values (?, ?, ?, ?);";
		PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
		for(int i = 0; i < releaseInfoList.length(); i ++) {
			JSONObject releaseInfo = releaseInfoList.getJSONObject(i);
			String published_at = releaseInfo.getString("published_at");
			String release_name = releaseInfo.getString("name");
			String message = releaseInfo.getString("body");
			stmt.setInt(1, repo_id);
			stmt.setString(2, release_name);
			stmt.setString(3, published_at);
			stmt.setString(4, message);
			stmt.executeQuery();
		}
	}

	public void InsertContributionsInfo(String repoName, JSONArray contributionInfoList) throws SQLException {
		int repo_id = repoIdMap.get(repoName);
		String SQL_INSERT = "insert into contributions(user_name, repo_id, commits) " +
				"values(?,?,?);";
		PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
		for(int i = 0; i < contributionInfoList.length(); i ++) {
			JSONObject contributionInfo = contributionInfoList.getJSONObject(i);
			String user_name = contributionInfo.getString("login");
			String url = contributionInfo.getString("url");
			int commits = contributionInfo.getInt("commits");
			stmt.setInt(1, repo_id);
			stmt.setString(2, user_name);
			stmt.setInt(3, commits);
			contributorMap.put(user_name, url);
			stmt.executeQuery();
		}
	}

	public void InsertContributorInfo(JSONObject contributorInfo) throws SQLException {
		String SQL_INSERT = "insert into contributors(user_name, followers, followings) " +
				"values(?,?,?);";
		PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
		String user_name = contributorInfo.getString("login");
		int followers = contributorInfo.getInt("followers");
		int following = contributorInfo.getInt("following");
		int public_repos = contributorInfo.getInt("public_repos");
		stmt.setString(1, user_name);
		stmt.setInt(2, followers);
		stmt.setInt(3, following);
		stmt.setInt(4, public_repos);
		stmt.executeQuery();
	}

	public void InsertCommitsInfo(String repoName, JSONArray commitInfoList) throws SQLException {
		int repo_id = repoIdMap.get(repoName);
		String SQL_INSERT = "insert into commits(repo_id, user_name, message, commit_time) " +
				"values (?,?,?,?);";
		PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
		for(int i = 0; i < commitInfoList.length(); i ++) {
			JSONObject contributionInfo = commitInfoList.getJSONObject(i);
			String user_name = contributionInfo.getString("login");
			String url = contributionInfo.getString("url");
			int commits = contributionInfo.getInt("commits");
			stmt.setInt(1, repo_id);
			stmt.setString(2, user_name);
			stmt.setInt(3, commits);
			contributorMap.put(user_name, url);
			stmt.executeQuery();
		}
	}

	public JSONArray getIssueInfo(String repoName) throws IOException {
		String baseURL = githubBaseUrl + "/repos/" + repoName + "/issues";
		JSONArray jsonArray = getInfo(baseURL);
		return jsonArray;
	}

	public JSONArray getContributorsInfo(String repoName) throws IOException {
		String baseURL = githubBaseUrl + "/repos/" + repoName + "/contributors";
		JSONArray jsonArray = getInfo(baseURL);
		return jsonArray;
	}

	public static void main(String[] args) throws Exception {
		Crawler crawler = new Crawler();
		//System.out.println(Timestamp.valueOf("2022-11-21T01:28:17Z"));
		String SQL_INSERT = "insert into releases(repo_id, release_name, published_at, message) values (?, ?, ?, ?);";
		PreparedStatement stmt = crawler.connection.prepareStatement(SQL_INSERT);
		stmt.setInt(1, 1);
		stmt.setString(2, "haha");
		stmt.setString(3, "2022-11-21T01:28:17Z");
		stmt.setString(4, "message");
		System.out.println(stmt);
		return;
	}
}
