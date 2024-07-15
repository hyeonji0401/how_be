package HOW.how.service.impl;

import HOW.how.domain.ApiData;
import HOW.how.repository.ApiDataRepository;
import HOW.how.service.ApiDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApiDataServiceImpl implements ApiDataService {

    private final ApiDataRepository apiDataRepository;

    @Autowired
    public ApiDataServiceImpl(ApiDataRepository apiDataRepository) {
        this.apiDataRepository = apiDataRepository;
    }

    @Override
    public List<ApiData> saveApiData() {
        String scriptPath = "src/main/resources/python/XMLToJSON/XMLToJSON.py";
        String jsonFilePath = "src/main/resources/jsonFile/apiJSON.json";
        List<ApiData> apiDataList = new ArrayList<>();

        executePythonFile(scriptPath);

        readJsonFile(jsonFilePath, apiDataList);

        return apiDataRepository.saveAll(apiDataList);
    }

    private void readJsonFile(String jsonFilePath, List<ApiData> apiDataList) {
        try (BufferedReader br = new BufferedReader(new FileReader(jsonFilePath))) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> dataList = objectMapper.readValue(br, List.class);
            for (Map<String, Object> datamap : dataList) {
                ApiData apiData = new ApiData();
                apiData.setData(datamap);
                apiDataList.add(apiData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executePythonFile(String scriptPath) {
        String pythonInterpreter = "python"; // Windows 환경에서는 보통 python.exe
        try {
            Path filePath = Paths.get(scriptPath);
            ProcessBuilder pb = new ProcessBuilder(pythonInterpreter, filePath.toString());
            Process process = pb.start();

            // 파이썬 출력 및 오류 처리
            StringBuilder output = new StringBuilder();
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                output.append(line).append("\n");
            }
            while ((line = error.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Error executing file: " + filePath.getFileName());
                System.out.println(output);
            } else {
                System.out.println("Successfully executed file: " + filePath.getFileName());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}