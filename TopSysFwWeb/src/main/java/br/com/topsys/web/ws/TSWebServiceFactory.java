package br.com.topsys.web.ws;

public final class TSWebServiceFactory {

	private static TSWebServiceFactory factory;
	
	private TSWebServiceFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static TSWebServiceFactory getInstance(){
		
		if(factory==null){
			return new TSWebServiceFactory();
		}
		
		return factory;
	}
	
	public TSWebServiceIf create(String wsdl){
		return new TSWebServiceApacheCFX(wsdl);
	}

}
