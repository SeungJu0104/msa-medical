package com.emr.member;

import java.util.List;
import java.util.Optional;

public interface PatientDAO {

    public Optional<List<String>> getDoctorList();

}
