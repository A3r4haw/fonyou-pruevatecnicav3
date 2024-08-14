USE `unidosisDev`;

-- ------------------------------------------------------------------------------
-- SEGURIDAD
ALTER TABLE `acciones` ADD UNIQUE INDEX `accionesNombre_idx_Uni` (`nombre` ASC);

ALTER TABLE `estructura` ADD UNIQUE INDEX `estructuraNombre_idx_Uni` (`nombre` ASC);

ALTER TABLE `modulos` ADD UNIQUE INDEX `modulosNombre_idx_Uni` (`modulo` ASC);
ALTER TABLE `modulos` ADD COLUMN `icon` VARCHAR(20) NULL AFTER `orden`;
 

ALTER TABLE `roles` ADD UNIQUE INDEX `rolesNombre_idx_U` (`nombre` ASC);

ALTER TABLE `transacciones` ADD UNIQUE INDEX `transaccionesNombre_idx_Uni` (`nombre` ASC);
ALTER TABLE `transacciones` ADD UNIQUE INDEX `transaccionesCodigo_idx_Uni` (`codigo` ASC);
ALTER TABLE `transacciones` ADD UNIQUE INDEX `transaccionesurl_idx_Uni` (`url` ASC);
ALTER TABLE `transacciones` ADD COLUMN `icon` VARCHAR(20) NULL AFTER `codigo`;

-- ------------------------------------------------------------------------------
-- USUARIOS

ALTER TABLE `usuarios` ADD UNIQUE INDEX `nombreUsuario_idx_Uni` (`nombreUsuario` ASC);
ALTER TABLE `usuarios` ADD UNIQUE INDEX `correoElectronico_idx_Uni` (`correoElectronico` ASC);

ALTER TABLE `usuarios` DROP COLUMN `fechaRegistro`;
ALTER TABLE `usuarios` CHANGE COLUMN `ultimoIngreso` `ultimoIngreso` DATETIME NULL ;
ALTER TABLE `usuarios` CHANGE COLUMN `idEstructura` `idEstructura` VARCHAR(36) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL ;


-- ------------------------------------------------------------------------------
-- = = = = = = = = ALTER MEDICAMENTO = = = = = = = =

ALTER TABLE `medicamento` ADD UNIQUE KEY `claveInstitucionalFx_idx` (`claveInstitucional`);
ALTER TABLE `medicamento` ADD INDEX `idSustanciaActivaFk_idx` (`sustanciaActiva` ASC);
ALTER TABLE `medicamento` ADD INDEX `idUnidadConcentracionFk_idx` (`idUnidadConcentracion` ASC);
ALTER TABLE `medicamento` ADD INDEX `idPresentacionEntradaFk_idx` (`idPresentacionEntrada` ASC);
ALTER TABLE `medicamento` ADD INDEX `idPresentacionSalidaFk_idx` (`idPresentacionSalida` ASC);
ALTER TABLE `medicamento` ADD INDEX `idCategoriaFk_idx` (`idCategoria` ASC);
ALTER TABLE `medicamento` ADD INDEX `idSubcategoriaFk_idx` (`idSubcategoria` ASC);
ALTER TABLE `medicamento` ADD INDEX `idViaAdministracionFk_idx` (`idViaAdministracion` ASC);
ALTER TABLE `medicamento` ADD INDEX `insertIdUsuarioFk_idx` (`insertIdUsuario` ASC);


ALTER TABLE `medicamento` ADD CONSTRAINT `idCategoria_Fk` FOREIGN KEY (`idCategoria`) REFERENCES `categoriaMedicamento` (`idCategoriaMedicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `medicamento` ADD CONSTRAINT `idPresentacionEntrada_Fk` FOREIGN KEY (`idPresentacionEntrada`) REFERENCES `presentacionMedicamento` (`idPresentacion`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `medicamento` ADD CONSTRAINT `idPresentacionSalida_Fk` FOREIGN KEY (`idPresentacionSalida`) REFERENCES `presentacionMedicamento` (`idPresentacion`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `medicamento` ADD CONSTRAINT `idSubcategoria_Fk` FOREIGN KEY (`idSubcategoria`) REFERENCES `subcategoriaMedicamento` (`idSubcategoriaMedicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `medicamento` ADD CONSTRAINT `idSustanciaActiva_Fk` FOREIGN KEY (`sustanciaActiva`) REFERENCES `sustanciaActiva` (`idSustanciaActiva`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `medicamento` ADD CONSTRAINT `idUnidadConcentracion_Fk` FOREIGN KEY (`idUnidadConcentracion`) REFERENCES `unidadConcentracion` (`idUnidadConcentracion`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `medicamento` ADD CONSTRAINT `idUsuario_Fk` FOREIGN KEY (`insertIdUsuario`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `medicamento` ADD CONSTRAINT `idViaAdministracion_Fk` FOREIGN KEY (`idViaAdministracion`) REFERENCES `viaAdministracion` (`idViaAdministracion`) ON DELETE NO ACTION ON UPDATE NO ACTION;



ALTER TABLE `categoriaMedicamento` ADD UNIQUE INDEX `nombreCategoriaMedicamento_idx_Uni` (`nombreCategoriaMedicamento` ASC);

ALTER TABLE `presentacionMedicamento` ADD UNIQUE INDEX `nombrePresentacion_idx_Uni` (`nombrePresentacion` ASC);

ALTER TABLE `unidadConcentracion` ADD UNIQUE INDEX `nombreunidadConcentracion_idx_Uni` (`nombreUnidadConcentracion` ASC);

ALTER TABLE `viaAdministracion` ADD UNIQUE INDEX `nombreViaAdministracion_idx_Uni` (`nombreViaAdministracion` ASC);



-- ------------------------------------------------------------------------------
-- PACIENTES

ALTER TABLE `grupoCatalogoGeneral` ADD UNIQUE INDEX `nombreGrupo_idx_Uni` (`nombreGrupo` ASC);


ALTER TABLE `catalogoGeneral` ADD INDEX `idGrupoFk_idx` (`idGrupo` ASC);
ALTER TABLE `catalogoGeneral` ADD CONSTRAINT `idGrupoFk_idx` FOREIGN KEY (`idGrupo`) REFERENCES `grupoCatalogoGeneral` (`idGrupoCatalogoGeneral`) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `paciente` ADD UNIQUE KEY `pacienteNumero_UNIQUE` (`pacienteNumero`);
ALTER TABLE `paciente` ADD UNIQUE KEY `curp_UNIQUE` (`curp`);
ALTER TABLE `paciente` ADD UNIQUE KEY `rfc_UNIQUE` (`rfc`);

ALTER TABLE `paciente` ADD INDEX `idEstatusPaciente_fk_idx` (`idEstatusPaciente` ASC);
ALTER TABLE `paciente` ADD CONSTRAINT `idEstatusPaciente_fk`FOREIGN KEY (`idEstatusPaciente`) REFERENCES `estatusPaciente` (`idEstatusPaciente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

    

ALTER TABLE `pacienteDomicilio` ADD INDEX `idPaciente_fk_idx` (`idPaciente` ASC);
ALTER TABLE `pacienteDomicilio` ADD CONSTRAINT `idPaciente_fk` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `pacienteDomicilio` ADD INDEX `idPais_fk_idx` (`idPais` ASC);
ALTER TABLE `pacienteDomicilio` ADD CONSTRAINT `idPais_fk` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `pacienteResponsable` ADD INDEX `idPaciente_fk_idx` (`idPaciente` ASC);


ALTER TABLE `sepomex` CHANGE COLUMN `id_cp` `idCp` INT(11) NOT NULL AUTO_INCREMENT ;
ALTER TABLE `sepomex` CHANGE COLUMN `d_codigo` `dCodigo` VARCHAR(5)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `d_asenta` `dAsenta` VARCHAR(120)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `d_tipo_asenta` `dTipoAsenta` VARCHAR(120)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `D_mnpio` `dMnpio` VARCHAR(120)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `d_estado` `dEstado` VARCHAR(120)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `d_ciudad` `dCiudad` VARCHAR(120)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `d_CP` `dCp` VARCHAR(5)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `c_estado` `cEstado` VARCHAR(2)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `c_oficina` `cOficina` VARCHAR(5)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `c_CP` `cCp` VARCHAR(5)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `c_tipo_asenta` `cTipoAsenta` VARCHAR(2)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `c_mnpio` `cMnpio` VARCHAR(3)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `id_asenta_cpcons` `idAsentaCpcons` VARCHAR(4)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `d_zona` `dZona` VARCHAR(15)  NOT NULL ;
ALTER TABLE `sepomex` CHANGE COLUMN `c_cve_ciudad` `cCveCiudad` VARCHAR(2)  NOT NULL ;



ALTER TABLE `sepomex` ADD INDEX `nombreEstado_idx` (`dEstado` ASC);
ALTER TABLE `sepomex` ADD INDEX `nombreMunicipio_idx` (`dMnpio` ASC);
ALTER TABLE `sepomex` ADD INDEX `nombreCiudad` (`dCiudad` ASC);
ALTER TABLE `sepomex` ADD INDEX `descCp_idx` (`dCP` ASC);

ALTER TABLE `sepomex` ADD INDEX `idEstado_idx` (`cEstado` ASC);
ALTER TABLE `sepomex` ADD INDEX `idMunicipio_idx` (`cMnpio` ASC);
ALTER TABLE `sepomex` ADD INDEX `cp_idx` (`cCP` ASC);
ALTER TABLE `sepomex` ADD INDEX `idCiudad_idx` (`cCveCiudad` ASC);

ALTER TABLE `pais` CHANGE COLUMN `N3` `N3` INT(11) NULL ;

-- ------------------------------------------------------------------------------
-- PRESCRIPCIONES

ALTER TABLE `estatusPaciente` ADD UNIQUE KEY `estatusPaciente_idx_uni` (`estatusPaciente`);


ALTER TABLE `prescripcion` ADD UNIQUE KEY `folioPrescripcion_idx_Uni` (`folio`);
ALTER TABLE `prescripcion` ADD INDEX `idEstructura_idx` (`idEstructura` ASC);
ALTER TABLE `prescripcion` ADD INDEX `idMedico_idx` (`idMedico` ASC);
ALTER TABLE `prescripcion` ADD INDEX `idPaciente_idx` (`idPaciente` ASC);
ALTER TABLE `prescripcion` ADD INDEX `idPacienteServicio_idx` (`idPacienteServicio` ASC);

ALTER TABLE `prescripcion` ADD CONSTRAINT `idMedicoFk` FOREIGN KEY (`idMedico`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------
-- todo
ALTER TABLE `prescripcion` ADD CONSTRAINT `idPacienteFk` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `prescripcion` ADD CONSTRAINT `idPacienteServicioFk` FOREIGN KEY (`idPacienteServicio`) REFERENCES `pacienteServicio` (`idPacienteServicio`) ON DELETE NO ACTION ON UPDATE NO ACTION;  
ALTER TABLE `prescripcion` ADD CONSTRAINT `idEstructuraFk` FOREIGN KEY (`idEstructura`) REFERENCES `estructura` (`idEstructura`) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- todo
-- ----------

ALTER TABLE `diagnosticoPaciente` ADD INDEX `idDiagnosticoFk_idx` (`idDiagnostico` ASC);
ALTER TABLE `diagnosticoPaciente` ADD INDEX `idUsuarioDiagnosticoFk_idx` (`idUsuarioDiagnostico` ASC);
ALTER TABLE `diagnosticoPaciente` ADD INDEX `idPrescripcionFk_idx` (`idPrescripcion` ASC);
ALTER TABLE `diagnosticoPaciente` ADD CONSTRAINT `idUsuarioDiagnosticoFk` FOREIGN KEY (`idUsuarioDiagnostico`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `diagnosticoPaciente` ADD CONSTRAINT `idDiagnosticoFk` FOREIGN KEY (`idDiagnostico`) REFERENCES `diagnostico` (`idDiagnostico`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `diagnosticoPaciente` ADD CONSTRAINT `idPrescripcionFk` FOREIGN KEY (`idPrescripcion`) REFERENCES `prescripcion` (`idPacienteServicio`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `diagnosticoPaciente` CHANGE COLUMN `idPrescripcion` `idPrescripcion` VARCHAR(36) CHARACTER SET 'utf8' NOT NULL ;
ALTER TABLE `diagnosticoPaciente` CHANGE COLUMN `fechaDiagnostico` `fechaDiagnostico` DATETIME NOT NULL ;
ALTER TABLE `diagnosticoPaciente` CHANGE COLUMN `idUsuarioDiagnostico` `idUsuarioDiagnostico` VARCHAR(36) CHARACTER SET 'utf8' NOT NULL ;
ALTER TABLE `diagnosticoPaciente` CHANGE COLUMN `insertFecha` `insertFecha` DATETIME NOT NULL ;
ALTER TABLE `diagnosticoPaciente` CHANGE COLUMN `insertIdUsuario` `insertIdUsuario` VARCHAR(36) CHARACTER SET 'utf8' NOT NULL ;
ALTER TABLE `diagnosticoPaciente` CHANGE COLUMN `idEstatusDiagnostico` `idEstatusDiagnostico` INT(2) NOT NULL ;



ALTER TABLE `prescripcionInsumo` ADD INDEX `idPrescripcion_idx` (`idPrescripcion` ASC);
ALTER TABLE `prescripcionInsumo` ADD CONSTRAINT `idPrescripcionFk` FOREIGN KEY (`idPrescripcion`) REFERENCES `prescripcion` (`idPrescripcion`) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `prescripcionInsumo` ADD INDEX `idInsumo_idx` (`idInsumo` ASC);
ALTER TABLE `prescripcionInsumo` ADD CONSTRAINT `idInsumoFk`  FOREIGN KEY (`idInsumo`) REFERENCES `medicamento` (`idMedicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `prescripcionInsumo` ADD COLUMN `indicaciones` VARCHAR(100) NULL AFTER `idEstatusPrescripcion`;



-- ------------------------------------------------------------------------------
-- CADENA DE SUMINISTRO

-- ALTER TABLE `almacen` ADD INDEX `idTipoAlmacen_idx` (`idTipoAlmacen` ASC);
-- ALTER TABLE `almacen` ADD CONSTRAINT `idTipoAlmacen` FOREIGN KEY (`idTipoAlmacen`) REFERENCES `tipoAlmacen` (`idTipoAlmacen`) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `almacenPuntosControl` ADD INDEX `idAlmacenFk_idx` (`idAlmacen` ASC);
ALTER TABLE `almacenPuntosControl` ADD CONSTRAINT `idAlmacenFk` FOREIGN KEY (`idAlmacen`) REFERENCES `almacen` (`idAlmacen`) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `almacenPuntosControl` ADD UNIQUE INDEX `folioOrdenCompra_idx_uni` (`folioOrdenCompra` ASC);
ALTER TABLE `almacenPuntosControl` ADD INDEX `idReabasto_idx` (`idReabasto` ASC);
ALTER TABLE `almacenPuntosControl` ADD INDEX `idProveedor` (`idProveedor` ASC);
ALTER TABLE `almacenPuntosControl` ADD INDEX `idEstatusOrdenCompra` (`idEstatusOrdenCompra` ASC);


CREATE INDEX `idTipoOrden_idx` ON `reabasto` (`idTipoOrden` ASC);
CREATE INDEX `idEstatusReabasto_idx` ON `reabasto` (`idEstatusReabasto` ASC);
CREATE INDEX `idProveedor_idx` ON `reabasto` (`idProveedor` ASC);
CREATE INDEX `idEstructuraFk_idx` ON `reabasto` (`idEstructura` ASC);
CREATE INDEX `idUsuarioSolicitudFk_idx` ON `reabasto` (`idUsuarioSolicitud` ASC);

ALTER TABLE `reabasto` ADD CONSTRAINT `idTipoOrdenFk` FOREIGN KEY (`idTipoOrden`) REFERENCES `tipoOrden` (`idTipoOrden`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `reabasto` ADD CONSTRAINT `idEstatusReabastoFk` FOREIGN KEY (`idEstatusReabasto`) REFERENCES `estatusReabasto` (`idEstatusReabasto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- -------------------------------------
-- todo agregar
ALTER TABLE `reabasto` ADD CONSTRAINT `idProveedorFk` FOREIGN KEY (`idProveedor`) REFERENCES `proveedor` (`idProveedor`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `reabasto` ADD CONSTRAINT `idEstructuraFk` FOREIGN KEY (`idEstructura`) REFERENCES `estructura` (`idEstructura`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `reabasto` ADD CONSTRAINT `idUsuarioSolicitudFk` FOREIGN KEY (`idUsuarioSolicitud`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- -------------------------------------

CREATE INDEX `idReabastoFk_idx` ON `reabastoInsumo` (`idReabasto` ASC);
CREATE INDEX `idInsumoFk_idx` ON `reabastoInsumo` (`idInsumo` ASC);


-- -------------------------------------
-- todo agregar
ALTER TABLE `reabastoInsumo` ADD CONSTRAINT `idReabastoFk` FOREIGN KEY (`idReabasto`) REFERENCES `reabasto` (`idReabasto`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `reabastoInsumo` ADD CONSTRAINT `idInsumoFk` FOREIGN KEY (`idInsumo`) REFERENCES `medicamento` (`idMedicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- -------------------------------------


CREATE INDEX `idEstructuraFk_idx` ON `almacenInsumoExistencia` (`idEstructura` ASC);
CREATE INDEX `idInsumoFk_idx` ON `almacenInsumoExistencia` (`idInsumo` ASC);

ALTER TABLE `almacenInsumoExistencia` DROP COLUMN `cantidadComprometida`;
ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `lote` VARCHAR(20) NOT NULL AFTER `idInsumo`;
ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `activo` INT(2) NOT NULL AFTER `lote`;



-- -------------------------------------
-- todo agregar
ALTER TABLE `almacenInsumoExistencia` ADD CONSTRAINT `idEstructuraFk` FOREIGN KEY (`idEstructura`) REFERENCES `estructura` (`idEstructura`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `almacenInsumoExistencia` ADD CONSTRAINT `idInsumoFk` FOREIGN KEY (`idInsumo`) REFERENCES `medicamento` (`idMedicamento`) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- -------------------------------------


ALTER TABLE `surtimiento` CHANGE COLUMN `idSurtimiento` `idSurtimiento` VARCHAR(36) NOT NULL ;
ALTER TABLE `surtimiento` ADD INDEX `idSurtimientoFk_idx` (`idPrescripcion` ASC);
ALTER TABLE `surtimiento` ADD UNIQUE INDEX `idSurtimiento_UNIQUE` (`idSurtimiento` ASC);
ALTER TABLE `surtimiento` ADD CONSTRAINT `idSurtimientoFk` FOREIGN KEY (`idPrescripcion`) REFERENCES `prescripcion` (`idPrescripcion`) ON DELETE NO ACTION ON UPDATE NO ACTION;
  

ALTER TABLE `surtimientoInsumo` ADD INDEX `idSurtimientoFk_idx` (`idSurtimiento` ASC);
ALTER TABLE `surtimientoInsumo` ADD INDEX `idPrescripcionInsumoFk_idx` (`idPrescripcionInsumo` ASC);


-- --------------------------------
-- todo
ALTER TABLE `surtimientoInsumo` ADD CONSTRAINT `idPrescripcionInsumoFk` FOREIGN KEY (`idPrescripcionInsumo`) REFERENCES `prescripcionInsumo` (`idPrescripcionInsumo`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `surtimientoInsumo` ADD CONSTRAINT `idSurtimientoFk` FOREIGN KEY (`idSurtimiento`) REFERENCES `surtimiento` (`idSurtimiento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- todo
-- ------------------------------------



ALTER TABLE `tipoMotivo` ADD INDEX `idTipoMovimiento_idx` (`idTipoMovimiento` ASC);
ALTER TABLE `tipoMotivo` ADD CONSTRAINT `idTipoMovimientoFk` FOREIGN KEY (`idTipoMovimiento`) REFERENCES `tipoMovimiento` (`idTipoMovimiento`) ON DELETE NO ACTION  ON UPDATE NO ACTION;


ALTER TABLE `movimientoInventario` ADD INDEX `idTipoMotivo_idx` (`idTipoMotivo` ASC);
ALTER TABLE `movimientoInventario` ADD INDEX `idUsuarioMovimiento_idx` (`idUsuarioMovimiento` ASC);
ALTER TABLE `movimientoInventario` ADD INDEX `idEstrutcuraOrigen_idx` (`idEstrutcuraOrigen` ASC);
ALTER TABLE `movimientoInventario` ADD INDEX `folioDocumento_idx` (`folioDocumento` ASC);
ALTER TABLE `movimientoInventario` ADD CONSTRAINT `idTipoMotivo` FOREIGN KEY (`idTipoMotivo`) REFERENCES `tipoMotivo` (`idTipoMotivo`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `movimientoInventario` ADD CONSTRAINT `idUsuarioMovimiento` FOREIGN KEY (`idUsuarioMovimiento`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `movimientoInventario` ADD CONSTRAINT `idEstrutcuraOrigen` FOREIGN KEY (`idEstrutcuraOrigen`) REFERENCES `estructura` (`idEstructura`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `movimientoInventario` ADD CONSTRAINT `folioDocumento` FOREIGN KEY (`folioDocumento`) REFERENCES `documentoPadre` (`folioDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `existenciaInicial` BIGINT(20) NOT NULL AFTER `cantidadActual`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `fechaIngreso` DATETIME NOT NULL AFTER `existenciaInicial`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `idPresentacion` INT(2) NOT NULL AFTER `fechaIngreso`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `lote` VARCHAR(45) NOT NULL AFTER `idPresentacion`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `fechaCaducidad` DATETIME NOT NULL AFTER `lote`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `costo` DECIMAL(18,2) NOT NULL AFTER `fechaCaducidad`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `costoUnidosis` DECIMAL(18,2) NOT NULL AFTER `costo`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `registroSsa` VARCHAR(45) DEFAULT NULL AFTER `costoUnidosis`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `dictamenMedico` VARCHAR(45) DEFAULT NULL AFTER `registroSsa`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `idProveedor` INT(11) DEFAULT NULL AFTER `dictamenMedico`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `idFabricante` VARCHAR(36) DEFAULT NULL AFTER `idProveedor`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `idMarca` INT(11) DEFAULT NULL AFTER `idFabricante`;
-- ALTER TABLE `almacenInsumoExistencia` ADD COLUMN `accesorios` text DEFAULT NULL AFTER `idMarca`;


ALTER TABLE `inventario` ADD KEY `idEstructuraFk_idx` (`idEstructura`);
ALTER TABLE `inventario` ADD KEY `idInsumoFk_idx` (`idInsumo`);
ALTER TABLE `inventario` ADD KEY `idPresentacionFk_idx` (`idPresentacion`);


-- ----------------------------------------------------------------------------------------
--  VISITA HOSPITALARIA , ASIGNACIÓN A SERVICIO, ASIGNACIÓN DE UBICACION (CAMA, SILLA, ETC)

ALTER TABLE `visita` ADD INDEX `idPaciente_idx` (`idPaciente` ASC);
ALTER TABLE `visita` ADD INDEX `idUsuarioIngresa_idx` (`idUsuarioIngresa` ASC);
ALTER TABLE `visita` ADD CONSTRAINT `idPacienteFk` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `visita` ADD CONSTRAINT `idUsuarioIngresaFk` FOREIGN KEY (`idUsuarioIngresa`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;
  

ALTER TABLE `pacienteServicio` ADD INDEX `idVisita_idx` (`idVisita` ASC);
ALTER TABLE `pacienteServicio` ADD INDEX `idEstructura_idx` (`idEstructura` ASC);
ALTER TABLE `pacienteServicio` ADD INDEX `idUsuarioAsignacionInicio_idx` (`idUsuarioAsignacionInicio` ASC);
ALTER TABLE `pacienteServicio` ADD CONSTRAINT `idVisitaFk` FOREIGN KEY (`idVisita`) REFERENCES `visita` (`idVisita`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `pacienteServicio` ADD CONSTRAINT `idEstructuraFk` FOREIGN KEY (`idEstructura`) REFERENCES `estructura` (`idEstructura`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `pacienteServicio` ADD CONSTRAINT `idUsuarioAsignacionInicioFk` FOREIGN KEY (`idUsuarioAsignacionInicio`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE `pacienteUbicacion` ADD INDEX `idPacienteServicioFk_idx` (`idPacienteServicio` ASC);
ALTER TABLE `pacienteUbicacion` ADD INDEX `idUsuarioUbicaInicio_idx` (`idUsuarioUbicaInicio` ASC);
ALTER TABLE `pacienteUbicacion` ADD CONSTRAINT `idPacienteServicioFk` FOREIGN KEY (`idPacienteServicio`) REFERENCES `pacienteServicio` (`idPacienteServicio`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `pacienteUbicacion` ADD CONSTRAINT `idUsuarioUbicaInicio` FOREIGN KEY (`idUsuarioUbicaInicio`) REFERENCES `usuarios` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

