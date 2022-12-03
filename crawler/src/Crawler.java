import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

import static java.lang.Math.min;

class ContributorSortingInfo {
	String str, login_name;
	Integer number;

	ContributorSortingInfo(String a, String name, Integer b) {
		str = a;
		login_name = name;
		number = b;
	}

	int getInt() {
		return number;
	}
	String getUrl() {
		return str;
	}
	String getName() {
		return login_name;
	}
}

public class Crawler {
	static String githubBaseUrl = "https://api.github.com";
	static String localDirPrefix = "E:\\CS209-GithubVisualization\\data\\";
	List<String> repoNames;
	static int totalCnt = 0;

	Crawler(List<String> repoNames) throws Exception {
		this.repoNames = repoNames;
	}

	public static JSONArray getJsonArray(URL url) throws IOException {
		String json = IOUtils.toString(url, Charset.forName("UTF-8"));
		totalCnt += 1;
		return new JSONArray(json);
	}

	public static JSONObject getJsonObject(URL url) throws IOException {
		String json = IOUtils.toString(url, Charset.forName("UTF-8"));
		totalCnt += 1;
		return new JSONObject(json);
	}

	public static JSONArray extendJsonArray(JSONArray jsonArray1, JSONArray jsonArray2) {
		for (int i = 0; i < jsonArray2.length(); i++) {
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
		} while (currentJSONArray.length() == 100);
		return resultJSONArray;
	}
	public JSONArray getInfo(String baseURL, String requirement) throws IOException {
		int id = 1;
		JSONArray currentJSONArray;
		JSONArray resultJSONArray = new JSONArray();
		do {
			URL url = new URL(baseURL + "?per_page=100&page=" + id + "&" + requirement
			);
			currentJSONArray = getJsonArray(url);
			resultJSONArray = extendJsonArray(resultJSONArray, currentJSONArray);
			id += 1;
		} while (currentJSONArray.length() == 100);
		return resultJSONArray;
	}

	public JSONObject getInfoObject(String baseURL) throws IOException {
		URL url = new URL(baseURL);
		return getJsonObject(url);
	}

	public static String changeDescriptionToFileName(String description) {
		return description.replaceAll("/", "-");
	}

	public static void writeToFile(JSONArray jsonArray, String description) throws FileNotFoundException {
		description = changeDescriptionToFileName(description);
		System.out.println(description);
		PrintWriter out = new PrintWriter(localDirPrefix + description + ".txt");
		out.print(jsonArray.toString());
		out.close();
	}

	public static JSONArray readFromFile(String description) throws IOException {
		description = changeDescriptionToFileName(description);
		Path path = Path.of(localDirPrefix + description + ".txt");
		String str = Files.readString(path);
		return new JSONArray(str);
	}

	public void writeToFile(JSONObject jsonObject, String description) throws FileNotFoundException {
		String saved = description;
		description = changeDescriptionToFileName(description);
		System.out.println(saved + " " + description);
		PrintWriter out = new PrintWriter(localDirPrefix + description + ".txt");
		out.print(jsonObject.toString());
		out.close();
	}

	public JSONArray getIssueInfo(String repoName) throws IOException {
		String baseURL = githubBaseUrl + "/repos/" + repoName + "/issues";
		JSONArray jsonArray = getInfo(baseURL, "state=closed");
		jsonArray = extendJsonArray(jsonArray, getInfo(baseURL, "state=open"));
		return jsonArray;
	}

	public JSONArray getCommitsInfo(String repoName) throws IOException {
		String baseURL = githubBaseUrl + "/repos/" + repoName + "/commits";
		JSONArray jsonArray = getInfo(baseURL);
		return jsonArray;
	}

	public JSONArray getContributionsInfo(String repoName) throws IOException {
		String baseURL = githubBaseUrl + "/repos/" + repoName + "/contributors";
		JSONArray jsonArray = getInfo(baseURL);
		return jsonArray;
	}

	public JSONArray getReleaseInfo(String repoName) throws IOException {
		String baseURL = githubBaseUrl + "/repos/" + repoName + "/releases";
		JSONArray jsonArray = getInfo(baseURL);
		return jsonArray;
	}
	public void getAllData() throws IOException, SQLException {
		for(String repoName : repoNames) {
			JSONArray releases = getReleaseInfo(repoName);
			writeToFile(releases, repoName + "-releases");
			JSONArray contributions = getContributionsInfo(repoName);
			writeToFile(contributions, repoName + "-contributions");
			JSONArray issues = getIssueInfo(repoName);
			writeToFile(issues, repoName + "-issues");
			JSONArray commits = getCommitsInfo(repoName);
			writeToFile(commits, repoName + "-commits");
		}
	}

	public static List<ContributorSortingInfo> getTopContributors(List<String> repoNames) throws IOException {
		Set<ContributorSortingInfo> urlSet = new HashSet<>();
		for(String repoName : repoNames) {
			JSONArray contributions = readFromFile(repoName + "-contributions");
			List<ContributorSortingInfo> contributorPairs = new ArrayList<>();
			for(int i = 0; i < contributions.length(); i ++) {
				JSONObject contribution = contributions.getJSONObject(i);
				String user_name = contribution.getString("login");
				String url = contribution.getString("url");
				int commits = contribution.getInt("contributions");
				contributorPairs.add(new ContributorSortingInfo(url, user_name, commits));
			}
			Collections.sort(contributorPairs, Comparator.comparingInt(ContributorSortingInfo::getInt).reversed());
			for(int i = 0; i < min(15, contributorPairs.size()); i ++) {
				urlSet.add(contributorPairs.get(i));
			}
		}
		return new ArrayList<>(urlSet);
	}

	public void getTopContributorsInfo() throws IOException {
		List<ContributorSortingInfo> contributors = getTopContributors(repoNames);
		for(ContributorSortingInfo contributor: contributors) {
			JSONObject object = getJsonObject(new URL(contributor.getUrl()));
			writeToFile(object, contributor.getName());
		}
	}

	public static void main(String[] args) throws Exception {
		List<String> repoNames = new ArrayList<>();
		repoNames.add("gsantner/markor");
		repoNames.add("beemdevelopment/Aegis");
		Crawler crawler = new Crawler(repoNames);
		//crawler.getAllData();
		crawler.getTopContributorsInfo();
		System.out.println("Total grab counts: " + totalCnt);
	}
}
