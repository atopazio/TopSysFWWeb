package br.com.topsys.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;


import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSHibernateUtil;

public class TSHibernateFilter implements Filter {
	
	private SessionFactory sessionFactory;
		

		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {
			
			
			try {
				sessionFactory.getCurrentSession().beginTransaction();
				
				chain.doFilter(request, response);
				
				sessionFactory.getCurrentSession().getTransaction().commit();
					
			
			} catch (TSSystemException ex) {
				
				try {
					if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
						System.err.println("Trying to rollback database transaction after exception");
						sessionFactory.getCurrentSession().getTransaction().rollback();
					}
				} catch (RuntimeException rbEx) {
					System.err.println("Could not rollback transaction after exception!"+
							rbEx.getMessage());
				}

				
			}
			
		}

		public void destroy() {
			TSHibernateUtil.getSessionFactory().close();
		}

		public void init(FilterConfig arg0) throws ServletException {
			sessionFactory = TSHibernateUtil.getSessionFactory();
			
		}
	

}
