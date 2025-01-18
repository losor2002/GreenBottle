package it.unisa.greenbottle.controller.filters;

import it.unisa.greenbottle.controller.accessoControl.util.SessionAdmin;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminFilter
    implements Filter {

  @Autowired
  private SessionAdmin sessionAdmin;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {


    if (sessionAdmin.getAdmin().isPresent()) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      ((HttpServletResponse) servletResponse).sendRedirect("/loginAdmin");
    }
  }
}
