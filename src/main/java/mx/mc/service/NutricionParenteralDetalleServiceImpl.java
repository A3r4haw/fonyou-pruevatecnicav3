/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.NutricionParenteralDetalleMapper;
import mx.mc.model.NutricionParenteralDetalle;
import mx.mc.model.NutricionParenteralDetalleExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class NutricionParenteralDetalleServiceImpl extends GenericCrudServiceImpl<NutricionParenteralDetalle, String> implements NutricionParenteralDetalleService {

    @Autowired
    private NutricionParenteralDetalleMapper nutricionParenteralDetalleMapper;
    
    @Override
    public List<NutricionParenteralDetalleExtended> obtenerNutricionParenteralDetalleById(String idNutricionParenteral) throws Exception {
        List<NutricionParenteralDetalleExtended> listaParenteralDetalle = new ArrayList<>();
        try {
            listaParenteralDetalle = nutricionParenteralDetalleMapper.obtenerNutricionParenteralDetalleById(idNutricionParenteral);
        } catch(Exception ex) {
            throw new Exception("Error al buscar el detlle de nutricion parenteral  " + ex.getMessage());
        }
        return listaParenteralDetalle;
    }
    
}
