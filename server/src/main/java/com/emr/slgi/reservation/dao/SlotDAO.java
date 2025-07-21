package com.emr.slgi.reservation.dao;

import com.emr.slgi.reservation.dto.FindReservationDate;
import com.emr.slgi.reservation.dto.Slot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SlotDAO {

    int checkSlotExistence(Long slotId);

    int selectSlotForUpdate(Long slotId);

    List<Slot> getAllSlots(FindReservationDate reservation);

}
