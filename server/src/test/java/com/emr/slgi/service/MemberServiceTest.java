package com.emr.slgi.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.common.dto.ListResponse;
import com.emr.slgi.member.dao.MemberDAO;
import com.emr.slgi.member.domain.Member;
import com.emr.slgi.member.dto.DoctorUuidName;
import com.emr.slgi.member.dto.MemberCreateDTO;
import com.emr.slgi.member.dto.MemberProfile;
import com.emr.slgi.member.dto.MemberProfileResponse;
import com.emr.slgi.member.dto.PatientRegisterDTO;
import com.emr.slgi.member.dto.PatientSearchDTO;
import com.emr.slgi.member.dto.PatientSummary;
import com.emr.slgi.member.dto.StaffSummary;
import com.emr.slgi.member.dto.UpdatePatientProfile;
import com.emr.slgi.member.dto.UpdatePatientProfileRequest;
import com.emr.slgi.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
  @Mock
  private MemberDAO memberDAO;

  @InjectMocks
  private MemberService memberService;

  @Nested
  class GetByUuidTests {
    @Test
    void returnsMember() {
      Member member = new Member();
      member.setUuid("uuid");
      when(memberDAO.getByUuid("uuid")).thenReturn(member);

      Member result = memberService.getByUuid("uuid");

      assertThat(result).isEqualTo(member);
      verify(memberDAO).getByUuid("uuid");
    }

    @Test
    void whenNotFound_returnsNull() {
      when(memberDAO.getByUuid("uuid")).thenReturn(null);

      Member result = memberService.getByUuid("uuid");

      assertThat(result).isNull();
      verify(memberDAO).getByUuid("uuid");
    }
  }

  @Nested
  class GetUuidByRrnTests {
    @Test
    void returnsOptionalUuid() {
      when(memberDAO.getUuidByRrn("rrn")).thenReturn(Optional.of("uuid"));

      Optional<String> result = memberService.getUuidByRrn("rrn");

      assertThat(result).isPresent().contains("uuid");
      verify(memberDAO).getUuidByRrn("rrn");
    }

    @Test
    void notFound_returnsEmpty() {
      when(memberDAO.getUuidByRrn("rrn")).thenReturn(Optional.empty());

      Optional<String> result = memberService.getUuidByRrn("rrn");

      assertThat(result).isNotPresent();
      verify(memberDAO).getUuidByRrn("rrn");
    }
  }

  @Nested
  class GetDoctorListTests {
    @Test
    void whenEmpty_throwsNotFound() {
      when(memberDAO.getDoctorList()).thenReturn(Collections.emptyList());

      assertThatThrownBy(() -> memberService.getDoctorList())
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("의사가 없습니다.")
        .matches(ex -> ((ResponseStatusException) ex).getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    void returnsListResponse() {
      List<DoctorUuidName> doctors = List.of(new DoctorUuidName());
      when(memberDAO.getDoctorList()).thenReturn(doctors);

      ListResponse<DoctorUuidName> response = memberService.getDoctorList();

      assertThat(response.list()).containsExactlyElementsOf(doctors);
    }
  }

  @Nested
  class GetOtherStaffListTests {
    @Test
    void whenEmpty_throwsNotFound() {
      when(memberDAO.getOtherStaffList("uuid")).thenReturn(Collections.emptyList());

      assertThatThrownBy(() -> memberService.getOtherStaffList("uuid"))
        .isInstanceOf(ResponseStatusException.class)
        .matches(ex -> ((ResponseStatusException) ex).getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    void returnsListResponse() {
      List<StaffSummary> staff = List.of(new StaffSummary());
      when(memberDAO.getOtherStaffList("uuid")).thenReturn(staff);

      ListResponse<StaffSummary> response = memberService.getOtherStaffList("uuid");

      assertThat(response.list()).isEqualTo(staff);
    }
  }

  @Nested
  class CreatePatientTests {
    @Test
    void success_returnsGeneratedUuid() {
      PatientRegisterDTO dto = new PatientRegisterDTO("Name", "123456-1234567", "010-1234-5678");

      String generatedUuid = memberService.createPatient(dto);

      assertThat(generatedUuid).isNotNull().hasSizeGreaterThan(0);

      ArgumentCaptor<MemberCreateDTO> captor = ArgumentCaptor.forClass(MemberCreateDTO.class);
      verify(memberDAO).createPatient(captor.capture());
      MemberCreateDTO captured = captor.getValue();
      assertThat(captured.getUuid()).isEqualTo(generatedUuid);
      assertThat(captured.getName()).isEqualTo("Name");
      assertThat(captured.getRrn()).isEqualTo("123456-1234567");
      assertThat(captured.getPhone()).isEqualTo("010-1234-5678");
    }

    @Test
    void whenConflict_throwsConflict() {
      PatientRegisterDTO dto = new PatientRegisterDTO("Name", "123456-1234567", "010-1234-5678");
      doThrow(new RuntimeException("SQL")).when(memberDAO).createPatient(any());

      assertThatThrownBy(() -> memberService.createPatient(dto))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("이미 존재하는 주민번호입니다.")
        .matches(ex -> ((ResponseStatusException) ex).getStatusCode() == HttpStatus.CONFLICT);
    }
  }

  @Nested
  class SearchTests {
    @Test
    void returnsListResponse() {
      PatientSearchDTO searchDTO = new PatientSearchDTO("test");
      List<PatientSummary> list = List.of(new PatientSummary());
      when(memberDAO.search(any())).thenReturn(list);

      ListResponse<PatientSummary> response = memberService.search(searchDTO);

      assertThat(response.list()).isEqualTo(list);
    }

    @Test
    void whenEmpty_returnsEmptyListResponse() {
      when(memberDAO.search(any())).thenReturn(Collections.emptyList());

      ListResponse<PatientSummary> response = memberService.search(new PatientSearchDTO("test"));

      assertThat(response.list()).isEmpty();
    }
  }

  @Nested
  class GetProfileTests {
    @Test
    void returnsResponse() {
      MemberProfile profile = new MemberProfile();
      when(memberDAO.getProfile("uuid")).thenReturn(profile);

      MemberProfileResponse response = memberService.getProfile("uuid");

      assertThat(response.member()).isEqualTo(profile);
    }

    @Test
    void whenNotFound_returnsResponseWithNull() {
      when(memberDAO.getProfile("uuid")).thenReturn(null);

      MemberProfileResponse response = memberService.getProfile("uuid");

      assertThat(response.member()).isNull();
    }
  }

  @Nested
  class UpdateProfileTests {
    @Test
    void invokesDAOUpdate() {
      UpdatePatientProfileRequest request = new UpdatePatientProfileRequest();
      request.setName("NewName");
      request.setPhone("010-1111-2222");

      memberService.updateProfile("uuid", request);

      ArgumentCaptor<UpdatePatientProfile> captor = ArgumentCaptor.forClass(UpdatePatientProfile.class);
      verify(memberDAO).updateProfile(captor.capture());
      UpdatePatientProfile update = captor.getValue();
      assertThat(update.getUuid()).isEqualTo("uuid");
      assertThat(update.getName()).isEqualTo("NewName");
      assertThat(update.getPhone()).isEqualTo("010-1111-2222");
    }

    @Test
    void whenDaoThrows_throwsRuntimeException() {
      doThrow(new RuntimeException("DBError")).when(memberDAO).updateProfile(any());

      UpdatePatientProfileRequest request = new UpdatePatientProfileRequest();
      assertThatThrownBy(() -> memberService.updateProfile("uuid", request))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("DBError");
    }
  }
}
