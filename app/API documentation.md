### API documentation

___

#### Repos

- gsantner/markor

- beemdevelopment/Aegis

- LibrePDF/OpenPDF

In database '/' in name of repos is replaced by '-'

- gsantner-markor

- beemdevelopment-Aegis

- LibrePDF-OpenPDF

#### Open APIs

1. `/repo/{repo_name}` Get repository id from repository name

    ```
    /repo/gsantner-markor
    
    ---->
    
    {
        "id": 1,
        "repoName": "gsantner-markor"
    }
    ```

2. `/repo/{repo_name}/issue` Get all issue of repo by repo name (paged)

    ```
    /repo/gsantner-markor/issue
    
    ---->
    
    *Example for paged contents, each page contains 100 elements, and by parameter page=x can request content on page x(default is 0). (e.g. /repo/gsantner-markor/issue?page=2); Following will omit paging part and only include single content object.
    
    {
        "content": [
            {
                "id": 1,
                "repoId": 1,
                "title": "links to sections in another dokument",
                "userName": "gitgerardo",
                "createTime": "2022-11-30T20:19:56+08:00",
                "closedTime": "2022-11-30T21:43:49+08:00",
                "stateClosed": true,
                "commentCnt": 4,
                "message": "### ⚠️ This issue respects the following points: ⚠️\n\n- [X] This is a **enhancement/feature request**. Not a [bug or question](https://github.com/gsantner/markor/issues/new/choose).\n- [X] The topic is **not** already reported at [Issues](https://github.com/gsantner/markor/issues?q=#js-issues-search). _(I've searched it)_.\n- [X] Markor **is** up to date. See [Releases](https://github.com/gsantner/markor/tags) for the latest version. Updates are available from [F-Droid](https://f-droid.org/en/packages/net.gsantner.markor/) and GitHub.\n- [X] The wanted feature/enhancement is not present in the latest development version (git master). (Please [download](https://nightly.link/gsantner/markor/workflows/build-android-project/master) and try the test version of Markor, named **Mordor**. Don't worry; Markor and Mordor appear as completely separate applications. You can install both side-by-side, and Markor's settings are not touched. If your desired feature is present, you don't need to open this issue. The change will be part of the next Markor update.)\n\n### Description\n\nI would like to propose a feature: it would nice to have the possibility to right links to sections in another document.\r\nThe format of the link should be: `[Link to Section](another_document.md#section)`\r\n\r\nThank you!\n\n### Information\n\nAndroid version: 13\r\nDevice: oneplus 6\r\nApp Version: latest , f-droid\r\n\n\n### Source\n\nF-Droid\n\n### Format / File type\n\nMarkdown\n\n### Additional info / Log\n\n```shell\n-\n```\n",
                "url": "https://api.github.com/repos/gsantner/markor/issues/1924"
            },
            ...],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageSize": 100,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "last": false,
        "totalElements": 1716,
        "totalPages": 18,
        "size": 100,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 100,
        "empty": false
    ```

3. `/repo/{repo_name}/commit` Get all commits of repo by repo name (paged)

   ```
   /repo/gsantner-markor/commit
   
   ---->
   
   {
       "id": 1,
       "repoId": 1,
       "contributorId": 15,
       "message": "Change default Markor application theme from auto to system, closes #1922, by @gsantner",
       "commit_time": "2022-11-30T21:53:22+08:00"
   }
   
   -----
   
   Params:
   	page: page index
   
       start: starting time
       end: ending time
       (Can only set one of them and the other is automatically unbounded)
   		e.g.: /repo/gsantner-markor/commit?start=2019-11-30T21:53:22%2B08:00&end=2022-11-30T21:53:22%2B08:00 (%2B is url encoding for +)
   	
   	weekday: 0-6 (0: commits on Monday, and so on)
   	(Can either set time interval or weekday)
   		e.g.: /repo/gsantner-markor/commit?weekday=0
   ```

4. `/repo/{repo_name}/contribution` Get all contributions of repo by repo name (paged)

    ```
    /repo/gsantner-markor/contirbution
    
    ---->
    
    {
        "id": 1,
        "contributorId": 1,
        "repoId": 15,
        "commitCnt": 1277
    }
    ```

5. `/repo/{repo_name}/contributor` Get top 15 contributors of repo by repo name sorted in descending order by commit count. (paged)

    ```
    /repo/gsantner-markor/contributor
    
    ---->
    
    {
        "id": 15,
        "name": "gsantner",
        "repoId": 1,
        "commitCnt": 1277,
        "followerCnt": 324,
        "followingCnt": 39,
        "publicRepoCnt": 39
    }
    ```

6. `/repo/{repo_name}/avg_commit_cnt` Get average commits between two releases.

    ```
    /repo/gsantner-markor/avg_commit_cnt
    
    ---->
    
    27
    ```

7. `/repo/{repo_name}/avg_resolve_time` Get average resolve time of issues. 

    ```
    /repo/gsantner-markor/avg_resolve_time
    
    ---->
    
    {
        "type": "interval",
        "value": "0 years 0 mons 35 days 11 hours 0 mins 0.912991 secs",
        "years": 0,
        "months": 0,
        "days": 35,
        "hours": 11,
        "minutes": 0,
        "wholeSeconds": 0,
        "microSeconds": 912991,
        "seconds": 0.912991,
        "null": false
    }
    ```

8. `/repo/{repo_name}/word_cnt` Get count of selected words (mostly nouns) in descending order. (paged)

    ```
    /repo/gsantner-markor/word_cnt
    
    ---->
    
    {
        "id": 500,
        "repoId": 1,
        "word": "file",
        "wordCnt": 298
    }
    ```

    

​		