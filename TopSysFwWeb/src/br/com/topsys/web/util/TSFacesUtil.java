package br.com.topsys.web.util;




import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.topsys.util.TSLogUtil;



/**
 * @author André Topázio
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public final class TSFacesUtil {
    private static ResourceBundle bundle=null;

	private TSFacesUtil(){}
	
	static{
			
	    bundle =
			ResourceBundle.getBundle("config.Messages");
	    TSLogUtil.getInstance().info("config.Messages foi instanciado!");
	}

    /**
     * 
     * @return
     */
	public static ServletContext getServletContext() {
		return (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
	}
	

	
	/**
	 * Get managed bean based on the bean name.
	 * 
	 * @param beanName the bean name
	 * @return the managed bean associated with the bean name
	 */
	public static Object getManagedBean(String beanName) {
		Object o = getValueBinding(getJsfEl(beanName)).getValue(FacesContext.getCurrentInstance());

		
		return o;
	}  
	
	/**
	 * Remove the managed bean based on the bean name.
	 * 
	 * @param beanName the bean name of the managed bean to be removed
	 */
	public static void resetManagedBean(String beanName) {
		getValueBinding(getJsfEl(beanName)).setValue(FacesContext.getCurrentInstance(), null);
	}
	
	/**
	 * Store the managed bean inside the session scope.
	 * 
	 * @param beanName the name of the managed bean to be stored
	 * @param managedBean the managed bean to be stored
	 */
	@SuppressWarnings("unchecked")
	public static void setManagedBeanInSession(String beanName, Object managedBean) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(beanName, managedBean);
	}

	/**
	 * Retrieve the managed bean inside the session scope.
	 * 
	 * @param beanName the name of the managed bean to be stored
	 * @param managedBean the managed bean to be stored
	 */
	public static Object getManagedBeanInSession(String beanName) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(beanName);
	}
	
	/**
	 * Remove the managed bean inside the session scope.
	 * 
	 * @param beanName the name of the managed bean to be stored
	 * @param managedBean the managed bean to be stored
	 */
	public static Object removeManagedBeanInSession(String beanName) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
	}
	
	/**
	 * Colocar na sessão o objeto passado como parametros.
	 * @param beanName
	 * @param managedBean
	 */
	public static void addObjectInSession(String beanName, Object managedBean) {
		setManagedBeanInSession(beanName,managedBean);
	}
	
	public static void addObjectInRequest(String beanName, Object managedBean) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(beanName,managedBean);
	
	}
	
	/**
	 * Set parameter value from request scope.
	 * 
	 * @param name the name of the parameter
	 * @param object the object of the parameter 
	 * @return the parameter value
	 */
	public static void addRequestParameter(String name, Object object) {
		FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().put(name, object);
	} 


	/**
	 * Remove na sessão o objeto passado como parametros.
	 * @param beanName
	 * @param managedBean
	 */
	public static void removeObjectInSession(String beanName) {
		removeManagedBeanInSession(beanName);
	}

	/**
	 * Pegar da sessão o objeto passado como parametros.
	 * @param beanName
	 * @param managedBean
	 */
	public static Object getObjectInSession(String beanName) {
		return getManagedBeanInSession(beanName);
	}
	
	/**
	 * Get parameter value from request scope.
	 * 
	 * @param name the name of the parameter
	 * @return the parameter value
	 */
	public static String getRequestParameter(String name) {
		return (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	
	}
	
	public static Object getObjectInRequest(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(name);
	
	}
	
	
	
	
	/**
	 * Add information message.
	 * 
	 * @param msg the information message
	 */
	public static void addInfoMessage(String msg) {
		addInfoMessage(null, msg);
	}
	
	/**
	 * Add information message to a sepcific client.
	 * 
	 * @param clientId the client id 
	 * @param msg the information message
	 */
	public static void addInfoMessage(String clientId, String msg) {
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, null, msg));
	}
	
	
	private static String getText(String key){
	    String text = null;
	    
	    	try {
	      		text = bundle.getString(key);
	    	} catch (Exception e) {
	    		TSLogUtil.getInstance().warning("Não existe essa chave no Message.properties! "+key);
	    		text = e.getMessage();
	    	}
	    return text;	
	    
	}
	
	/**
	 * 
	 * @param clientId
	 * @param key
	 */
	public static void addMessage(String clientId, String key) {
		// Look up the requested message text
			// Construct and add a FacesMessage containing it
    	FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(getText(key)));
  	}    
	
	/**
	 * @author Henrique Machado
	 * 
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest(){
	    return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getResponse(){
	    return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}
	
	
	
	public static FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
   /**
	 * Add error message.
	 * 
	 * @param msg the error message
	 */
	public static void addErrorMessage(String msg) {
		addErrorMessage(null, msg);
	}
	
	public static void addErrorMessageKey(String key){
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,null, getText(key)));
	}
	
	public static void addInfoMessageKey(String key){
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,null, getText(key)));
	}
	
	/**
	 * Add error message to a sepcific client.
	 * 
	 * @param clientId the client id 
	 * @param msg the error message
	 */	
	public static void addErrorMessage(String clientId, String msg) {
		FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, msg));
	}
	  
	/**
	 * Evaluate the integer value of a JSF expression.
	 * 
	 * @param el the JSF expression
	 * @return the integer value associated with the JSF expression
	 */
	public static Integer evalInt(String el) {
		if (el == null) {
			return null;
		}
		
		if (UIComponentTag.isValueReference(el)) {
			Object value = getElValue(el);

			if (value == null) {
				return null;
			}
			else if (value instanceof Integer) {
				return (Integer)value;
			}
			else {
				return new Integer(value.toString());
			}
		}
		else {
			return new Integer(el);
		}
	}
	
	
	
	  
    /**
     * 
     * @return
     */ 
	private static Application getApplication() {
		ApplicationFactory appFactory = (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
		return appFactory.getApplication(); 
	}
	
	/**
	 * 
	 * @param el
	 * @return
	 */
	private static ValueBinding getValueBinding(String el) {
		return getApplication().createValueBinding(el);
	}
	
	/**
	 * 
	 * @return
	 */
	private static HttpServletRequest getServletRequest() {
		return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	/**
	 * 
	 * @param el
	 * @return
	 */
	private static Object getElValue(String el) {
		return getValueBinding(el).getValue(FacesContext.getCurrentInstance());
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private static String getJsfEl(String value) {
		return "#{" + value + "}";
	}
}


