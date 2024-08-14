package mx.mc.mapper;

import java.util.List;
import mx.mc.model.AlmacenInsumoComprometido;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface AlmacenInsumoComprometidoMapper extends GenericCrudMapper<AlmacenInsumoComprometido, String> {

    boolean registraAlmacenInsumoCopmrometidoList(List<AlmacenInsumoComprometido> comprometidoLis);

    boolean eliminaComprometidoPorIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);

    boolean registarActualizar(List<AlmacenInsumoComprometido> comprometidoLis);

}
