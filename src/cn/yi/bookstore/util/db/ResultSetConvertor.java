package cn.yi.bookstore.util.db;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//todo能否做成FunctionalInterface?
public abstract class ResultSetConvertor<T> {

    //todo 使用static试试
    final ResultSetHandler<T> firstRow2Object = rs -> {
        if (rs.next()) {
            return this.convert(rs);
        } else {
            return null;
        }
    };

    final ResultSetHandler<List<T>> rowByRow2Object = rs -> {
        List<T> resultList = new ArrayList<>();
        while (rs.next()) {
            resultList.add(this.convert(rs));
        }
        return resultList;
    };

    public static final ResultSetConvertor<Integer> INTEGER_CONVERTOR = new ResultSetConvertor<Integer>() {
        @Override
        public Integer convert(ResultSet rs) {
            try {
                return rs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to convert Integer");
            }
        }
    };

    public abstract T convert(ResultSet rs);
}
