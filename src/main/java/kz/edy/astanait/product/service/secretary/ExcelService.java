package kz.edy.astanait.product.service.secretary;

import kz.edy.astanait.product.dto.requestDto.secretary.ExcelDtoRequest;
import kz.edy.astanait.product.dto.responseDto.UserDtoResponse;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.education.ENTDetails;
import kz.edy.astanait.product.model.enrollee.AdmissionInfo;
import kz.edy.astanait.product.model.enrollee.DocumentInfo;
import kz.edy.astanait.product.model.enrollee.EducationalInfo;
import kz.edy.astanait.product.model.enrollee.PersonalInfo;
import kz.edy.astanait.product.repository.entrollee.AdmissionInfoRepository;
import kz.edy.astanait.product.repository.entrollee.DocumentInfoRepository;
import kz.edy.astanait.product.repository.entrollee.EducationalInfoRepository;
import kz.edy.astanait.product.repository.entrollee.PersonalInfoRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ExcelService {
    private final PersonalInfoRepository personalInfoRepository;
    private final DocumentInfoRepository documentInfoRepository;
    private final AdmissionInfoRepository admissionInfoRepository;
    private final EducationalInfoRepository educationalInfoRepository;
    private final DateTimeFormatter dateTimeFormatter;
    private final UserFilterService userFilterService;

    public ExcelService(PersonalInfoRepository personalInfoRepository, DocumentInfoRepository documentInfoRepository, AdmissionInfoRepository admissionInfoRepository, EducationalInfoRepository educationalInfoRepository, UserFilterService userFilterService) {
        this.personalInfoRepository = personalInfoRepository;
        this.documentInfoRepository = documentInfoRepository;
        this.admissionInfoRepository = admissionInfoRepository;
        this.educationalInfoRepository = educationalInfoRepository;
        this.userFilterService = userFilterService;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    public InputStreamResource getExcelInputStreamResource(Set<Long> ids) throws IOException {
        Workbook excel = createForAllBlocks(ids);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            excel.write(out);
            try (ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray())) {
                return new InputStreamResource(in);
            }
        }
    }

    public Workbook createForAllBlocks(Set<Long> abiturIds) {
        Workbook excel = new XSSFWorkbook();

        createPersonalInfoSheet(excel, abiturIds);
        createDocumentsSheet(excel, abiturIds);
        createAdmissionInfoSheet(excel, abiturIds);
        createEducationInfoSheet(excel, abiturIds);

        Iterator<Sheet> iterator = excel.sheetIterator();
        while (iterator.hasNext()) {
            Sheet sheet = iterator.next();
            autoSizeColumns(sheet);
        }

        return excel;
    }

    private void createPersonalInfoSheet(Workbook excel, Set<Long> abiturIds) {
        List<PersonalInfo> personalInfos = personalInfoRepository.findAllByUserIdIn(abiturIds);
        Sheet sheet = excel.createSheet("Личные данные");

        Row firstRow = sheet.createRow(0);
        int firstRowColumnIndex = 0;
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ИИН");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ФИО");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Дата рождения");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Сотовый номер телефона");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Почтовый адрес");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Пол");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Национальность");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Гражданство");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Адрес прописки");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Адрес проживания");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ФИО первого родителя");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Сотовый номер телефона первого родителя");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ФИО второго родителя");
        firstRow.createCell(firstRowColumnIndex).setCellValue("Сотовый номер телефона второго родителя");

        AtomicInteger i = new AtomicInteger(1);
        personalInfos.forEach(personalInfo -> {
            User user = personalInfo.getUser();

            Row r = sheet.createRow(i.getAndIncrement());
            int colInd = 0;
            r.createCell(colInd++).setCellValue(personalInfo.getIin());
            r.createCell(colInd++).setCellValue(getUserFullName(user));
            r.createCell(colInd++).setCellValue(personalInfo.getDateOfBirth() == null ? "" : dateTimeFormatter.format(personalInfo.getDateOfBirth()));
            r.createCell(colInd++).setCellValue(user.getPhoneNumber());
            r.createCell(colInd++).setCellValue(user.getEmail());
            r.createCell(colInd++).setCellValue(personalInfo.getIsMale() == null ? "" : personalInfo.getIsMale() ? "М" : "Ж");
            r.createCell(colInd++).setCellValue(personalInfo.getNationality() == null ? "" : personalInfo.getNationality().getName());
            r.createCell(colInd++).setCellValue(personalInfo.getCitizenship() == null ? "" : personalInfo.getCitizenship().getName());
            r.createCell(colInd++).setCellValue(personalInfo.getResidenceAddress());
            r.createCell(colInd++).setCellValue(personalInfo.getLifeAddress());

            String s = getFullName(personalInfo.getFirstParentSurname(), personalInfo.getFirstParentName(), personalInfo.getFirstParentPatronymic());
            r.createCell(colInd++).setCellValue(s.isBlank() ? "" : s);
            r.createCell(colInd++).setCellValue(personalInfo.getFirstParentPhoneNumber());

            s = getFullName(personalInfo.getSecondParentSurname(), personalInfo.getSecondParentName(), personalInfo.getSecondParentPatronymic());
            r.createCell(colInd++).setCellValue(s.isBlank() ? "" : s);
            r.createCell(colInd).setCellValue(personalInfo.getSecondParentPhoneNumber());
        });
    }

    private void createDocumentsSheet(Workbook excel, Set<Long> abiturIds) {
        List<DocumentInfo> documentInfos = documentInfoRepository.findAllByUserIdIn(abiturIds);

        Sheet sheet = excel.createSheet("Документы");
        Row firstRow = sheet.createRow(0);

        int firstRowColumnIndex = 0;
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ИИН");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ФИО");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Тип документа, удостоверяющий личность");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Другой тип документа, удостоверяющий личность");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Номер документа, удостоверяющего личность");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Дата выдачи документа, удостоверяющего личность");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Срок действия документа, удостоверяющего личность");
        firstRow.createCell(firstRowColumnIndex).setCellValue("Орган, выдавший документ, удостоверяющий личность");

        AtomicInteger i = new AtomicInteger(1);
        documentInfos.forEach(documentInfo -> {
            User user = documentInfo.getUser();
            Row r = sheet.createRow(i.getAndIncrement());
            int colInd = 0;

            r.createCell(colInd++).setCellValue(getUserIin(user));
            r.createCell(colInd++).setCellValue(getUserFullName(user));
            r.createCell(colInd++).setCellValue(documentInfo.getIdentityDocumentType() == null ? "" : documentInfo.getIdentityDocumentType().getName());
            r.createCell(colInd++).setCellValue(documentInfo.getOtherDocumentType());
            r.createCell(colInd++).setCellValue(documentInfo.getIdentityDocumentNumber());
            r.createCell(colInd++).setCellValue(documentInfo.getIdentityDocumentIssueDate() == null ? "" : dateTimeFormatter.format(documentInfo.getIdentityDocumentIssueDate()));
            r.createCell(colInd++).setCellValue(documentInfo.getIdentityDocumentValidityPeriod() == null ? "" : dateTimeFormatter.format(documentInfo.getIdentityDocumentValidityPeriod()));
            r.createCell(colInd).setCellValue(documentInfo.getDocumentIssuingAuthority() == null ? "" : documentInfo.getDocumentIssuingAuthority().getName());
        });
    }

    private void createAdmissionInfoSheet(Workbook excel, Set<Long> abiturIds) {
        List<AdmissionInfo> admissionInfos = admissionInfoRepository.findAllByUserIdIn(abiturIds);
        Sheet sheet = excel.createSheet("Сведения о поступлении");
        Row firstRow = sheet.createRow(0);

        int firstRowColumnIndex = 0;
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ИИН");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ФИО");

        firstRow.createCell(firstRowColumnIndex++).setCellValue("Группа образовательных программ");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Образовательная программа");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Вид экзамена");

        firstRow.createCell(firstRowColumnIndex++).setCellValue("Баллы собеседования");

        firstRow.createCell(firstRowColumnIndex++).setCellValue("ЕНТ баллы: Казахская история");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ЕНТ баллы: Грамотность чтения");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ЕНТ баллы: Математическая грамотность");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Профильный предмет 1");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ЕНТ баллы: Профильный предмет 1");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Профильный предмет 2");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ЕНТ баллы: Профильный предмет 2");

        firstRow.createCell(firstRowColumnIndex++).setCellValue("Творческий экзамен");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Результут творческого экзамена");
        firstRow.createCell(firstRowColumnIndex).setCellValue("Подтверждение владения английским языком");
        firstRow.createCell(firstRowColumnIndex).setCellValue("ИКТ");

        AtomicInteger i = new AtomicInteger(1);
        admissionInfos.forEach(admissionInfo -> {
            User user = admissionInfo.getUser();
            Row r = sheet.createRow(i.getAndIncrement());
            int colInd = 0;

            r.createCell(colInd++).setCellValue(getUserIin(user));
            r.createCell(colInd++).setCellValue(getUserFullName(user));
            r.createCell(colInd++).setCellValue(admissionInfo.getEducationalProgram() == null ? "" :
                    admissionInfo.getEducationalProgram().getEducationalProgramGroup() == null ? "" : admissionInfo.getEducationalProgram().getEducationalProgramGroup().getName());
            r.createCell(colInd++).setCellValue(admissionInfo.getEducationalProgram() == null ? "" : admissionInfo.getEducationalProgram().getName());
            r.createCell(colInd++).setCellValue(admissionInfo.getExamType() == null ? "" : admissionInfo.getExamType().getName());

            r.createCell(colInd++).setCellValue(admissionInfo.getInterviewPoints());

            ENTDetails entDetails = admissionInfo.getEntDetails();
            if (entDetails == null) {
                r.createCell(colInd++).setCellValue("");
                r.createCell(colInd++).setCellValue("");
                r.createCell(colInd++).setCellValue("");
                r.createCell(colInd++).setCellValue("");
                r.createCell(colInd++).setCellValue("");
                r.createCell(colInd++).setCellValue("");
                r.createCell(colInd++).setCellValue("");
            } else {
                r.createCell(colInd++).setCellValue(entDetails.getKazakhstanHistoryPoint() == null ? "" : entDetails.getKazakhstanHistoryPoint().toString());
                r.createCell(colInd++).setCellValue(entDetails.getReadingLiteracyPoint() == null ? "" : entDetails.getReadingLiteracyPoint().toString());
                r.createCell(colInd++).setCellValue(entDetails.getMathematicalLiteracyPoint() == null ? "" : entDetails.getMathematicalLiteracyPoint().toString());

                r.createCell(colInd++).setCellValue(admissionInfo.getEducationalProgram() == null ? "" : admissionInfo.getEducationalProgram().getEducationalProgramGroup() == null ? "" : admissionInfo.getEducationalProgram().getEducationalProgramGroup().getSubject1() == null ? "" : admissionInfo.getEducationalProgram().getEducationalProgramGroup().getSubject1().getTitle());
                r.createCell(colInd++).setCellValue(entDetails.getProfileSubject1Point() == null ? "" : entDetails.getProfileSubject1Point().toString());

                r.createCell(colInd++).setCellValue(admissionInfo.getEducationalProgram() == null ? "" : admissionInfo.getEducationalProgram().getEducationalProgramGroup() == null ? "" : admissionInfo.getEducationalProgram().getEducationalProgramGroup().getSubject2() == null ? "" : admissionInfo.getEducationalProgram().getEducationalProgramGroup().getSubject2().getTitle());
                r.createCell(colInd++).setCellValue(entDetails.getProfileSubject2Point() == null ? "" : entDetails.getProfileSubject2Point().toString());
            }
            if (admissionInfo.getEducationalProgram() != null
                    && admissionInfo.getEducationalProgram().getEducationalProgramGroup() != null
                    && admissionInfo.getEducationalProgram().getEducationalProgramGroup().getIsCreativeExam() != null
            ) {
                r.createCell(colInd++).setCellValue(admissionInfo.getEducationalProgram().getEducationalProgramGroup().getIsCreativeExam() ? "Да" : "Нет");
            } else {
                r.createCell(colInd++).setCellValue("");
            }
            r.createCell(colInd++).setCellValue(admissionInfo.getCreativeExamPoints() == null ? "" : admissionInfo.getCreativeExamPoints());
            r.createCell(colInd).setCellValue(admissionInfo.getEnglishCertificateType() == null ? "" : admissionInfo.getEnglishCertificateType().getName());
            r.createCell(colInd).setCellValue(admissionInfo.getIkt() == null ? "" : admissionInfo.getIkt());
        });
    }

    private void createEducationInfoSheet(Workbook excel, Set<Long> abiturIds) {
        List<EducationalInfo> educationalInfos = educationalInfoRepository.findAllByUserIdIn(abiturIds);
        Sheet sheet = excel.createSheet("Сведения об образовании");
        Row firstRow = sheet.createRow(0);
        firstRow.createCell(0).setCellValue("");

        int firstRowColumnIndex = 0;
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ИИН");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("ФИО");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Вид образовательного учреждения");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Область окончания учебного заведения");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Город окончания учебного заведения");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Название образовательного учреждения");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Серия аттестат о среднем образовании");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Номер свидетельства");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Дата выдачи");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Средний балл аттестата");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Знак отличия");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Наименование документа об образовании");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("№ свидетельства о нострификации");
        firstRow.createCell(firstRowColumnIndex++).setCellValue("Дата свидетельство о нострификации");
        firstRow.createCell(firstRowColumnIndex).setCellValue("Наименование специальности");

        AtomicInteger i = new AtomicInteger(1);
        educationalInfos.forEach(educationalInfo -> {
            User user = educationalInfo.getUser();
            Row r = sheet.createRow(i.getAndIncrement());
            int colInd = 0;

            r.createCell(colInd++).setCellValue(getUserIin(user));
            r.createCell(colInd++).setCellValue(getUserFullName(user));

            r.createCell(colInd++).setCellValue(educationalInfo.getEducationInstitutionType() == null ? "" : educationalInfo.getEducationInstitutionType().getName());

            r.createCell(colInd++).setCellValue(educationalInfo.getKzGraduationLocality() == null ? "" : educationalInfo.getKzGraduationLocality().getRegion().getName());
            r.createCell(colInd++).setCellValue(educationalInfo.getKzGraduationLocality() == null ? "" : educationalInfo.getKzGraduationLocality().getName());
            r.createCell(colInd++).setCellValue(educationalInfo.getEducationalInstitution());
            r.createCell(colInd++).setCellValue(educationalInfo.getKzGraduationCertificateSeries());
            r.createCell(colInd++).setCellValue(educationalInfo.getKzGraduationCertificateNumber());

            r.createCell(colInd++).setCellValue(educationalInfo.getGraduationCertificateIssueDate() == null ? "" : dateTimeFormatter.format(educationalInfo.getGraduationCertificateIssueDate()));
            r.createCell(colInd++).setCellValue(educationalInfo.getKzGraduationCertificateAveragePoint());
            r.createCell(colInd++).setCellValue(educationalInfo.getDistinctionMarkType() == null ? "" : educationalInfo.getDistinctionMarkType().getName());
            r.createCell(colInd++).setCellValue(educationalInfo.getGraduationCertificateName());
            r.createCell(colInd++).setCellValue(educationalInfo.getNostrificationCertificateNumber());
            r.createCell(colInd++).setCellValue(educationalInfo.getNostrificationCertificateDate() == null ? "" : dateTimeFormatter.format(educationalInfo.getNostrificationCertificateDate()));
            r.createCell(colInd).setCellValue(educationalInfo.getSpecialityName());
        });
    }

    private String getUserIin(User user){
        Optional<PersonalInfo> optionalPersonalInfo = personalInfoRepository.findByUserId(user.getId());
        return optionalPersonalInfo.isEmpty() ? "" : optionalPersonalInfo.get().getIin();
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++)
            sheet.autoSizeColumn(i);
    }

    private String getUserFullName(User user) {
        return getFullName(user.getSurname(), user.getName(), user.getPatronymic());
    }

    private String getFullName(String surname, String name, String patronymic) {
        String fullName = checkStringNullableAndReturn(surname) + " " + checkStringNullableAndReturn(name) + " " + checkStringNullableAndReturn(patronymic);
        return fullName.trim();
    }

    private String checkStringNullableAndReturn(String str) {
        if (str == null || str.isEmpty() || str.isBlank()) return "";
        return str;
    }

    public Set<Long> selectFromFilterForExcel(ExcelDtoRequest excelDtoRequest) {
        List<UserDtoResponse> list;
        switch (excelDtoRequest.getType()) {
            case "iin":
                list = userFilterService.selectFilteredIINForExcel(excelDtoRequest.getKeyword(), excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification());
                break;
            case "locality":
                list = userFilterService.selectFilteredLocalityForExcel(excelDtoRequest.getKeyword(), excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification());
                break;
            case "FIO":
                list = userFilterService.selectFilteredFIOForExcel(excelDtoRequest.getKeyword(), excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification());
                break;
            default://ANY
                list = userFilterService.selectAllForExcel(excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification());
        }
        return list.stream().map(UserDtoResponse::getId).collect(Collectors.toSet());
    }

    public Set<Long> selectFromFilterForExcelByPage(ExcelDtoRequest excelDtoRequest) {
        HashMap<String, Object> map;
        switch (excelDtoRequest.getType()) {
            case "iin":
                map = userFilterService.selectFilteredIIN(excelDtoRequest.getKeyword(), excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification(), excelDtoRequest.getPage());
                break;
            case "locality":
                map = userFilterService.selectFilteredLocality(excelDtoRequest.getKeyword(), excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification(), excelDtoRequest.getPage());
                break;
            case "FIO":
                map = userFilterService.selectFilteredFIO(excelDtoRequest.getKeyword(), excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification(), excelDtoRequest.getPage());
                break;
            default:
                map = userFilterService.selectAll(excelDtoRequest.getEntFrom(), excelDtoRequest.getEntTo(), excelDtoRequest.getAETEnglishFrom(), excelDtoRequest.getAETEnglishTo(), excelDtoRequest.isFullness(), excelDtoRequest.isEnglishCertification(), excelDtoRequest.getPage());
        }
        List<UserDtoResponse> list = (List<UserDtoResponse>) map.get("list");
        return list.stream().map(UserDtoResponse::getId).collect(Collectors.toSet());
    }
}
