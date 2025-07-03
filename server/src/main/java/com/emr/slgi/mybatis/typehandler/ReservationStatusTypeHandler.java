package com.emr.slgi.mybatis.typehandler;

import com.emr.slgi.reception.enums.ReceptionStatus;
import com.emr.slgi.reservation.enums.ReservationStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(ReservationStatus.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ReservationStatusTypeHandler extends BaseTypeHandler<ReservationStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ReservationStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public ReservationStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ReservationStatus.fromCode(rs.getString(columnName));
    }

    @Override
    public ReservationStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ReservationStatus.fromCode(rs.getString(columnIndex));
    }

    @Override
    public ReservationStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ReservationStatus.fromCode(cs.getString(columnIndex));
    }

}
