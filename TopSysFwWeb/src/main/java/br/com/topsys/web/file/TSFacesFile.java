package br.com.topsys.web.file;

import java.sql.Blob;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Essa Classe ï¿½ usada para manipular arquivos. 
 * 
 * @author André Monteiro
 */
public final class TSFacesFile {
	
	public static void blobToFileTemp(Blob blob, String nomeArquivo) {

		try {

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			response.setContentType("application/*");

			response.setHeader("Content-disposition", "attachment; filename=" + nomeArquivo);

			ServletOutputStream out = response.getOutputStream();
			out.write(blob.getBytes(1, (int) blob.length()));
			out.flush();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}