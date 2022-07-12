package com.dean.my_blog.interceptor.mybatis;

import com.dean.my_blog.entity.BaseEntity;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MyPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        MapperMethod.ParamMap parameterMap = (MapperMethod.ParamMap) invocation.getArgs()[1];
        Object parameter = parameterMap.get("et");
        if (parameter instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) parameter;
            if (Objects.equals(SqlCommandType.INSERT, mappedStatement.getSqlCommandType())) {
                baseEntity.setUpdatedAt(LocalDateTime.now());
                baseEntity.setCreatedAt(LocalDateTime.now());
            } else if (Objects.equals(SqlCommandType.UPDATE, mappedStatement.getSqlCommandType())) {
                baseEntity.setUpdatedAt(LocalDateTime.now());
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
