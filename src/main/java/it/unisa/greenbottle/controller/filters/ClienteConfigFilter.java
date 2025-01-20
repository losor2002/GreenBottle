package it.unisa.greenbottle.controller.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteConfigFilter {

  @Autowired
  private ClienteFilter clienteFilter;

  @Bean
  public FilterRegistrationBean<ClienteFilter> filterBeanCliente() {
    FilterRegistrationBean<ClienteFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(clienteFilter);
    registrationBean.addUrlPatterns("/areaPersonale", "/areaPersonale/*", "/carrello");

    return registrationBean;
  }
}
