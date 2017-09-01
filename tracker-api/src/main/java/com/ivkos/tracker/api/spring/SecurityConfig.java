package com.ivkos.tracker.api.spring;

import com.ivkos.tracker.core.models.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ivkos.tracker.core.constants.ApiEndpoints.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
   private final DeviceAuthenticationFilter deviceAuthFilter;

   @Autowired
   SecurityConfig(DeviceAuthenticationFilter deviceAuthFilter)
   {
      super(true);
      this.deviceAuthFilter = deviceAuthFilter;
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception
   {
      http.csrf().disable();
      http.sessionManagement().disable();

      http.anonymous().principal(Device.ANONYMOUS);

      http
            .authorizeRequests()
            .antMatchers(POST, HEARTBEAT).hasAuthority(Roles.DEVICE)

            .antMatchers(GET, DEVICES).hasAuthority(Roles.ADMIN)
            .antMatchers(POST, DEVICES).hasAuthority(Roles.ADMIN)
            .antMatchers(GET, DEVICES_UUID_OF_HARDWARE_ID).hasAuthority(Roles.ADMIN)

            .antMatchers(GET, HISTORY).hasAuthority(Roles.CLIENT)
            .antMatchers(GET, HISTORY_ID).hasAuthority(Roles.CLIENT)
            .antMatchers(PUT, HISTORY).hasAuthority(Roles.DEVICE)

            .anyRequest().authenticated()
            .and()
            .addFilterAt(deviceAuthFilter, UsernamePasswordAuthenticationFilter.class);
   }
}