//package mx.mc.service;
//
//import java.util.List;
//import mx.mc.enums.TipoDocumento_Enum;
//import mx.mc.mapper.FoliosMapper;
//import mx.mc.mapper.SolucionDistribuyeDetalleMapper;
//import mx.mc.mapper.SolucionDistribuyeMapper;
//import mx.mc.model.Folios;
//import mx.mc.model.SolucionDistribuye;
//import mx.mc.model.SolucionDistribuyeDetalle;
//import mx.mc.model.NotaDispenColectDetalle_Extended;
//import mx.mc.util.Comunes;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// *
// * @author Cervanets
// */
//@Service
//public class SolucionDistribuyeServiceImpl extends GenericCrudServiceImpl<SolucionDistribuye, String> implements SolucionDistribuyeService {
//    
//    @Autowired
//    private SolucionDistribuyeMapper solucionDistribuyeMapper;
//    @Autowired
//    private SolucionDistribuyeDetalleMapper solucionDistribuyeDetalleMapper;
//    @Autowired
//    private FoliosMapper foliosMapper;
//
//    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @Override
//    public boolean registraListaDistribucion(SolucionDistribuye sd, List<NotaDispenColectDetalle_Extended> sdd) throws Exception {
//        boolean res = false;
//        try {
//            int tipoDoc = TipoDocumento_Enum.LISTA_DISTRIBUCION.getValue();
//            Folios folio = foliosMapper.obtenerPrefixPorDocument(tipoDoc);
//            String folioDoc = Comunes.generaFolio(folio);
//            sd.setFolio(folioDoc);
//            folio.setSecuencia(Comunes.separaFolio(folioDoc));
//            res = foliosMapper.actualizaFolios(folio);
//            if (!res) {
//                throw new Exception("Error al actualizar el folio de la lista de distribución. " );
//                
//            } else {
//                sd.setFolio(folioDoc);
//                res = solucionDistribuyeMapper.insertar(sd);
//                if (!res){
//                    throw new Exception("Error Registrar lista de distribución. " );
//                } else {
//                    res = solucionDistribuyeDetalleMapper.registraLista(sdd);
//                    if (!res){
//                        throw new Exception("Error Registrar detalle de lista de distribución. " );
//                    }
//                }
//            }
//            
//        } catch (Exception ex) {
//            throw new Exception("Error Registrar Lista de Distribución. " + ex.getMessage());
//        }
//        return res;
//    }
//
//    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    @Override
//    public boolean actualizaListaDistribucion(SolucionDistribuye sd, List<NotaDispenColectDetalle_Extended> sdd) throws Exception {
//        boolean res = false;
//        try {
//            res = super.actualizar(sd);
//            if (!res){
//                throw new Exception("Error actualizar lista de distribución. " );
//            } else {
//                res = solucionDistribuyeDetalleMapper.actualizaLista(sdd);
//                if (!res){
//                    throw new Exception("Error actualizar detalle de lista de distribución. " );
//                }
//            }
//            
//        } catch (Exception ex) {
//            throw new Exception("Error actualizar Lista de Distribución. " + ex.getMessage());
//        }
//        return res;
//    }
//    
//}
