/*******************************************************************************
 * Copyright 2013-2015 alladin-IT GmbH
 * Copyright 2013-2015 Rundfunk und Telekom Regulierungs-GmbH (RTR-GmbH)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package at.rtr.rmbt.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbConnection
{
    
    private static DataSource ds;
    
    private static void init()
    {
        DataSource _ds = null;
        try
        {
            _ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/rmbt");
        }
        catch (final NamingException e)
        {
        }
        ds = _ds;
    }
    
    public static Connection getConnection() throws NamingException, SQLException
    {
        if (ds == null) {
            init();
        }
        final Connection connection = ds.getConnection();
        connection.setAutoCommit(true);
        return connection;
    }
}
