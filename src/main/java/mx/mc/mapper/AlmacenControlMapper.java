/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.AlmacenControl;
import mx.mc.model.AlmacenControl_Extended;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface AlmacenControlMapper extends GenericCrudMapper<AlmacenControl,String> {    
    public boolean actualizarMasivo(List<AlmacenControl> insumos);
    public boolean ctrlSurtido(AlmacenControl insumos);
    public boolean updateSolicitudByIdAlmacen(AlmacenControl insumos);
    public AlmacenControl obtenerIdPuntosControl(@Param("idAlmacen") String idAlmacen,@Param("idMedicamento") String idMedicamento);
    public AlmacenControl obtenerIdPuntosControlServicio(@Param("idAlmacen") String idAlmacen,@Param("idMedicamento") String idMedicamento);
    public boolean actualizaEstatusGabinete(@Param("idMedicamento") String idMedicamento,@Param("estatusGabinete") Integer estatusGabinete);
    
    public boolean actualizaListaAlmacenCtrl(List<AlmacenControl> insumos);
    public List<AlmacenControl_Extended> obtenerTotalInsumoAlmacen(@Param("estructuraList") List<Estructura> estructuraList,@Param("insumoList") List<String> insumoList);
}
