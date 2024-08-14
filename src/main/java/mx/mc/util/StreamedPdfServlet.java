/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.util;

import java.io.IOException;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.mc.magedbean.SesionMB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author apalacios
 */
@WebServlet("/Reportes")
public class StreamedPdfServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamedPdfServlet.class);
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession httpSession = request.getSession();
            SesionMB sesion = (SesionMB)httpSession.getAttribute("sesionMB");
            if (sesion != null) {
                byte[] content = sesion.getReportStream();
                if (content != null) {
                    response.setContentType("application/pdf");
                    response.setContentLength(content.length);            
                    response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", sesion.getReportName()));
                    response.getOutputStream().write(content);
                }
            }
        }
        catch (IOException ex) {
            LOGGER.error("Error al obtener el contenido. {}", ex.getMessage());
        }
    }

}