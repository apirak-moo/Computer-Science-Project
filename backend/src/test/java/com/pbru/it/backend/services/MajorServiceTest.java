package com.pbru.it.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.pbru.it.backend.DTO.request.MajorRequest;
import com.pbru.it.backend.Models.Major;
import com.pbru.it.backend.Models.Program;
import com.pbru.it.backend.Repositories.MajorRepository;
import com.pbru.it.backend.Repositories.ProgramRepository;
import com.pbru.it.backend.Services.FileService;
import com.pbru.it.backend.Services.MajorService;
import com.querydsl.jpa.impl.JPAQueryFactory;

@ExtendWith(MockitoExtension.class)
class MajorServiceTest {

    @InjectMocks
    private MajorService majorService;

    @Mock
    private MajorRepository majorRepository;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private JPAQueryFactory jpaQueryFactory;

    @Mock
    private FileService fileService;

    @Mock
    private CSVReader csvReader;

    private Major major;
    private MajorRequest majorRequest;

    @BeforeEach // Method ที่จะรันก่อนทุกๆ @Test
    void setUp() {
        // เตรียมข้อมูลตัวอย่าง
        Program program = new Program();
        program.setId(1L);
        program.setNameTh("หลักสูตรวิศวกรรมศาสตรบัณฑิต");

        major = new Major();
        major.setId(1L);
        major.setNameTh("วิศวกรรมคอมพิวเตอร์");
        major.setNameEn("Computer Engineering");
        major.setProgram(program);
        major.setStatus(true);

        majorRequest = new MajorRequest();
        majorRequest.setProgramId(1L);
        majorRequest.setNameTh("วิศวกรรมคอมพิวเตอร์");
        majorRequest.setNameEn("Computer Engineering");
        majorRequest.setStatus(true);
    }

    @Test
    void findById_whenMajorExists_shouldReturnMajor() {
        // Given: กำหนดว่าเมื่อ majorRepository.findById(1L) ถูกเรียก จะต้องคืนค่า
        // Optional ที่มี major object ของเรา
        when(majorRepository.findById(1L)).thenReturn(Optional.of(major));

        // When: เรียก service method ที่ต้องการทดสอบ
        Optional<Major> foundMajor = majorService.findById(1L);

        // Then: ตรวจสอบผลลัพธ์
        assertTrue(foundMajor.isPresent(), "ควรจะหา Major เจอ");
        assertEquals("วิศวกรรมคอมพิวเตอร์", foundMajor.get().getNameTh(), "ชื่อภาษาไทยของ Major ควรจะตรงกัน");

        // Verify: ตรวจสอบว่า majorRepository.findById(1L) ถูกเรียกใช้งาน 1 ครั้ง
        verify(majorRepository, times(1)).findById(1L);
    }

    @Test
    void save_whenGivenValidRequestWithImage_shouldSaveAndReturnMajor() {
        // Given
        // 1. สร้างไฟล์ Mock สำหรับอัปโหลด
        MultipartFile mockImageFile = new MockMultipartFile(
                "image",
                "hello.png",
                "image/png",
                "some-image-content".getBytes());
        majorRequest.setImage(mockImageFile);

        // 2. กำหนดพฤติกรรมของ Mock objects
        when(fileService.uploadImageFile(any(MultipartFile.class))).thenReturn("/images/new-image.png");
        // any() คือการบอกว่า เมื่อมีการเรียก save ด้วย object Major ประเภทใดก็ตาม
        // ให้คืนค่า major ที่เรากำหนด
        when(majorRepository.save(any(Major.class))).thenAnswer(invocation -> {
            Major majorToSave = invocation.getArgument(0);
            majorToSave.setId(2L); // สมมติว่า database generate id ใหม่เป็น 2
            return majorToSave;
        });

        // When
        Major savedMajor = majorService.save(majorRequest);

        // Then
        assertNotNull(savedMajor);
        assertEquals(2L, savedMajor.getId());
        assertEquals("วิศวกรรมคอมพิวเตอร์", savedMajor.getNameTh());
        assertEquals("/images/new-image.png", savedMajor.getImage(), "ควรจะมีการเซ็ต URL รูปภาพใหม่");

        // Verify
        verify(fileService, times(1)).uploadImageFile(mockImageFile);
        verify(majorRepository, times(1)).save(any(Major.class));
    }

    @Test
    void updateById_whenMajorNotFound_shouldThrowRuntimeException() {
        // Given: กำหนดว่าเมื่อค้นหา ID ที่ไม่มีอยู่จริง จะคืนค่า Optional.empty()
        when(majorRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then: ตรวจสอบว่ามีการโยน RuntimeException ออกมาเมื่อเรียก updateById
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            majorService.updateById(99L, majorRequest);
        });

        assertEquals("Major not found!", exception.getMessage(), "ข้อความของ Exception ควรจะถูกต้อง");

        // Verify: ตรวจสอบว่าไม่มีการเรียก save เกิดขึ้น
        verify(majorRepository, never()).save(any(Major.class));
    }

    @Test
    void deleteById_whenMajorExists_shouldDeleteMajorAndImage() {
        // Given
        major.setImage("/images/old-image.jpg"); // สมมติว่า major นี้มีรูปภาพอยู่
        when(majorRepository.findById(1L)).thenReturn(Optional.of(major));
        // doNothing() ใช้เมื่อ method ที่ mock เป็น void
        doNothing().when(fileService).deleteImageFile(anyString());
        doNothing().when(majorRepository).delete(any(Major.class));

        // When
        majorService.deleteById(1L);

        // Then: ตรวจสอบว่ามีการเรียก method ที่ถูกต้อง
        verify(majorRepository, times(1)).findById(1L);
        verify(fileService, times(1)).deleteImageFile("/images/old-image.jpg");
        verify(majorRepository, times(1)).delete(major);
    }

}
