/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.ConfirmacionReporteFoliadoMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.ConfirmacionReporteFoliado;
import mx.mc.model.Folios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class ConfirmacionReporteFoliadoServiceImpl extends GenericCrudServiceImpl<ConfirmacionReporteFoliado, String> implements ConfirmacionReporteFoliadoService {
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    private ConfirmacionReporteFoliadoMapper confirmacionReporteFoliadoMapper;
    
    @Autowired
    public ConfirmacionReporteFoliadoServiceImpl(GenericCrudMapper<ConfirmacionReporteFoliado, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarFolioReporteDiario(Folios folio, ConfirmacionReporteFoliado reporteFoliado) throws Exception {
        boolean resp = false;
        
        resp = foliosMapper.actualizaFolios(folio);
        if(!resp) {
            throw new Exception("Error al actualizar Folio de reporte Diario. ");
        } else {
            resp = confirmacionReporteFoliadoMapper.insertar(reporteFoliado);
            if(!resp) {
                throw new Exception("Error al registrar Folio de Reporte Diario. ");
            }
        }
        return resp;
    }
    
}
