package HOW.how.controller;

import HOW.how.domain.ApiData;
import HOW.how.service.ApiDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiDataController {

    private final ApiDataService apiDataService;

    @PostMapping("/save")
    public ResponseEntity<List<ApiData>> getApiData() {
        List<ApiData> saveApiDataList = apiDataService.saveApiData();
        return ResponseEntity.ok(saveApiDataList);
    }


}
