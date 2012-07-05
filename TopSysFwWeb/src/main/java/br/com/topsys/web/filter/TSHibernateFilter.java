package br.com.topsys.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.topsys.util.TSHibernateUtil;



public class TSHibernateFilter implements Filter {
	
	
		

		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {
			
			Session session = TSHibernateUtil.getSession();
			Transaction tx =null;
			
			try {
				tx = session.beginTransaction();
				
				chain.doFilter(request, response);
				
				if(tx.isActive()){
					tx.commit();
				}
					
			
			} catch (RuntimeException ex) {
				
				ex.printStackTrace();
				try {
					if (tx.isActive()) {
						System.err.println("Trying to rollback database transaction after exception");
						tx.rollback();
					}
				} catch (RuntimeException rbEx) {
					System.err.println("Could not rollback transaction after exception!"+
							rbEx.getMessage());
				}

				
			}finally{
				//session.close();
			}
			
		}

		public void destroy() {
			TSHibernateUtil.getSessionFactory().close();
		}

		public void init(FilterConfig arg0) throws ServletException {
			// TODO Auto-generated method stub
			
		}
	

}
