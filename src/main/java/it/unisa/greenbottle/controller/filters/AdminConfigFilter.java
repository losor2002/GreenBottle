package it.unisa.greenbottle.controller.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe di configurazione del filtro per l'amministratore.
 */
@Configuration
public class AdminConfigFilter {

  @Autowired
  private AdminFilter adminFilter;


  /**
   * Metodo per la creazione del filtro per l'amministratore.
   *
   * @return il filtro per l'amministratore.
   */
  @Bean
  public FilterRegistrationBean<AdminFilter> filterBeanAdmin() {
    FilterRegistrationBean<AdminFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(adminFilter);
    registrationBean.addUrlPatterns("/admin", "/admin/*");

    return registrationBean;
  }
}