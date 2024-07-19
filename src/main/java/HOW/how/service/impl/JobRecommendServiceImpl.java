package HOW.how.service.impl;

import HOW.how.domain.ApiData;
import HOW.how.domain.JobRecommend;
import HOW.how.domain.Member;
import HOW.how.domain.MemberDetail;
import HOW.how.dto.JobRecommendDTO;
import HOW.how.exception.EntityNotFoundException;
import HOW.how.repository.ApiDataRepository;
import HOW.how.repository.JobRecommendRepository;
import HOW.how.repository.MemberDetailRepository;
import HOW.how.service.GetAuthenticationService;
import HOW.how.service.JobRecommendService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobRecommendServiceImpl implements JobRecommendService {

    private final JobRecommendRepository jobRecommendRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final ApiDataRepository apiDataRepository;
    private final GetAuthenticationService getAuthenticationService;
    private static final String PYTHON_SCRIPT_PATH = "src/main/resources/python/Recommend/recommendJobs.py";
    private static final String JOB_TITLES_PATH = "src/main/resources/jsonFile/job_title.json";

    @Override
    public JobRecommend recommendJobs() {
        try{
            Member member = getAuthenticationService.getAuthentication();
            Optional<MemberDetail> optionalMemberDetail = memberDetailRepository.findByMemberId(member);

            updateJobTitles();

            if(optionalMemberDetail.isPresent()){
                MemberDetail memberDetail = optionalMemberDetail.get();

                //임시 사용자정보파일 생성
                String userInfo = new ObjectMapper().writeValueAsString(memberDetail);
                Path tempDir = Paths.get("src/main/resources/jsonFile");
                Path tempUserInfoJsonFile = Files.createTempFile(tempDir,"user_info_", ".json");
                Files.writeString(tempUserInfoJsonFile, userInfo);

                //임시 회사정보파일 생성
                List<ApiData> apiDataList = apiDataRepository.findAll();
                String companyInfos = new ObjectMapper().writeValueAsString(apiDataList);
                Path tempDir_c = Paths.get("src/main/resources/jsonFile");
                Path tempCompanyJsonFile = Files.createTempFile(tempDir_c, "company_info_", ".json");
                Files.writeString(tempCompanyJsonFile, companyInfos);

                List<Map<String,Object>> recommendedJobs = executePythonScript(tempUserInfoJsonFile.toString(), tempCompanyJsonFile.toString());

                Files.delete(tempUserInfoJsonFile);
                Files.delete(tempCompanyJsonFile);

                JobRecommend jobRecommend = new JobRecommend();
                jobRecommend.setMember(member);
                jobRecommend.setCompanyInfos(recommendedJobs);

                jobRecommendRepository.save(jobRecommend);

                return jobRecommend;
            } else {
                throw new EntityNotFoundException("MemberDetail not found for MemberId: " + member.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JobRecommendDTO getJobRecommends() {
        Member member = getAuthenticationService.getAuthentication();
        Optional<JobRecommend> optionalJobRecommend = jobRecommendRepository.findByMember(member);
        if(optionalJobRecommend.isPresent()){
            JobRecommend jobRecommend = optionalJobRecommend.get();
            JobRecommendDTO jobRecommendDTO = new JobRecommendDTO();
            jobRecommendDTO.setId(jobRecommend.getId());
            jobRecommendDTO.setCompanyInfo(jobRecommend.getCompanyInfos());
            return jobRecommendDTO;
        }else {
            throw new EntityNotFoundException("JobRecommend not found for MemberId: " + member.getId());
        }
    }


    private List<Map<String, Object>> executePythonScript(String tempUserInfoJsonFilePath, String tempCompanyJsonFile) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python",PYTHON_SCRIPT_PATH,tempUserInfoJsonFilePath,tempCompanyJsonFile,JOB_TITLES_PATH);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> response = mapper.readValue(result.toString(), new TypeReference<List<Map<String, Object>>>() {});
        if (!response.isEmpty() && "error".equals(response.get(0).get("status"))) {
            throw new RuntimeException("Python script error: " + response.get(0).get("message"));
        }

        return response;
    }

    private void updateJobTitles() throws Exception {
        // 데이터베이스에서 ApiData 엔티티 로드
        List<ApiData> apiDataList = apiDataRepository.findAll();

        // 현재 job_titles.json 파일 로드
        Set<String> jobTitles = new HashSet<>();
        Path jobTitlesPath = Paths.get(JOB_TITLES_PATH);
        ObjectMapper objectMapper = new ObjectMapper();

        // job_titles.json 파일이 존재하지 않거나 비어있는 경우 처리
        if (Files.exists(jobTitlesPath) && Files.size(jobTitlesPath) > 0) {
            jobTitles.addAll(objectMapper.readValue(jobTitlesPath.toFile(), new TypeReference<Set<String>>() {}));
        }

        // ApiData 엔티티에서 jobNm 필드 추출 및 업데이트
        for (ApiData apiData : apiDataList) {
            Map<String, Object> data = apiData.getData();
            String jobNm = (String) data.get("jobNm");
            if (jobNm != null) {
                jobTitles.add(jobNm);
            }
        }

        // job_titles.json 파일 업데이트
        objectMapper.writeValue(jobTitlesPath.toFile(), jobTitles);
    }

}
