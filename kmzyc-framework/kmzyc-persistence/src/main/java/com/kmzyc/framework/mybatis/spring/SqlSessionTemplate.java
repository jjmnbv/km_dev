/**
 * Copyright 2010-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kmzyc.framework.mybatis.spring;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.kmzyc.framework.mybatis.session.SqlSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Thread safe, Spring managed, {@code SqlSession} that works with Spring
 * transaction management to ensure that that the actual SqlSession used is the
 * one associated with the current Spring transaction. In addition, it manages
 * the session life-cycle, including closing, committing or rolling back the
 * session as necessary based on the Spring transaction configuration.
 * <p>
 * The template needs a SqlSessionFactory to create SqlSessions, passed as a
 * constructor argument. It also can be constructed indicating the executor type
 * to be used, if not, the default executor type, defined in the session factory
 * will be used.
 * <p>
 * This template converts MyBatis PersistenceExceptions into unchecked
 * DataAccessExceptions, using, by default, a {@code MyBatisExceptionTranslator}.
 * <p>
 * Because SqlSessionTemplate is thread safe, a single instance can be shared
 * by all DAOs; there should also be a small memory savings by doing this. This
 * pattern can be used in Spring configuration files as follows:
 * <p>
 * <pre class="code">
 * {@code
 * <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
 * <constructor-arg ref="sqlSessionFactory" />
 * </bean>
 * }
 * </pre>
 *
 * @author Putthibong Boonbong
 * @author Hunter Presnall
 * @author Eduardo Macarron
 * @version $Id$
 * @see SqlSessionFactory
 * @see MyBatisExceptionTranslator
 */
public class SqlSessionTemplate extends org.mybatis.spring.SqlSessionTemplate implements SqlSession, DisposableBean {

    public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
    }

    public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
        this(sqlSessionFactory, executorType,
                new MyBatisExceptionTranslator(
                        sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true));
    }

    public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType, PersistenceExceptionTranslator exceptionTranslator) {
        super(sqlSessionFactory, executorType, exceptionTranslator);
    }


    @Override
    public <T> T queryForObject(String statement) {
        return super.selectOne(statement);
    }

    @Override
    public <T> T queryForObject(String statement, Object parameter) {
        return super.selectOne(statement, parameter);
    }

    @Override
    public <E> List<E> queryForList(String statement) {
        return super.selectList(statement);
    }

    @Override
    public <E> List<E> queryForList(String statement, Object parameter) {
        return super.selectList(statement, parameter);
    }

    @Override
    public <E> List<E> queryForList(String statement, Object parameter, RowBounds rowBounds) {
        return super.selectList(statement, parameter, rowBounds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, String valueKey) {
        List<Map<K, V>> list = super.selectList(statement, parameter);
        Map<K, V> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }

        for (Map<K, V> temp : list) {
            K key = (K) temp.get(mapKey);
            V value = temp.get(valueKey);
            map.put(key, value);
        }

        return map;
    }

    @Override
    public int batchInsert(String statement, Collection parameter) {
        org.apache.ibatis.session.SqlSession sqlSession = this.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            for (Object o : parameter)
                sqlSession.insert(statement, o);
            sqlSession.flushStatements();
        } finally {
            SqlSessionUtils.closeSqlSession(sqlSession, this.getSqlSessionFactory());
        }

        return parameter.size();
    }

    @Override
    public int batchUpdate(String statement, Collection parameter) {
        org.apache.ibatis.session.SqlSession sqlSession = this.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            for (Object o : parameter)
                sqlSession.update(statement, o);
            sqlSession.flushStatements();
        } finally {
            SqlSessionUtils.closeSqlSession(sqlSession, this.getSqlSessionFactory());
        }

        return parameter.size();
    }

    public int batchDelete(String statement, Collection parameters) {
        org.apache.ibatis.session.SqlSession sqlSession = this.getSqlSessionFactory().openSession(ExecutorType.BATCH);
        try {
            for (Object o : parameters)
                sqlSession.delete(statement, o);
            sqlSession.flushStatements();
        } finally {
            SqlSessionUtils.closeSqlSession(sqlSession, this.getSqlSessionFactory());
        }
        return parameters.size();
    }
}
