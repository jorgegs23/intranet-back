package com.proyecto.intranet.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;


/**
 * The type Jasper document provider.
 */
public interface JasperDocumentProvider {

    /**
     * Crear jasper byte [ ].
     *
     * @param contenidoUnico       the contenido
     * @param parametroJasper the parametro jasper
     * @return the byte [ ]
     * @throws JRException the jr exception
     */
    public byte[] crearJasper(Map<String,Object> contenidoUnico,String parametroJasper) throws JRException;
    
    public JasperPrint crearJasperNoFields(Map<String,Object> params, String fileName) throws JRException;
    
    public byte[] generatePdfList(List<JasperPrint> prints) throws JRException;

    /**
     * Crear jasper.
     *
     * @param params the params
     * @param fields the fields
     * @param parametroJasper the parametro jasper
     * @return the byte[]
     * @throws JRException the JR exception
     */
    public byte[] crearJasper(Map<String,Object> params, List<Map<String,Object>> fields, String parametroJasper) throws JRException;


    /**
     * método de test para escribir a disco el pdf
     * @param content
     * @param path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveDocToFile(byte[] content, String path) throws FileNotFoundException, IOException;

    public JasperPrint getObjectPdf(String fileName, Map<String, Object> parameters, List<Map<String,Object>> fields);

    public byte[] getBytesJasper(JasperPrint jasperPrint);
    
    
    byte[] crearByteJasper(Map<String, Object> contenido, String jasperTemplate) throws Exception;
}
