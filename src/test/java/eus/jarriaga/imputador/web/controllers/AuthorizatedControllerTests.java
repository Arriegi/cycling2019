package eus.jarriaga.imputador.web.controllers;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class AuthorizatedControllerTests {

    protected Principal principalAdmin, principalUser;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    protected MockMvc mockMvc;
    protected final static String USERNAME_ADMIN = "jon@elkarmedia.eus";
    protected final static String PASSWORD_ADMIN = "elkarmedia";
    protected final static String USERNAME_USER = "jon_arriaga@hotmail.com";
    protected final static String PASSWORD_USER = "pasahitza";
    protected final static String ROLE_ADMIN = "ROLE_ADMIN";
    protected final static String ROLE_USER = "ROLE_USER";

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilters(this.springSecurityFilterChain).build();
        List<GrantedAuthority> authoritiesAdmin = new ArrayList<GrantedAuthority>();
        List<GrantedAuthority> authoritiesUser = new ArrayList<GrantedAuthority>();
        authoritiesAdmin.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        authoritiesUser.add(new SimpleGrantedAuthority(ROLE_USER));
        principalAdmin = new UsernamePasswordAuthenticationToken(USERNAME_ADMIN,
                null, authoritiesAdmin);
        principalUser = new UsernamePasswordAuthenticationToken(USERNAME_USER,
                null, authoritiesUser);
    }

}
