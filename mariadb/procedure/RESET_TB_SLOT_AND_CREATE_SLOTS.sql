DELIMITER //

CREATE PROCEDURE auto_cancel_and_create_slots()
BEGIN
  DECLARE tomorrow DATE;
  DECLARE curTime TIME;
  DECLARE slotTime DATETIME;

  UPDATE TB_RESERVATION
  SET STATUS = 'RS04'
  WHERE SLOT_ID IN (
    SELECT ID FROM TB_SLOT
    WHERE DATE(SLOT) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)
  )
  AND STATUS IN ('RS01');

  SET tomorrow = DATE_ADD(CURDATE(), INTERVAL 1 DAY);
  IF DAYOFWEEK(tomorrow) != 1 THEN
    SET curTime = '09:00:00';

    WHILE curTime < '18:00:00' DO
      IF curTime < '13:00:00' OR curTime >= '14:00:00' THEN
        SET slotTime = STR_TO_DATE(CONCAT(tomorrow, ' ', curTime), '%Y-%m-%d %H:%i:%s');
        INSERT INTO TB_SLOT (SLOT, CREATE_DATE)
        VALUES (slotTime, NOW());
      END IF;
      SET curTime = ADDTIME(curTime, '00:15:00');
    END WHILE;
  END IF;
END;
//

DELIMITER ;
