DROP database IF EXISTS `unidosisDev` ;

CREATE SCHEMA IF NOT EXISTS `unidosisDev` DEFAULT CHARACTER SET utf8  ;
USE `unidosisDev`;



-- ------------------------------------------------------------------------------
-- SEGURIDAD

DROP TABLE IF EXISTS `acciones`;
CREATE TABLE `acciones` (
  `idAccion` varchar(36) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `descripcion` varchar(45) DEFAULT '',
  `activo` int(11) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idAccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='ALMACENA ACCIONES POSIBLES DENTRO DE UNA TRANSACCION';



DROP TABLE IF EXISTS `estructura`;
CREATE TABLE `estructura` (
  `idEstructura` varchar(36) NOT NULL,
  `idEstructuraPadre` varchar(36) DEFAULT NULL,
  `nombre` varchar(60) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `activa` int(1) NOT NULL,
  `idTipoInstitucion` int(2) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idEstructura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `idEstructura` varchar(36) NOT NULL,
  `idTransaccion` varchar(36) NOT NULL,
  `idUsuario` varchar(36) NOT NULL,
  `fechaHora` datetime NOT NULL,
  `nivel` varchar(10) NOT NULL,
  `ip` varchar(45) NOT NULL,
  `so` varchar(45) NOT NULL,
  `cliente` varchar(45) NOT NULL,
  `texto` varchar(299) NOT NULL,
  PRIMARY KEY (`idEstructura`,`idTransaccion`,`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='REGISTRA LOGS';



DROP TABLE IF EXISTS `modulos`;
CREATE TABLE `modulos` (
  `idModulo` varchar(36) NOT NULL,
  `modulo` varchar(45) NOT NULL,
  `activo` int(1) NOT NULL,
  `orden` INT(4) NOT NULL DEFAULT 0,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idModulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `idRol` varchar(36) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `descripcion` varchar(45) DEFAULT '',
  `activo` int(1) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='ALMACENA ROLES DE LA APP';



DROP TABLE IF EXISTS `transaccionAcciones`;
CREATE TABLE `transaccionAcciones` (
  `idRol` varchar(36) NOT NULL,
  `idTransaccion` varchar(36) NOT NULL,
  `idAccion` varchar(36) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idRol`,`idTransaccion`,`idAccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `transacciones`;
CREATE TABLE `transacciones` (
  `idTransaccion` varchar(36) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `descripcion` varchar(45) DEFAULT '',
  `url` varchar(45) DEFAULT '',
  `codigo` varchar(7) DEFAULT '',
  `activo` int(1) NOT NULL,
  `idModulo` varchar(36) DEFAULT NULL,
  `orden` INT(4) NOT NULL DEFAULT 0,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idTransaccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




DROP TABLE IF EXISTS `usuariosRoles`;
CREATE TABLE `usuariosRoles` (
  `idUsuario` varchar(36) NOT NULL,
  `idRol` varchar(36) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`,`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- ------------------------------------------------------------------------------
-- USUARIOS

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `idUsuario` varchar(36) NOT NULL,
  `nombreUsuario` varchar(10) NOT NULL,
  `claveAcceso` varchar(40) NOT NULL,
  `correoElectronico` varchar(90) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellidoPaterno` varchar(45) DEFAULT '',
  `apellidoMaterno` varchar(45) DEFAULT '',
  `activo` int(11) NOT NULL,
  `usuarioBloqueado` int(11) NOT NULL,
  `fechaRegistro` datetime NOT NULL,
  `fechaVigencia` datetime NOT NULL,
  `ultimoIngreso` datetime NOT NULL,
  `idEstructura` varchar(36) NOT NULL,
  `pathEstructura` VARCHAR(200) DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='ALMACENA USUARIOS DE LA APP';




-- ------------------------------------------------------------------------------
-- PACIENTES

DROP TABLE IF EXISTS `grupoCatalogoGeneral`;
CREATE TABLE `grupoCatalogoGeneral` (
  `idGrupoCatalogoGeneral` int(11) NOT NULL,
  `nombreGrupo` varchar(100) NOT NULL,
  `descripcionGrupo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idGrupoCatalogoGeneral`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ;


DROP TABLE IF EXISTS `catalogoGeneral`;
CREATE TABLE `catalogoGeneral` (
  `idCatalogoGeneral` int(11) NOT NULL,
  `nombreCatalogo` varchar(150) NOT NULL,
  `estatus` int(11) NOT NULL,
  `idGrupo` int(11) NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  `updateFecha` datetime DEFAULT NULL,
  PRIMARY KEY (`idCatalogoGeneral`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ;


DROP TABLE IF EXISTS `sepomex`;
CREATE TABLE `sepomex` (
  `id_cp` int(11) NOT NULL AUTO_INCREMENT,
  `d_codigo` varchar(5) NOT NULL,
  `d_asenta` varchar(120) NOT NULL,
  `d_tipo_asenta` varchar(120) NOT NULL,
  `D_mnpio` varchar(120) NOT NULL,
  `d_estado` varchar(120) NOT NULL,
  `d_ciudad` varchar(120) NOT NULL,
  `d_CP` varchar(5) NOT NULL,
  `c_estado` varchar(2) NOT NULL,
  `c_oficina` varchar(5) NOT NULL,
  `c_CP` varchar(5) NOT NULL,
  `c_tipo_asenta` varchar(2) NOT NULL,
  `c_mnpio` varchar(3) NOT NULL,
  `id_asenta_cpcons` varchar(4) NOT NULL,
  `d_zona` varchar(15) NOT NULL,
  `c_cve_ciudad` varchar(2) NOT NULL,
  PRIMARY KEY (`id_cp`)
) ENGINE=InnoDB AUTO_INCREMENT=105275 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



CREATE VIEW `unidosis`.`viewSepomex` AS SELECT * FROM `comunes`.`sepomexBak`;



DROP TABLE IF EXISTS `paciente`;
CREATE TABLE `paciente` (
  `idPaciente` varchar(36)  NOT NULL,
  `nombreCompleto` varchar(45)  NOT NULL DEFAULT ' ',
  `apellidoPaterno` varchar(45)  NOT NULL DEFAULT ' ',
  `apellidoMaterno` varchar(45)  DEFAULT ' ',
  `sexo` char(1)  NOT NULL DEFAULT '',
  `fechaNacimiento` datetime NOT NULL,
  `rfc` varchar(13)  DEFAULT ' ',
  `curp` varchar(19)  NOT NULL DEFAULT ' ',
  `idTipoPaciente` int(2) NOT NULL DEFAULT '1',
  `idUnidadMedica` int(2) NOT NULL DEFAULT '1',
  `claveDerechohabiencia` varchar(45)  DEFAULT ' ',
  `idEstatusPaciente` int(2) NOT NULL DEFAULT '1',
  `pacienteParticular` char(1)  DEFAULT '',
  `pacienteNumero` varchar(45)  NOT NULL DEFAULT ' ',
  `idEstadoCivil` int(2) NOT NULL DEFAULT '1',
  `idEscolaridad` int(2) NOT NULL DEFAULT '1',
  `idGrupoEtnico` int(2) NOT NULL DEFAULT '1',
  `idGrupoSanguineo` int(2) NOT NULL DEFAULT '1',
  `idReligion` int(2) NOT NULL DEFAULT '1',
  `idNivelSocioEconomico` int(2) NOT NULL DEFAULT '1',
  `idTipoVivienda` int(2) NOT NULL DEFAULT '1',
  `idOcupacion` int(2) NOT NULL DEFAULT '1',
  `idEstructura` varchar(36)  DEFAULT NULL,
  `idEstructuraPeriferico` varchar(36)  DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(45)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idPaciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `pacienteResponsable`;
CREATE TABLE `pacienteResponsable` (
  `idPacienteResponsable` varchar(36)  NOT NULL,
  `idPaciente` varchar(36)  NOT NULL,
  `nombreCompleto` varchar(45)  NOT NULL,
  `apellidoPaterno` varchar(45)  NOT NULL,
  `apellidoMaterno` varchar(45)  DEFAULT NULL,
  `telefonoCasa` varchar(45)  NOT NULL,
  `telefonoCelular` varchar(45)  DEFAULT NULL,
  `correoElectronico` varchar(45)  DEFAULT NULL,
  `idParentesco` int(2) NOT NULL,
  `responsableLegal` varchar(2)  NOT NULL,
  `rfc` varchar(45)  DEFAULT NULL,
  `curp` varchar(45)  NOT NULL,
  `domicilio` varchar(45)  DEFAULT NULL,
  `comentarios` varchar(45)  DEFAULT NULL,
  `insertFecha` datetime NOT NULL ,
  `insertIdUsuario` varchar(36)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idPacienteResponsable`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



CREATE TABLE `pacienteDomicilio` (
  `idPacienteDomicilio` varchar(36)  NOT NULL,
  `idPaciente` varchar(36)  NOT NULL,
  `idPais` int(3) NOT NULL,
  `idEstado` int(11) DEFAULT NULL,
  `idMunicipio` int(11) DEFAULT NULL,
  `idColonia` int(11) DEFAULT NULL,
  `calle` varchar(36)  NOT NULL DEFAULT ' ',
  `codigoPostal` varchar(5)  DEFAULT ' ',
  `numeroExterior` varchar(45)  NOT NULL DEFAULT ' ',
  `numeroInterior` varchar(45)  DEFAULT ' ',
  `telefonoCasa` varchar(45)  NOT NULL DEFAULT ' ',
  `telefonoOficina` varchar(45)  DEFAULT ' ',
  `extension` varchar(45)  DEFAULT ' ',
  `telefonoCelular` varchar(45)  DEFAULT ' ',
  `correoElectronico` varchar(90)  DEFAULT ' ',
  `cuentaFacebook` varchar(45)  DEFAULT ' ',
  `domicilioActual` int(1) NOT NULL DEFAULT '1',
  `insertFecha` datetime NOT NULL ,
  `insertIdUsuario` varchar(36)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idPacienteDomicilio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais` (
  `idPais` int(11) NOT NULL,
  `nombrePais` varchar(100) NOT NULL,
  `A2` varchar(2) NOT NULL,
  `A3` varchar(3) NOT NULL,
  `N3` int(11) NOT NULL,
  `estatus` int(2) NOT NULL,
  PRIMARY KEY (`idPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




-- = = = = = = = = Modulo MEDICAMENTOS = = = = = = = =

DROP TABLE IF EXISTS `medicamento`;
CREATE TABLE `medicamento` (
  `idMedicamento` varchar(45)  NOT NULL,
  `claveInstitucional` varchar(45)  DEFAULT NULL,
  `sustanciaActiva` int(11) DEFAULT NULL,
  `idViaAdministracion` int(11) DEFAULT NULL,
  `nombreCorto` varchar(150)  DEFAULT NULL,
  `nombreLargo` varchar(250)  DEFAULT NULL,
  `concentracion` varchar(50)  DEFAULT NULL,
  `idUnidadConcentracion` int(11) DEFAULT NULL,
  `presentacionLaboratorio` varchar(100)  DEFAULT NULL,
  `laboratorio` varchar(50)  DEFAULT NULL,
  `idPresentacionEntrada` int(11) DEFAULT NULL,
  `factorTransformacion` int(11) DEFAULT NULL,
  `idPresentacionSalida` int(11) DEFAULT NULL,
  `idCategoria` int(11) DEFAULT NULL,
  `idSubcategoria` int(11) DEFAULT NULL,
  `grupo` varchar(50)  DEFAULT NULL,
  `indivisible` int(11) DEFAULT NULL,
  `imagenPresentacion` blob,
  `nameImage` varchar(45)  DEFAULT NULL,
  `cuadroBasico` int(1) DEFAULT NULL,
  `activo` int(1) DEFAULT NULL,
  `tipo` int(2) DEFAULT NULL,
  `insertFecha` date DEFAULT NULL,
  `insertIdUsuario` varchar(36)  DEFAULT NULL,
  `updateFecha` date DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idMedicamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Almacena medicamentos';




DROP TABLE IF EXISTS `categoriaMedicamento`;
CREATE TABLE `categoriaMedicamento` (
  `idCategoriaMedicamento` int(11) NOT NULL,
  `nombreCategoriaMedicamento` varchar(50) DEFAULT NULL,
  `activa` int(11) DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idCategoriaMedicamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Almacena la categoria de los medicamentos';


DROP TABLE IF EXISTS `presentacionMedicamento`;
CREATE TABLE `presentacionMedicamento` (
  `idPresentacion` int(11) NOT NULL,
  `nombrePresentacion` varchar(50) DEFAULT NULL,
  `activa` int(11) DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idPresentacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Almacena la presentacion del medicamento';


DROP TABLE IF EXISTS `subcategoriaMedicamento`;
CREATE TABLE `subcategoriaMedicamento` (
  `idSubcategoriaMedicamento` int(11) NOT NULL,
  `idCategoriaMedicamento` int(11) DEFAULT NULL,
  `nombreSubcategoriaMedicamento` varchar(50) DEFAULT NULL,
  `activa` int(11) DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idSubcategoriaMedicamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Almacena la subcategoria del medicamento';


DROP TABLE IF EXISTS `unidadConcentracion`;
CREATE TABLE `unidadConcentracion` (
  `idUnidadConcentracion` int(11) NOT NULL,
  `nombreUnidadConcentracion` varchar(50) DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idUnidadConcentracion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Almacena la unidad de concentracion';


DROP TABLE IF EXISTS `viaAdministracion`;
CREATE TABLE `viaAdministracion` (
  `idViaAdministracion` int(11) NOT NULL,
  `nombreViaAdministracion` varchar(50) DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idViaAdministracion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Almacena la via de administracion';


DROP TABLE IF EXISTS `sustanciaActiva`;
CREATE TABLE `sustanciaActiva` (
  `idSustanciaActiva` int(11) NOT NULL,
  `nombreSustanciaActiva` varchar(300) DEFAULT NULL,
  `activa` int(11) DEFAULT NULL,
  `insertFecha` datetime DEFAULT NULL,
  `insertIdUsuario` varchar(36) DEFAULT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idSustanciaActiva`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Almacena el nombre de sustancias activas';



-- = = = = = = = = Vista Medicamentos = = = = = = = =

CREATE VIEW `viewMedicamentos` AS
    SELECT 
        `m`.`idMedicamento` AS `idMedicamento`,
        `m`.`claveInstitucional` AS `claveInstitucional`,
        `s`.`nombreSustanciaActiva` AS `sustanciaActiva`,
        `v`.`nombreViaAdministracion` AS `viaAdministracion`,
        `m`.`nombreCorto` AS `nombreCorto`,
        `m`.`nombreLargo` AS `nombreLargo`,
        `m`.`concentracion` AS `concentracion`,
        `u`.`nombreUnidadConcentracion` AS `unidadConcentracion`,
        `m`.`presentacionLaboratorio` AS `presentacionLaboratorio`,
        `m`.`laboratorio` AS `laboratorio`,
        `c`.`nombreCategoriaMedicamento` AS `categoria`,
        `sc`.`nombreSubcategoriaMedicamento` AS `subcategoria`,
        `pe`.`nombrePresentacion` AS `presentacionEntrada`,
        `ps`.`nombrePresentacion` AS `presentacionSalida`,
        `m`.`factorTransformacion` AS `factorTransformacion`,
        `m`.`grupo` AS `grupo`,
        `m`.`cuadroBasico` AS `cuadroBasico`,
        `m`.`activo` AS `activo`,
        `m`.`tipo` AS `tipo`
    FROM
        (((((((`medicamento` `m`
        LEFT JOIN `sustanciaActiva` `s` ON ((`m`.`sustanciaActiva` = `s`.`idSustanciaActiva`)))
        LEFT JOIN `viaAdministracion` `v` ON ((`m`.`idViaAdministracion` = `v`.`idViaAdministracion`)))
        LEFT JOIN `unidadConcentracion` `u` ON ((`m`.`idUnidadConcentracion` = `u`.`idUnidadConcentracion`)))
        LEFT JOIN `categoriaMedicamento` `c` ON ((`m`.`idCategoria` = `c`.`idCategoriaMedicamento`)))
        LEFT JOIN `subcategoriaMedicamento` `sc` ON ((`m`.`idSubcategoria` = `sc`.`idSubcategoriaMedicamento`)))
        LEFT JOIN `presentacionMedicamento` `pe` ON ((`m`.`idPresentacionEntrada` = `pe`.`idPresentacion`)))
        LEFT JOIN `presentacionMedicamento` `ps` ON ((`m`.`idPresentacionSalida` = `ps`.`idPresentacion`)));

-- = = = = = = = = End MEDICAMENTO = = = = = = = =


-- = = = = = = = = Modulo Usuarios = = = = = = = = 
CREATE VIEW `viewUsuarios` AS
   SELECT 
        `u`.`idUsuario` AS `idUsuario`,
        `u`.`nombre` AS `nombre`,
        `u`.`apellidoPaterno` AS `apellidoPaterno`,
        `u`.`apellidoMaterno` AS `apellidoMaterno`,
        `u`.`nombreUsuario` AS `nombreUsuario`,
        `u`.`activo` AS `activo`,
        `e`.`nombre` AS `estructura`,
        `u`.`pathEstructura` AS `pathEstructura`,
        `r`.`nombre` AS `nombreRol`
    FROM
        `usuarios` `u`
        LEFT JOIN `usuariosRoles` `ur` ON `u`.`idUsuario` = `ur`.`idUsuario`
        LEFT JOIN `roles` `r` ON `ur`.`idRol` = `r`.`idRol`
        LEFT JOIN `estructura` `e` ON `u`.`idEstructura` = `e`.`idEstructura`;

    

-- = = = = = = = = END USUARIOS = = = = = = = =



-- ------------------------------------------------------------------------------
-- PRESCRIPCIONES

DROP TABLE IF EXISTS `estatusPaciente`;
CREATE TABLE `estatusPaciente` (
  `idestatusPaciente` int(4) NOT NULL,
  `estatusPaciente` varchar(50)  NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idestatusPaciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Posibles Estatus de un paciente';


DROP TABLE IF EXISTS `estatusPrescripcion`;
CREATE TABLE `estatusPrescripcion` (
  `idEstatusPrescripcion` int(4) NOT NULL,
  `estatus` varchar(50)  NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idEstatusPrescripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Posibles Estatus de un Prescripción';


DROP TABLE IF EXISTS `diagnostico`;
CREATE TABLE `diagnostico` (
  `idDiagnostico` varchar(36)  NOT NULL,
  `nombre` varchar(45)  NOT NULL,
  `descripcion` varchar(36)  NOT NULL,
  `activo` int(1) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idDiagnostico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Catálogo de Diagnosticos';


DROP TABLE IF EXISTS `prescripcion`;
CREATE TABLE `prescripcion` (
  `idPrescripcion` varchar(36) NOT NULL,
  `idPacienteServicio` varchar(36)  NOT NULL,
  `idPaciente` varchar(36)  NOT NULL,
  `folio` varchar(36)  NOT NULL,
  `fechaPrescripcion` datetime NOT NULL,
  `fechaFirma` datetime NOT NULL,
  `tipoPrescripcion` varchar(1)  NOT NULL,
  `tipoConsulta` varchar(1)  NOT NULL,
  `idMedico` varchar(36)  NOT NULL,
  `idUsuarioCancela` VARCHAR(36) NULL,
  `recurrente` int(1) NOT NULL,
  `comentarios` text ,
  `idEstatusPrescripcion` int(2) NOT NULL,
  `idEstructura` VARCHAR(36) DEFAULT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idPrescripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='ALMACENA CABECERA DE PRESCRIPCIONES INTERNAS Y EXTERNAS';



DROP TABLE IF EXISTS `prescripcionInsumo` (
CREATE TABLE `prescripcionInsumo` (
  `idPrescripcionInsumo` varchar(36)  NOT NULL,
  `idPrescripcion` varchar(36)  NOT NULL,
  `idInsumo` varchar(36)  NOT NULL,
  `fechaInicio` datetime NOT NULL,
  `dosis` int(11) NOT NULL,
  `frecuencia` int(11) NOT NULL,
  `duracion` int(11) NOT NULL,
  `comentarios` varchar(199)  DEFAULT NULL,
  `idEstatusPrescripcion` int(2) NOT NULL,
  `idMedico` VARCHAR(36) NOT NULL,
  `fechaCancela` DATETIME NULL,
  `idUsuarioCancela` VARCHAR(36) NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36)  NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36)  DEFAULT NULL,
  PRIMARY KEY (`idPrescripcionInsumo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='ALMACENA DETALLE DE INSUMOS DE PRESCRIPCIONES INTERNAS Y EXTERNAS';



DROP TABLE IF EXISTS `diagnosticoPaciente`;
CREATE TABLE IF NOT EXISTS `diagnosticoPaciente` (
  `idDiagnosticoPaciente` VARCHAR(36) NOT NULL,
  `idPrescripcion` VARCHAR(36) NULL,
  `fechaDiagnostico` DATETIME NULL,
  `idUsuarioDiagnostico` VARCHAR(36) NULL,
  `idDiagnostico` VARCHAR(36) NOT NULL,
  `fechaFinDiagnostico` DATETIME NULL,
  `idUsuarioDiagnosticoTratado` VARCHAR(36) NULL,
  `idEstatusDiagnostico` INT(2) NULL,
  `insertFecha` DATETIME NULL,
  `insertIdUsuario` VARCHAR(36) NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idDiagnosticoPaciente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra Los diagnósticos por Paciente por cada prescripción';



-- ------------------------------------------------------------------------------
-- CADENA DE SUMINISTRO

DROP TABLE IF EXISTS tipoBloqueo ;
CREATE TABLE IF NOT EXISTS `tipoBloqueo` (
  `idTipoBloqueo` int(2) COLLATE utf8_unicode_ci NOT NULL auto_increment,
 -- `tipoBloqueo` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `claveInstitucional` varchar(45)  DEFAULT NULL,
  `lote` varchar(20) NOT NULL,  
  `comentarios` varchar(199) DEFAULT NULL,
  `activo` int(1) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idTipoBloqueo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Define los motivos específicos de bloqueo de medicamentos y o lotes';

DROP TABLE IF EXISTS tipoBloqueoMotivos ;
CREATE TABLE IF NOT EXISTS`tipoBloqueoMotivos` (
  `idTipoBloqueoMotivos` int(2) COLLATE utf8_unicode_ci NOT NULL auto_increment, 
  `tipoBloqueoMotivos` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `activo` int(1) NOT NULL,
   PRIMARY KEY (`idTipoBloqueoMotivos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `tipoAlmacen` ;
CREATE TABLE IF NOT EXISTS `tipoAlmacen` (
  `idTipoAlmacen` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idTipoAlmacen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `almacen` ;
-- CREATE TABLE IF NOT EXISTS `almacen` (
--   `idAlmacen` VARCHAR(36) NOT NULL,
--   `nombreAlmacen` VARCHAR(45) NOT NULL,
--   `descripcion` VARCHAR(100) NULL,
--   `idAlmacenPadre` INT NOT NULL,
--   `idTipoAlmacen` INT NOT NULL,
--   `insertFecha` DATETIME NOT NULL,
--   `insertIdUsuario` VARCHAR(36) NOT NULL,
--   `updateFecha` DATETIME NULL,
--   `updateIdUsuario` VARCHAR(36) NULL,
--   PRIMARY KEY (`idAlmacen`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `tipoOrden` ;
CREATE TABLE IF NOT EXISTS `tipoOrden` (
  `idTipoOrden` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idTipoOrden`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `proveedor` ;
CREATE TABLE IF NOT EXISTS `proveedor` (
  `idProveedor` INT NOT NULL,
  `nombreProveedor` VARCHAR(100) NULL,
  `telefono` VARCHAR(12) NULL,
  `empresa` VARCHAR(100) NULL,
  `domicilio` VARCHAR(100) NULL,
  `correo` VARCHAR(50) NULL,
  `rfc` VARCHAR(13) NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idProveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';



DROP TABLE IF EXISTS `almacenPuntosControl` ;
CREATE TABLE IF NOT EXISTS `almacenPuntosControl` (
  `idAlmacenPuntosControl` VARCHAR(36) NOT NULL,
  `idAlmacen` VARCHAR(36) NOT NULL,
  `idMedicamento` VARCHAR(36) NOT NULL,
  `minimo` INT NOT NULL,
  `reorden` INT NOT NULL,
  `maximo` INT NOT NULL,
  `activo` INT NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idAlmacenPuntosControl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `estatusOrdenCompra` ;
CREATE TABLE IF NOT EXISTS `estatusOrdenCompra` (
  `idEstatusOrdenCompra` INT NOT NULL,
  `nombreEstatus` VARCHAR(45) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idEstatusOrdenCompra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `ordenCompra` ;
CREATE TABLE IF NOT EXISTS `ordenesCompra` (
  `idOrdenCompra` VARCHAR(36) NOT NULL,
  `idReabasto` VARCHAR(36) NOT NULL,
  `folioOrdenCompra` VARCHAR(36) NOT NULL,
  `idProveedor` VARCHAR(36) NOT NULL,
  `idEstatusOrdenCompra` INT NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idOrdenCompra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';




DROP TABLE IF EXISTS `estatusReabasto`;
CREATE TABLE IF NOT EXISTS `estatusReabasto` (
  `idEstatusReabasto` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idEstatusReabasto`))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `reabasto`;
CREATE TABLE IF NOT EXISTS `reabasto` (
  `idReabasto` VARCHAR(36) NOT NULL,
  `idEstructura` VARCHAR(36) NOT NULL,
  `idTipoOrden` INT(2) NOT NULL,
  `folio` VARCHAR(45) NOT NULL,
  `fechaSolicitud` DATETIME NOT NULL,
  `idUsuarioSolicitud` VARCHAR(36) NOT NULL,
  `fechaSurtida` DATETIME NULL,
  `idUsuarioSurtida` VARCHAR(36) NULL,
  `documentoSurtida` VARCHAR(45) NULL,
  `fechaRecepcion` DATETIME NULL,
  `idUsuarioRecepcion` VARCHAR(36) NULL,
  `fechaIngresoInventario` DATETIME NULL,
  `idUsuarioIngresoInventario` VARCHAR(36) NULL,
  `idEstatusReabasto` INT(2) NOT NULL,
  `idProveedor` VARCHAR(36) NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idReabasto`) )
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `reabastoInsumo`;
CREATE TABLE IF NOT EXISTS `reabastoInsumo` (
  `idReabastoInsumo` VARCHAR(36) NOT NULL,
  `idReabasto` VARCHAR(36) NOT NULL,
  `idInsumo` VARCHAR(36) NOT NULL,
  `idPaisOrigen` INT NOT NULL,
  `laboratorio` VARCHAR(100) NULL,
  `caducidad` DATETIME NOT NULL,
  `lote` VARCHAR(50) NOT NULL,
  `costo` DOUBLE NULL,
  `nombreComercial` VARCHAR(100) NOT NULL,
  `observaciones` VARCHAR(100) NULL,
  `cantidadSolicitada` INT(11) NOT NULL,
  `cantidadComprometida` INT(11) NOT NULL,
  `cantidadSurtida` INT(11) NOT NULL,
  `cantidadRecibida` INT(11) NOT NULL,
  `cantidadIngresada` INT(11) NULL,
  `idEstatusReabasto` INT(2) NOT NULL,
  `insertFecha` DATETIME NULL,
  `insertIdUsuario` VARCHAR(36) NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idReabastoInsumo`))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


DROP TABLE IF EXISTS `tipoAlmacen`;
CREATE TABLE IF NOT EXISTS `tipoAlmacen` (
  `idTipoAlmacen` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idTipoAlmacen`))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='';


-- DROP TABLE IF EXISTS `almacenInsumoExistencia`;
-- CREATE TABLE IF NOT EXISTS `almacenInsumoExistencia` (
--   `idAlmacenInsumoExistencia` VARCHAR(36) NOT NULL,
--   `idEstructura` VARCHAR(36) NOT NULL,
--   `idInsumo` VARCHAR(36) NOT NULL,
--   `cantidadActual` INT(11) NULL,
--   `cantidadComprometida` INT(11) NULL,
--   `insertFecha` DATETIME NULL,
--   `insertIdUsuario` VARCHAR(36) NULL,
--   `updateFecha` DATETIME NULL,
--   `updateIdUsuario` VARCHAR(36) NULL,
--   PRIMARY KEY (`idAlmacenInsumoExistencia`)
--   )
-- ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Mantiene actualizadas las existencias fisicas y apartados';


DROP TABLE IF EXISTS `surtimiento`;
CREATE TABLE `surtimiento` (
  `idSurtimiento` varchar(36) NOT NULL,
  `idPrescripcion` varchar(36) NOT NULL,
  `folio` varchar(20) NOT NULL,
  `folioPadre` varchar(20) NOT NULL,
  `fechaProgramada` datetime NOT NULL,
  `fechaSurtimiento` datetime DEFAULT NULL,
  `idUsuarioSurtimiento` varchar(36) DEFAULT NULL,
  `fechaCancela` datetime DEFAULT NULL,
  `idUsuarioCancela` varchar(36) DEFAULT NULL,
  `idEstatusSurtimiento` int(2) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idSurtimiento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra los sutimientos que debe enviar un Almacén/SubAlmacén por cada Prescripción';


DROP TABLE IF EXISTS `surtimientoInsumo`;
CREATE TABLE `surtimientoInsumo` (
  `idSurtimientoInsumo` varchar(36) NOT NULL,
  `idSurtimiento` varchar(36) NOT NULL,
  `idPrescripcionInsumo` varchar(36) NOT NULL,
  `fechaProgramada` datetime NOT NULL,
  `cantidadSolicitada` int(11) NOT NULL,
  `fechaEnviada` datetime DEFAULT NULL,
  `idUsuarioEnviada` varchar(36) DEFAULT NULL,
  `cantidadEnviada` int(11) DEFAULT NULL,
  `fechaRecepcion` datetime DEFAULT NULL,
  `idUsuarioRecepcion` varchar(36) DEFAULT NULL,
  `cantidadRecepcion` int(11) DEFAULT NULL,
  `fechaMinistracion` datetime DEFAULT NULL,
  `idUsuarioMinistracion` varchar(36) DEFAULT NULL,
  `cantidadMinistracion` int(11) DEFAULT NULL,
  `fechaCancela` datetime DEFAULT NULL,
  `idUsuarioCancela` varchar(36) DEFAULT NULL,
  `idEstatusPrescripcion` int(2) NOT NULL,
  `insertFecha` datetime NOT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idSurtimientoInsumo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra el detalle de Insumos de los sutimientos que debe enviar un Almacén/SubAlmacén por cada Prescripción';




DROP TABLE IF EXISTS `tipoMovimiento`;
CREATE TABLE `tipoMovimiento` (
  `idTipoMovimiento` INT(2) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `tipoMovimiento` VARCHAR(1) NOT NULL,
  `activo` INT(1) NOT NULL,
  PRIMARY KEY (`idTipoMovimiento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Define los tipos de movimientos de inventarios';


DROP TABLE IF EXISTS `tipoMotivo`;
CREATE TABLE `tipoMotivo` (
  `idTipoMotivo` INT(2) NOT NULL,
  `idTipoMovimiento` INT(2) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `activo` INT(1) NOT NULL,
  PRIMARY KEY (`idTipoMotivo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Define los motivos específicos de los tipos de movimiento';


DROP TABLE IF EXISTS `movimientoInventario`;
CREATE TABLE `movimientoInventario` (
  `idMovimientoInventario` varchar(36) NOT NULL,
  `idTipoMotivo` INT(2) NOT NULL,
  `fecha` datetime NOT NULL,
  `idUsuarioMovimiento` varchar(36) NOT NULL,
  `idEstrutcuraOrigen` varchar(36) NOT NULL,
  `idEstrutcuraDestino` varchar(36) DEFAULT NULL,
  `idInventario` varchar(36) NOT NULL,  
  `cantidad` int(11) NOT NULL,
  `folioDocumento` varchar(20) NOT NULL,
  PRIMARY KEY (`idMovimientoInventario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra la bitácora de detalle de movimiento de inventario';


DROP TABLE IF EXISTS `tipoDocumento`;
CREATE TABLE `tipoDocumento` (
  `idTipoDocumento` INT(2) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `activo` INT(1) NOT NULL,  
  PRIMARY KEY (`idTipoDocumento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Define los tipos de documentos utilizados en el sistema';


DROP TABLE IF EXISTS `documentoPadre`;
CREATE TABLE `documentoPadre` (
  `folioDocumento` VARCHAR(20) NOT NULL,
  `folioPadre` VARCHAR(20) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`folioDocumento`, `folioPadre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra la relación de folioDocumento con folioDocumentoPadre';




DROP TABLE IF EXISTS `inventario`;
CREATE TABLE `inventario` (
  `idInventario` varchar(36) NOT NULL,
  `idEstructura` varchar(36) NOT NULL,
  `idInsumo` varchar(36) NOT NULL,
  `idPresentacion` int(11) NOT NULL,
  `lote` varchar(20) NOT NULL,
  `fechaCaducidad` datetime NOT NULL,
  `costo` decimal(18,2) NOT NULL,
  `costoUnidosis` decimal(18,2) NOT NULL,
  `registroSsa` varchar(45) DEFAULT NULL,
  `dictamenMedico` varchar(45) DEFAULT NULL,
  `idProveedor` int(11) DEFAULT NULL,
  `idFabricante` varchar(36) DEFAULT NULL,
  `idMarca` int(11) DEFAULT NULL,
  `accesorios` text,
  `activo` int(2) NOT NULL,
  `cantidadActual` int(11) DEFAULT NULL,
  `existenciaInicial` bigint(20) NOT NULL,
  `fechaIngreso` datetime NOT NULL,
  `insertFecha` datetime DEFAULT NULL,
  `insertIdUsuario` varchar(36) NOT NULL,
  `updateFecha` datetime DEFAULT NULL,
  `updateIdUsuario` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`idInventario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra Existencias por Almacen Insumo y Lote registrando datos de ingreso';



DROP TABLE IF EXISTS `almacenInsumoComprometido`;
CREATE TABLE `almacenInsumoComprometido` (
  `idEstructura` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `idInsumo` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `cantidadComprometida` int(11) DEFAULT NULL,  
  PRIMARY KEY (`idEstructura` , `idInsumo` , `cantidadComprometida`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra las cantidades comprometidas por Almacen e Insumo';




-- ----------------------------------------------------------------------------------------
--  VISITA HOSPITALARIA , ASIGNACIÓN A SERVICIO, ASIGNACIÓN DE UBICACION (CAMA, SILLA, ETC)

DROP TABLE IF EXISTS `visita`;
CREATE TABLE IF NOT EXISTS `visita` (
  `idVisita` VARCHAR(36) NOT NULL,
  `idPaciente` VARCHAR(36) NOT NULL,
  `fechaIngreso` DATETIME NOT NULL,
  `idUsuarioIngresa` VARCHAR(36) NOT NULL,
  `fechaEgreso` DATETIME NULL,
  `idUsuarioCierra` VARCHAR(36) NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idVisita`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Registra cada Visita del paciente al hospital y concluye en los egresos';


DROP TABLE IF EXISTS `pacienteServicio`
CREATE TABLE IF NOT EXISTS `pacienteServicio` (
  `idPacienteServicio` VARCHAR(36) NOT NULL,
  `idVisita` VARCHAR(36) NOT NULL,
  `idEstructura` VARCHAR(36) NOT NULL,
  `fechaAsignacionInicio` DATETIME NOT NULL,
  `idUsuarioAsignacionInicio` VARCHAR(36) NULL,
  `fechaAsignacionFin` DATETIME NULL,
  `idUsuarioAsignacionFin` VARCHAR(36) NULL,
  `idEstatus` INT(2) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idPacienteServicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Asigna un paciente con vivita abierta a un servicio de Consulta Interna o externa';



CREATE TABLE IF NOT EXISTS `pacienteUbicacion` (
  `idPacienteUbicacion` VARCHAR(36) NOT NULL,
  `idPacienteServicio` VARCHAR(36) NOT NULL,
  `fechaUbicaInicio` DATETIME NOT NULL,
  `idUsuarioUbicaInicio` VARCHAR(36) NULL,
  `fechaUbicaFin` DATETIME NULL,
  `idUsuarioUbicaFin` VARCHAR(36) NULL,
  `idEstatusUbicacion` INT(2) NOT NULL,
  `insertFecha` DATETIME NOT NULL,
  `insertIdUsuario` VARCHAR(36) NOT NULL,
  `updateFecha` DATETIME NULL,
  `updateIdUsuario` VARCHAR(36) NULL,
  PRIMARY KEY (`idPacienteUbicacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Asigna una entidad de ubicación a u paciente con asignación de servicio abierta puede ser cama, silla, pasillo, camilla, cuna';



