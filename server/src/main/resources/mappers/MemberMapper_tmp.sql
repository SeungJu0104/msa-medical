<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="member">
    <select id="getDoctorList" resultType="PatientDTO">
        SELECT
            NAME
        FROM
             TB_MEMBER
        WHERE
            MEMBER_ROLE = 'R002'
    </select>

</mapper>

