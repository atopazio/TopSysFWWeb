package br.com.topsys.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

import br.com.topsys.util.TSHibernateUtil;

/**
 * 
 * @author andre.topazio
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class TSHibernateFilter implements Filter {

	private SessionFactory sf = null;

	private static Log log = LogFactory.getLog(TSHibernateFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
		this.sf = TSHibernateUtil.getSessionFactory();
		log.info("Servlet filter init the Framework TopSys, now opening/closing a Session for each request.");

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {


		try {
			sf.getCurrentSession().beginTransaction();
			
			chain.doFilter(request, response);
			
			if(sf.getCurrentSession().getTransaction().isActive()){
				sf.getCurrentSession().getTransaction().commit();
			}
				

		} catch (StaleObjectStateException staleEx) {
			log
					.error("This interceptor does not implement optimistic concurrency control!");
			log
					.error("Your application will not work until you add compensation actions!");
		
			throw staleEx;
		} catch (RuntimeException ex) {
			// Rollback only
			log.info("roolback------------>"+ ex.getMessage());

			ex.printStackTrace();
			try {
				if (sf.getCurrentSession().getTransaction().isActive()) {
					log
							.debug("Trying to rollback database transaction after exception");
					sf.getCurrentSession().getTransaction().rollback();
				}
			} catch (RuntimeException rbEx) {
				log.error("Could not rollback transaction after exception!",
						rbEx);
			}

			
		}
		//finally{
			
		//	sf.getCurrentSession().close();
			
		//}
	}

	public void destroy() {
		sf.close();
	}
}