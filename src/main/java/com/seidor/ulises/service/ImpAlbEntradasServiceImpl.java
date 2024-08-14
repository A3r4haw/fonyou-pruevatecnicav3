/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.mapper.ImpAlbEntradasMapper;
import com.seidor.ulises.mapper.ImpBCMapper;
import com.seidor.ulises.mapper.ImpLineasAlbEntradasMapper;
import mx.mc.service.*;
import com.seidor.ulises.model.ImpAlbEntradas;
import com.seidor.ulises.model.ImpBC;
import com.seidor.ulises.model.ImpLineasAlbEntradas;
import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apalacios
 */
@Service
public class ImpAlbEntradasServiceImpl extends GenericCrudServiceImpl<ImpAlbEntradas, String> implements ImpAlbEntradasService {
    
    @Autowired
    private ImpAlbEntradasMapper impAlbEntradasMapper;
    @Autowired
    private ImpLineasAlbEntradasMapper impLineasAlbEntradasMapper;
    @Autowired
    private ImpBCMapper impBCMapper;

    @Autowired
    public ImpAlbEntradasServiceImpl(GenericCrudMapper<ImpAlbEntradas, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarAlbaran(ImpAlbEntradas impAlbEntradas, List<ImpLineasAlbEntradas> listImpLineasAlbEntradas) throws Exception {
        boolean res = false;
        try {
            res = impLineasAlbEntradasMapper.registraImpLineasAlbEntradasList(listImpLineasAlbEntradas);
            if (res) {
                res = impAlbEntradasMapper.insertar(impAlbEntradas);
                if (res) {
                    List<ImpBC> listaCodigos = new ArrayList<>();
                    for (ImpLineasAlbEntradas lineaAlbaran : listImpLineasAlbEntradas) {
                        ImpBC barCode = new ImpBC(lineaAlbaran.getArticulo(), lineaAlbaran.getLote(), lineaAlbaran.getFechaCaducidad());
                        listaCodigos.add(barCode);
                    }
                    res = impBCMapper.registraImpBCList(listaCodigos);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al agregar el Medicamento en Ulises");
        }
        return res;
    }
}
