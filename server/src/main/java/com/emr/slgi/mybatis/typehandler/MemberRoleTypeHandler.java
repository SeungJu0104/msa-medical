package com.emr.slgi.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.emr.slgi.member.enums.MemberRole;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(MemberRole.class)
public class MemberRoleTypeHandler extends EnumTypeHandler<MemberRole> {

    public MemberRoleTypeHandler(Class<MemberRole> type) {
        super(type);
    }

    @Override
    public MemberRole getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return (code == null) ? null : MemberRole.fromCode(code);
    }

    @Override
    public MemberRole getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return (code == null) ? null : MemberRole.fromCode(code);
    }

    @Override
    public MemberRole getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return (code == null) ? null : MemberRole.fromCode(code);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MemberRole parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getCode());
    }
}
