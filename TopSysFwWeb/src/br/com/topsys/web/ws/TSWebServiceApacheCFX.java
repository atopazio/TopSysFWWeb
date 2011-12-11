package br.com.topsys.web.ws;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;

import br.com.topsys.exception.TSSystemException;

public final class TSWebServiceApacheCFX implements TSWebServiceIf {

	private Client client;
	private DynamicClientFactory dcf;

	protected TSWebServiceApacheCFX(String wsdl) {

		dcf = DynamicClientFactory.newInstance();
		client = dcf.createClient(wsdl);
	}

	public Object execute(String nameMethod, Object... parameter) {
		Object[] ret = null;
		try {
			if (parameter != null) {

				ret = (Object[]) client.invoke(nameMethod, parameter);

			} else {

				ret = (Object[]) client.invoke(nameMethod, null);
			}
		} catch (Exception e) {
			throw new TSSystemException(e);

		}
		return ret != null ? ret[0] : null;
	}

}
