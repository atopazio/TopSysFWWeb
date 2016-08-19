/*
 * Created on 11/08/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.topsys.web.faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSDataBaseException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.web.util.TSFacesUtil;


/**
 * @author andre
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class TSMainFaces implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean insertDisabled;

	private boolean updateDisabled;

	private boolean deleteDisabled;

	private boolean findDisabled;

	private boolean detailDisabled;

	private boolean defaultMessage;
	
	private boolean clearFields;

	private static final String OPERACAO_SUCESSO = "OPERACAO_SUCESSO";
	
	protected static final String CADASTRO = "cadastro";
	
	protected static final String PESQUISA = "pesquisa";
	
	protected static final String SUCESSO = "sucesso";
	
	
	

	/**
	 * 
	 */
	public TSMainFaces() {
		this.clearFields = true;
		this.defaultMessage = true;
	}



	protected final void addInfoMessage(String msg) {
		TSFacesUtil.addInfoMessage(msg);
	}

	protected final void addInfoMessageKey(String msg) {
		TSFacesUtil.addInfoMessageKey(msg);
	}

	protected final void addErrorMessage(String msg) {
		TSFacesUtil.addErrorMessage(msg); 
	}

	protected final void addErrorMessageKey(String msg) {
		TSFacesUtil.addErrorMessageKey(msg);
	}

	protected final void addObjectInSession(String name, Object value) {
		TSFacesUtil.addObjectInSession(name, value);
	}
	
	protected final void addObjectInRequest(String name, Object value) {
		TSFacesUtil.addObjectInRequest(name, value);
	}
	

	protected final void removeObjectInSession(String name) {
		TSFacesUtil.removeObjectInSession(name);
	}

	protected final String getRequestParameter(String name) {
		return TSFacesUtil.getRequestParameter(name);
	}

	protected final Object getObjectInRequest(String name) {
		return TSFacesUtil.getObjectInRequest(name);
	}

	protected final Object getObjectInSession(String name) {
		return TSFacesUtil.getObjectInSession(name);
	}
	
	protected final FacesContext getFacesContext(){
		return TSFacesUtil.getFacesContext();
	}
	
	protected final List<SelectItem> initCombo(Collection coll,String nomeValue,String nomeLabel) {
		return TSFacesUtil.initCombo(coll, nomeValue, nomeLabel);
	}

	public final String insertEvent() {
		String retorno = null;
		
		if(!hasAccess()){
			return null;
		}
		
		try {
			this.habilitarInserir();

			retorno = insert();
			
			if(this.isClearFields()){
				clearFields();
			}
			

			if (isDefaultMessage()) {
				this.addInfoMessageKey(OPERACAO_SUCESSO);
			}

		} catch (TSApplicationException e) {
			this.throwException(e);
			
		
		} catch (TSSystemException e) {
			e.printStackTrace();
			
			this.addErrorMessage(e.getMessage());
			

		} catch (Exception e) {
			e.printStackTrace();
			
			this.addErrorMessage(e.getMessage());
			
		}
		return retorno;
	}

	protected void throwException(Exception e) {
		if (e instanceof TSDataBaseException){
			this.addErrorMessage(e.getMessage());
		}else{
			this.addErrorMessageKey(e.getMessage());
		}
		
	}



	public final String updateEvent() {
		String retorno = null;
		
		if(!hasAccess()){
			return null;
		}
		
		try {
			this.habilitarAlterar();

			retorno = update();

			if (isDefaultMessage()) {
				this.addInfoMessageKey(OPERACAO_SUCESSO);
			}

		} catch (TSApplicationException e) {
			this.throwException(e);
		
		} catch (TSSystemException e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		
		} catch (Exception e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		}
		return retorno;
	}

	public final String deleteEvent() {
		String retorno = null;
		
		if(!hasAccess()){
			return null;
		}
		
		try {
			
			retorno = delete();
			
			if (isDefaultMessage()) {
				this.addInfoMessageKey(OPERACAO_SUCESSO);
			}
		
		} catch (TSApplicationException e) {
			this.throwException(e);
		
		} catch (TSSystemException e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		
		} catch (Exception e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		}
		return retorno;
	}

	public final String detailEvent() {
		String retorno = null;
		
		try {
		
			this.habilitarAlterar();

			retorno = detail();

		} catch (TSSystemException e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		
		} catch (Exception e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		}
		return retorno;
	}

	public final String findEvent() {
		String retorno = null;
		try {
			retorno = find();

		} catch (TSSystemException e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		
		} catch (Exception e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		}
		return retorno;
	}

	public final String findLinkEvent() {
		String retorno = null;
		try {
			retorno = findLink();

			clearFields();

		} catch (TSSystemException e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		
		} catch (Exception e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		}
		return retorno;
	}

	public final String newLinkEvent() {
		String retorno = null;
		
		try {
			retorno = newLink();

			this.habilitarInserir();
			
			clearFields();

		} catch (TSSystemException e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		
		} catch (Exception e) {
			e.printStackTrace();
			this.addErrorMessage(e.getMessage());
			//this.addErrorMessage("Verifique o log, pois ocorreu um erro no sistema!");
		}
		return retorno;
	}

	protected String insert() throws TSApplicationException {
		return null;
	}

	protected String update() throws TSApplicationException {
		return null;
	}

	protected String delete() throws TSApplicationException {
		return null;
	}

	protected String find() {
		return null;
	}

	protected String findLink() {
		return null;
	}

	protected String newLink() {
		return null;
	}

	protected String detail() {
		return null;
	}

	protected void clearFields() {

	}
	
	protected boolean hasAccess(){
		return true;
	}
	

	protected final void habilitarInserir() {
		this.insertDisabled = false;
		this.updateDisabled = true;
		this.deleteDisabled = true;
	}

	protected final void habilitarAlterar() {
		this.insertDisabled = true;
		this.updateDisabled = false;
		this.deleteDisabled = false;
	}


	public final boolean isUpdateDisabled() {
		return updateDisabled;
	}

	public final void setUpdateDisabled(boolean alterar) {
		this.updateDisabled = alterar;
	}

	public final boolean isDetailDisabled() {
		return detailDisabled;
	}

	public final void setDetailDisabled(boolean detalhar) {
		this.detailDisabled = detalhar;
	}

	public final boolean isDeleteDisabled() {
		return deleteDisabled;
	}

	public final void setDeleteDisabled(boolean excluir) {
		this.deleteDisabled = excluir;
	}

	public final boolean isInsertDisabled() {
		return insertDisabled;
	}

	public final void setInsertDisabled(boolean inserir) {
		this.insertDisabled = inserir;
	}

	public final boolean isFindDisabled() {
		return findDisabled;
	}

	public final void setFindDisabled(boolean pesquisar) {
		this.findDisabled = pesquisar;
	}

	protected final boolean isDefaultMessage() {
		return defaultMessage;
	}

	protected final void setDefaultMessage(boolean defaultMessage) {
		this.defaultMessage = defaultMessage;
	}



	public boolean isClearFields() {
		return clearFields;
	}



	public void setClearFields(boolean clearFields) {
		this.clearFields = clearFields;
	}
	


}
