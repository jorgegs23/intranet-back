package com.proyecto.intranet.provider.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.proyecto.intranet.provider.JasperDocumentProvider;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


/**
 * The type Jasper document provider.
 */
@Slf4j
@Component
public class JasperDocumentProviderImpl implements JasperDocumentProvider {

    /** The jasper src. */
    @Value("${jasper.src}")
    private String JASPER_SRC;

    /**
     * Alternate jasper source.
     *
     * @return the string
     */
    private String alternateJasperSource(){
        return Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "jasper").toString();
    }

    /**
     * Crear jasper byte [ ].
     *
     * @param contenidoUnico       the contenido
     * @param fileName the parametro jasper
     * @return the byte [ ]
     * @throws JRException the jr exception
     */
    public byte[] crearJasper(Map<String,Object> contenidoUnico,String fileName) throws JRException {
        byte[] b = null;

        String sourceFileName =
                JASPER_SRC + File.separator + fileName;

        if (! (new File(sourceFileName).exists()) ) {
            String localJasperDir =
                alternateJasperSource() + File.separator + fileName;

            if (new File(localJasperDir).exists()) {
                sourceFileName = localJasperDir;
            }
        }

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(Collections.singletonList(contenidoUnico) );

        String printFileName = JasperFillManager.fillReportToFile(sourceFileName, new HashMap<>(), beanColDataSource);
        if (printFileName != null) {
            ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput( new SimpleExporterInput(printFileName) );
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport) );

            exporter.exportReport();

            b = xlsReport.toByteArray();
        }

        return b;
    }

    /**
     * Crear jasper.
     *
     * @param params the params
     * @param fields the fields
     * @param fileName the parametro jasper
     * @return the byte[]
     * @throws JRException the JR exception
     */
    public byte[] crearJasper(Map<String,Object> params, List<Map<String,Object>> fields, String fileName) throws JRException {
        byte[] b = null;

        String sourceFileName =
                JASPER_SRC + File.separator + fileName;

        if (! (new File(sourceFileName).exists()) ) {
            String localJasperDir =
                    alternateJasperSource() + File.separator + fileName;

            if (new File(localJasperDir).exists()) {
                sourceFileName = localJasperDir;
            }
        }

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(fields);

        String printFileName = JasperFillManager.fillReportToFile(sourceFileName, params, beanColDataSource);
        if (printFileName != null) {
            ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput( new SimpleExporterInput(printFileName) );
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport) );

            exporter.exportReport();

            b = xlsReport.toByteArray();
        }

        return b;
    }
    
    public JasperPrint crearJasperNoFields(Map<String,Object> params, String fileName) throws JRException {
        byte[] b = null;

        String sourceFileName =
                JASPER_SRC + File.separator + fileName;

        if (! (new File(sourceFileName).exists()) ) {
            String localJasperDir =
                    alternateJasperSource() + File.separator + fileName;

            if (new File(localJasperDir).exists()) {
                sourceFileName = localJasperDir;
            }
        }

        return JasperFillManager.fillReport(sourceFileName, params, new JREmptyDataSource());
    }
    
    public byte[] generatePdfList(List<JasperPrint> prints) throws JRException {
    	JRPdfExporter exporter = new JRPdfExporter();
    	ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
    	exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
    	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
    	exporter.exportReport();
    	return xlsReport.toByteArray();
    }

    public void saveDocToFile(byte[] content, String path) throws FileNotFoundException, IOException {
    	try (FileOutputStream fos = new FileOutputStream(path)) {
    		fos.write(content);
    	}
    }

    public JasperPrint getObjectPdf(String fileName, Map<String, Object> parameters, List<Map<String,Object>> fields) {
        JasperPrint jasperPrint = null;
        //InputStream inStream = null;

        String sourceFileName =
                JASPER_SRC + File.separator + fileName;

        if (! (new File(sourceFileName).exists()) ) {
            String localJasperDir =
                    alternateJasperSource() + File.separator + fileName;

            if (new File(localJasperDir).exists()) {
                sourceFileName = localJasperDir;
            }
        }
        try {

            File fileJasper = new File(sourceFileName);
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(fields);
            JasperReport informe = (JasperReport) JRLoader.loadObject(fileJasper);
            informe.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
            jasperPrint = JasperFillManager.fillReport(informe, parameters, beanColDataSource);

        } catch (JRException jre) {
            jre.printStackTrace();
        } 

        return jasperPrint;
    }

    public byte[] getBytesJasper(JasperPrint jasperPrint) {

        byte[] archivo = null;
        try {
            archivo = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e1) {
            log.error(e1.getMessage());
        }

        return archivo;

    }
    
    
    
    public byte[] crearByteJasper(Map<String, Object> contenido, String jasperTemplate) throws Exception {
        byte docBytes[];
        List<Map<String, Object>> fields = new ArrayList();
        fields.add(contenido);
        try {
            docBytes = crearJasper(contenido, fields, jasperTemplate);
        } catch (JRException e) {
            log.error("Error generando documento jasper", e);
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return docBytes;
    }
    
    
}
