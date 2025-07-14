package com.emr.slgi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.common.dto.ListResponse;
import com.emr.slgi.member.controller.PatientController;
import com.emr.slgi.member.dto.MemberProfile;
import com.emr.slgi.member.dto.MemberProfileResponse;
import com.emr.slgi.member.dto.PatientRegisterDTO;
import com.emr.slgi.member.dto.PatientSearchDTO;
import com.emr.slgi.member.dto.PatientSummary;
import com.emr.slgi.member.dto.UpdatePatientProfileRequest;
import com.emr.slgi.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @Mock
  private MemberService memberService;

  @InjectMocks
  private PatientController patientController;

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
  }

  @Nested
  class RegisterByStaffTests {
    @Test
    void shouldReturnCreated() throws Exception {
      PatientRegisterDTO dto = new PatientRegisterDTO("홍길동", "900101-1234567", "010-1234-5678");

      mockMvc.perform(post("/patient")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated());

      verify(memberService).createPatient(dto);
    }
  }

  @Nested
  class SearchTests {
    @Test
    void shouldReturnOkAndData() throws Exception {
      PatientSummary summary = new PatientSummary();
      summary.setUuid("uuid-1234");
      summary.setName("홍길동");
      summary.setRrn("900101-1234567");
      summary.setPhone("010-1234-5678");
      summary.setCreateDate(LocalDateTime.of(2025, 7, 13, 19, 44, 49));
      List<PatientSummary> summaries = List.of(summary);
      when(memberService.search(any(PatientSearchDTO.class)))
        .thenReturn(new ListResponse<>(summaries));

      mockMvc.perform(get("/patient/search")
        .param("searchValue", "홍"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.list[0].uuid").value("uuid-1234"))
        .andExpect(jsonPath("$.list[0].name").value("홍길동"))
        .andExpect(jsonPath("$.list[0].rrn").value("900101-1234567"))
        .andExpect(jsonPath("$.list[0].phone").value("010-1234-5678"))
        .andExpect(jsonPath("$.list[0].createDate").exists());

      verify(memberService).search(any(PatientSearchDTO.class));
    }

    @Test
    void shouldReturnEmptyListForNoResults() throws Exception {
      when(memberService.search(any(PatientSearchDTO.class)))
        .thenReturn(new ListResponse<>(Collections.emptyList()));

      mockMvc.perform(get("/patient/search")
        .param("searchValue", "none"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.list").isArray())
        .andExpect(jsonPath("$.list").isEmpty());

      verify(memberService).search(any(PatientSearchDTO.class));
    }
  }

  @Nested
  class GetProfileTests {
    @Test
    void shouldReturnOkProfile() throws Exception {
      MemberProfile memberProfile = new MemberProfile();
      memberProfile.setUserid("test-uuid");
      memberProfile.setName("홍길동");
      memberProfile.setPhone("010-1234-5678");
      memberProfile.setRegisterDate("2025-07-01");
      MemberProfileResponse response = new MemberProfileResponse(memberProfile);
      when(memberService.getProfile(any()))
        .thenReturn(response);

      mockMvc.perform(get("/patient/profile")
        .principal((Principal) () -> "test-uuid"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.member.userid").value("test-uuid"))
        .andExpect(jsonPath("$.member.name").value("홍길동"))
        .andExpect(jsonPath("$.member.phone").value("010-1234-5678"));

      verify(memberService).getProfile(any());
    }

    @Test
    void shouldReturn404WhenProfileNotFound() throws Exception {
      when(memberService.getProfile(any()))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

      mockMvc.perform(get("/patient/profile")
        .principal((Principal) () -> "missing"))
        .andExpect(status().isNotFound());

      verify(memberService).getProfile(any());
    }
  }

  @Nested
  class UpdateProfileTests {
    @Test
    void shouldReturnNoContent() throws Exception {
      UpdatePatientProfileRequest updateDto = new UpdatePatientProfileRequest();
      updateDto.setName("김철수");
      updateDto.setPhone("010-9876-5432");

      mockMvc.perform(patch("/patient/profile")
        .principal((Principal) () -> "test-uuid")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isNoContent());

      verify(memberService).updateProfile(any(), any(UpdatePatientProfileRequest.class));
    }

    @Test
    void shouldReturn404WhenUpdateNotFound() throws Exception {
      UpdatePatientProfileRequest updateDto = new UpdatePatientProfileRequest();
      updateDto.setName("김철수");
      updateDto.setPhone("010-9876-5432");
      doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
        .when(memberService).updateProfile(any(), any(UpdatePatientProfileRequest.class));

      mockMvc.perform(patch("/patient/profile")
        .principal((Principal) () -> "missing")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateDto)))
        .andExpect(status().isNotFound());

      verify(memberService).updateProfile(any(), any(UpdatePatientProfileRequest.class));
    }
  }
}
