package com.emr.slgi.reception.typehandler;

import com.emr.slgi.reception.enums.ReceptionStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(ReceptionStatus.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ReceptionStatusTypeHandler extends BaseTypeHandler<ReceptionStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ReceptionStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public ReceptionStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ReceptionStatus.fromCode(rs.getString(columnName));
    }

    @Override
    public ReceptionStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ReceptionStatus.fromCode(rs.getString(columnIndex));
    }

    @Override
    public ReceptionStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ReceptionStatus.fromCode(cs.getString(columnIndex));
    }
}







