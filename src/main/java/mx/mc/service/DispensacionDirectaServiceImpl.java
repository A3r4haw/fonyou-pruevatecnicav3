/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.EstructuraMapper;
import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.PrescripcionInsumoMapper;
import mx.mc.mapper.PrescripcionMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.model.Folios;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.util.Comunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class DispensacionDirectaServiceImpl implements DispensacionDirectaService {
    
    @Autowired
    private PrescripcionMapper prescripcionMapper;
    
    @Autowired
    private PrescripcionInsumoMapper prescripcionInsumoMapper;
    
    @Autowired
    private SurtimientoMapper surtimientoMapper;
    
    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;
    
    @Autowired
    private EstructuraMapper estructuraMapper;
    
    @Autowired
    private FoliosMapper foliosMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarPrescripcion(Prescripcion prescripcion, PrescripcionInsumo prescripcionInsumo, Surtimiento surtimiento, SurtimientoInsumo surtimientoInsumo) throws Exception {
         boolean res = false;
        try {
            int tipoDoc = TipoDocumento_Enum.PRESCRIPCION.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            
            prescripcion.setFolio(Comunes.generaFolio(folio));
            folio.setSecuencia(Comunes.separaFolio(prescripcion.getFolio()));
            res = foliosMapper.actualizaFolios(folio);
            if (!res) {
                throw new Exception("Error al registrar Folio de Prescripción. ");

            } else {
                res = prescripcionMapper.insertar(prescripcion);
                if (!res) {
                    throw new Exception("Error al registrar Prescripción. ");
                } else {
                    res = prescripcionInsumoMapper.insertar(prescripcionInsumo);
                    if (!res) {
                        throw new Exception("Error al registrar Prescripción Insumo. ");
                    } else {
                        int docTipo = TipoDocumento_Enum.SURTIMIENTO_DE_PRESCRIPCION.getValue();
                        Folios folioSurtimiento = foliosMapper.obtenerPrefixPorDocument(docTipo);
                        
                        surtimiento.setFolio(Comunes.generaFolio(folioSurtimiento));
                        folioSurtimiento.setSecuencia(Comunes.separaFolio(surtimiento.getFolio()));
                        res = foliosMapper.actualizaFolios(folioSurtimiento);
                        if (!res) {
                            throw new Exception("Error al registrar Folio de Surtimiento. ");
                        } else {
                            res = surtimientoMapper.insertar(surtimiento);
                            if (!res) {
                                throw new Exception("Error al registrar Surtimiento. ");
                            } else {
                                res = surtimientoInsumoMapper.insertar(surtimientoInsumo);
                                if (!res) {
                                    throw new Exception("Error al registrar Surtimiento Insumo. ");
                                }
                            }
                        }                        
                    }
                }
            }            
        } catch (Exception ex) {
            throw new Exception("Error registrar Prescripción. " + ex.getMessage());
        }
        return res;
    }

    @Override
    public String obtenerclaveEstructuraPeriferico(String idEstructura) throws Exception {
        String claveEstructuraPeriferico;
        try {
            
            claveEstructuraPeriferico =  estructuraMapper.obtenerclaveEstructuraPeriferico(idEstructura);
        } catch (Exception ex) {
            throw new Exception("Error al buscar el servicio de la estructura " + ex.getMessage());
        }
        return claveEstructuraPeriferico;
    }

    @Override
    public String obtenerFolioPrescripcion() throws Exception {
        String folio = null;
        try {
            int tipoDoc = TipoDocumento_Enum.PRESCRIPCION.getValue();
            Folios fol = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            folio = Comunes.generaFolio(fol);
        } catch (Exception ex) {
            throw new Exception("Error al obtener folio de prescripción " + ex.getMessage());
        }
        return folio;
    }
    
}
