/**
 * Copyright 2009-2016 the original author or authors.
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
package com.kmzyc.framework.mybatis.session;

import org.apache.ibatis.session.RowBounds;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The primary Java interface for working with MyBatis.
 * Through this interface you can execute commands, get mappers and manage transactions.
 *
 * @author Clinton Begin
 */
public interface SqlSession extends org.apache.ibatis.session.SqlSession {

    /**
     * @param <T>       the returned object type
     * @param statement
     * @return Mapped object
     * @deprecated Retrieve a single row mapped from the statement key
     */
    <T> T queryForObject(String statement);

    /**
     * @param <T>       the returned object type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return Mapped object
     * @deprecated Retrieve a single row mapped from the statement key and parameter.
     */
    <T> T queryForObject(String statement, Object parameter);

    /**
     * @param <E>       the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @return List of mapped object
     * @deprecated Retrieve a list of mapped objects from the statement key and parameter.
     */
    <E> List<E> queryForList(String statement);

    /**
     * @param <E>       the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return List of mapped object
     * @deprecated Retrieve a list of mapped objects from the statement key and parameter.
     */
    <E> List<E> queryForList(String statement, Object parameter);

    /**
     * @param <E>       the returned list element type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param rowBounds Bounds to limit object retrieval
     * @return List of mapped object
     * @deprecated Retrieve a list of mapped objects from the statement key and parameter,
     * within the specified row bounds.
     */
    <E> List<E> queryForList(String statement, Object parameter, RowBounds rowBounds);

    /**
     * The selectMap is a special case in that it is designed to convert a list
     * of results into a Map based on one of the properties in the resulting
     * objects.
     *
     * @param <K>       the returned Map keys type
     * @param <V>       the returned Map values type
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param mapKey    The property to be used as the key in the Map.
     * @param valueKey  The property to be used as the value in the Map.
     * @return Map containing key pair data.
     * extend by xuyuliang in 2016-06-07
     * reference ibatis SqlMapClient:  Map queryForMap(String id, Object parameterObject, String keyProp, String valueProp) throws SQLException
     */
    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, String valueKey);

    /**
     * Execute an insert statement with the given parameter object. Any generated
     * autoincrement values or selectKey entries will modify the given parameter
     * object properties. Only the number of rows affected will be returned.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the insert.
     */
    int batchInsert(String statement, Collection parameter);

    /**
     * Execute an update statement. The number of rows affected will be returned.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the update.
     */
    int batchUpdate(String statement, Collection parameter);

    /**
     * Execute an delete statement. The number of rows affected will be returned.
     *
     * @param statement  Unique identifier matching the statement to execute.
     * @param parameters parameter objects to pass to the statement.
     * @return int The number of rows affected by the update.
     */
    public int batchDelete(String statement, Collection parameters);

}
