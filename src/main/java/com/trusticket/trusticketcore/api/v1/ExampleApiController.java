package com.trusticket.trusticketcore.api.v1;

import com.trusticket.trusticketcore.common.ErrorDefineCode;
import com.trusticket.trusticketcore.common.response.CommonResponse;
import com.trusticket.trusticketcore.common.response.SwaggerErrorResponseType;
import com.trusticket.trusticketcore.common.exception.custom.exception.ForbiddenException403;
import com.trusticket.trusticketcore.common.exception.custom.exception.NoSuchElementFoundException404;
import com.trusticket.trusticketcore.common.exception.custom.exception.UnsupportedMediaTypeException415;
import com.trusticket.trusticketcore.config.security.constant.AuthConstant;
import com.trusticket.trusticketcore.example.ExampleRequest;
import com.trusticket.trusticketcore.example.ExampleResponse;
import com.trusticket.trusticketcore.example.ExampleService;
import com.trusticket.trusticketcore.example.ExampleValidationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "EXAM API", description = "예제와 관련된 API") // Swagger Docs : API 이름
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/example")
public class ExampleApiController {

    private final ExampleService exampleService;

    @PostMapping()
    @PreAuthorize(AuthConstant.AUTH_COMMON)
    public CommonResponse<Long> saveExample(
            @Valid @RequestBody ExampleRequest request
    ) {
        Long result = this.exampleService.addExample(request);

        return new CommonResponse(true, HttpStatus.OK, "Example 저장에 성공했습니다", result);
    }

    @GetMapping("/{pathValue}")
    @PreAuthorize(AuthConstant.AUTH_COMMON)
    @ApiResponses(value = {
    })
    public CommonResponse<List<ExampleResponse>> queryExampleByKey(
            // Path Variable 사용 예시 : /api/v1/example/blah
            @Schema(description = "Path Variable 예시", example = "blah")   //Swagger 파라미터 설명
            @PathVariable String pathValue,

            // Query Parameter 사용 예시 : /api/v1/example?paramKeyword=blah
            @Parameter(description = "Parameter 예시", example = "blah") //Swagger 파라미터 설명
            @RequestParam(required = false) String paramValue

    ) {
        List<ExampleResponse> result = this.exampleService.searchExampleByKeyword(pathValue);


        return new CommonResponse(true, HttpStatus.OK, "리스트 조회에 성공했습니다", result);
    }


}
