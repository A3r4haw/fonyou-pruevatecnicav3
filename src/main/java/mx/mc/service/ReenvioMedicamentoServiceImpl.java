/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.mapper.FoliosMapper;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ReenvioMedicamentosMapper;
import mx.mc.mapper.SurtimientoInsumoMapper;
import mx.mc.mapper.SurtimientoMapper;
import mx.mc.model.Folios;
import mx.mc.model.ReenvioMedicamento;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.util.Comunes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mcalderon
 */
@Service
public class ReenvioMedicamentoServiceImpl extends GenericCrudServiceImpl<ReenvioMedicamento, String> implements ReenvioMedicamentoService {

    @Autowired
    private ReenvioMedicamentosMapper reenvioMedicamentosMapper;

    @Autowired
    private SurtimientoMapper surtimientoMapper;

    @Autowired
    private SurtimientoInsumoMapper surtimientoInsumoMapper;

    @Autowired
    private FoliosMapper foliosMapper;

    @Autowired
    public ReenvioMedicamentoServiceImpl(GenericCrudMapper<ReenvioMedicamento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ReenvioMedicamento> obtenerDatosReenvioMedicamentos(String cama, String prescripcion, String numPaciente, Date fechaPrescripcion) throws Exception {
        try {
            return reenvioMedicamentosMapper.obtenerDatosReenvioMedicamentos(cama, prescripcion, numPaciente, fechaPrescripcion);
        } catch (Exception e) {
            throw new Exception("Error al obtener los Datos" + e.getMessage());
        }
    }

    @Override
    public List<ReenvioMedicamento> obtenerMotivosReenvio() throws Exception {
        try {
            return reenvioMedicamentosMapper.obtenerMotivosReenvio();
        } catch (Exception ex) {
            throw new Exception("Error al Obtener los Motivos de Reenvio" + ex.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean surtirReenvioMedicamento(Surtimiento surtimientoSelect, List<SurtimientoInsumo> surtimientoInsumoList) throws Exception {
        boolean res = false;
        try {
            int tipoDoc = TipoDocumento_Enum.REENVIO_MEDICAMENTO.getValue();
            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
            surtimientoSelect.setFolio(Comunes.generaFolio(folio));
            folio.setSecuencia(Comunes.separaFolio(surtimientoSelect.getFolio()));
            res = foliosMapper.actualizaFolios(folio);
            if (!res) {
                throw new Exception("Error al registrar Folio de Surtimiento. ");
            } else {
                res = surtimientoMapper.insertar(surtimientoSelect);
                if (!res) {
                    throw new Exception("Error al insertar el Surtimiento");
                } else {
                    res = surtimientoInsumoMapper.registraSurtimientoInsumoList(surtimientoInsumoList);
                    if (!res) {
                        throw new Exception("Error al insertar el SurtimientoInsumo");
                    }
                }
            }

        } catch (Exception e) {
            throw new Exception("Error al Reenviar el Medicamento " + e.getMessage());
        }
        return res;
    }

}
