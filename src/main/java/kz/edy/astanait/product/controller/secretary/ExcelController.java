package kz.edy.astanait.product.controller.secretary;

import kz.edy.astanait.product.dto.requestDto.secretary.ExcelDtoRequest;
import kz.edy.astanait.product.service.secretary.ExcelService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/excel")
public class ExcelController {
    private final ExcelService excelService;
    private static final String HEADER_VALUE = "attachment; filename=otchet.xlsx";

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/current-page")
    public ResponseEntity<InputStreamResource> getExcelWithAllBlocksCurrentPage(@RequestBody ExcelDtoRequest excelDtoRequest) throws IOException {
        Set<Long> setId = excelService.selectFromFilterForExcelByPage(excelDtoRequest);
        InputStreamResource inputStreamResource = excelService.getExcelInputStreamResource(setId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/all-pages")
    public ResponseEntity<InputStreamResource> getExcelWithAllBlocksAllPages(@RequestBody ExcelDtoRequest excelDtoRequest) throws IOException {
        Set<Long> setId = excelService.selectFromFilterForExcel(excelDtoRequest);
        InputStreamResource inputStreamResource = excelService.getExcelInputStreamResource(setId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, HEADER_VALUE);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK);
    }
}
