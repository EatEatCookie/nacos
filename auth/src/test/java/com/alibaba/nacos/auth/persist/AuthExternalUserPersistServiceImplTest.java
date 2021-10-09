/*
 * Copyright 1999-2021 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.auth.persist;

import com.alibaba.nacos.auth.model.Page;
import com.alibaba.nacos.auth.users.User;
import com.alibaba.nacos.auth.persist.repository.PaginationHelper;
import com.alibaba.nacos.auth.persist.repository.externel.AuthExternalStoragePersistServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;

@RunWith(MockitoJUnitRunner.class)
public class AuthExternalUserPersistServiceImplTest {
    
    @Mock
    private AuthExternalStoragePersistServiceImpl persistService;
    
    @Mock
    private JdbcTemplate jdbcTemplate;
    
    @Mock
    private PaginationHelper paginationHelper;
    
    private AuthExternalUserPersistServiceImpl externalUserPersistService;
    
    @Before
    public void setUp() throws Exception {
        externalUserPersistService = new AuthExternalUserPersistServiceImpl();
        
        Class<AuthExternalUserPersistServiceImpl> externalUserPersistServiceClass = AuthExternalUserPersistServiceImpl.class;
        Field persistServiceClassDeclaredField = externalUserPersistServiceClass.getDeclaredField("persistService");
        persistServiceClassDeclaredField.setAccessible(true);
        persistServiceClassDeclaredField.set(externalUserPersistService, persistService);
        
        Mockito.when(persistService.getJdbcTemplate()).thenReturn(jdbcTemplate);
        Mockito.when(persistService.createPaginationHelper()).thenReturn(paginationHelper);
        externalUserPersistService.init();
    }
    
    @Test
    public void testCreateUser() {
        externalUserPersistService.createUser("username", "password");
        
        String sql = "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)";
        Mockito.verify(jdbcTemplate).update(sql, "username", "password", true);
    }
    
    @Test
    public void testFindUserByUsername() {
        User username = externalUserPersistService.findUserByUsername("username");
        Assert.assertNull(username);
    }
    
    @Test
    public void testGetUsers() {
        Page<User> users = externalUserPersistService.getUsers(1, 10);
        Assert.assertNotNull(users);
    }
    
}