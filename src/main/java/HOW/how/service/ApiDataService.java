package HOW.how.service;

import HOW.how.domain.ApiData;

import java.util.List;

public interface ApiDataService {
    List<ApiData> saveApiData();
    void executePythonFile(String scriptPath);
}
