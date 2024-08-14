package mx.mc.ws.suministro.model;

import java.io.Serializable;

/**
 *
 * @author hramirez
 */
public class OrdenReabastoMedicamento implements Serializable {
    private String IMCEUCONDI_GPO_ART;
    private String IMCEUCONDI_GEN_ART;
    private String IMCEUCONDI_CD_ESP;
    private String IMCEUCONDI_CD_DIF;
    private String IMCEUCONDI_CD_VAR;
    private String DESCR120;
    private int IMCEUCONDI_UNISOL;
    private int IMCEUCONDI_UNISUR;
    private String IMCEUCONDI_UM_UNIT;

    public OrdenReabastoMedicamento() {
        //No code needed in constructor
    }

    public String getIMCEUCONDI_GPO_ART() {
        return IMCEUCONDI_GPO_ART;
    }

    public void setIMCEUCONDI_GPO_ART(String IMCEUCONDI_GPO_ART) {
        this.IMCEUCONDI_GPO_ART = IMCEUCONDI_GPO_ART;
    }

    public String getIMCEUCONDI_GEN_ART() {
        return IMCEUCONDI_GEN_ART;
    }

    public void setIMCEUCONDI_GEN_ART(String IMCEUCONDI_GEN_ART) {
        this.IMCEUCONDI_GEN_ART = IMCEUCONDI_GEN_ART;
    }

    public String getIMCEUCONDI_CD_ESP() {
        return IMCEUCONDI_CD_ESP;
    }

    public void setIMCEUCONDI_CD_ESP(String IMCEUCONDI_CD_ESP) {
        this.IMCEUCONDI_CD_ESP = IMCEUCONDI_CD_ESP;
    }

    public String getIMCEUCONDI_CD_DIF() {
        return IMCEUCONDI_CD_DIF;
    }

    public void setIMCEUCONDI_CD_DIF(String IMCEUCONDI_CD_DIF) {
        this.IMCEUCONDI_CD_DIF = IMCEUCONDI_CD_DIF;
    }

    public String getIMCEUCONDI_CD_VAR() {
        return IMCEUCONDI_CD_VAR;
    }

    public void setIMCEUCONDI_CD_VAR(String IMCEUCONDI_CD_VAR) {
        this.IMCEUCONDI_CD_VAR = IMCEUCONDI_CD_VAR;
    }

    public String getDESCR120() {
        return DESCR120;
    }

    public void setDESCR120(String DESCR120) {
        this.DESCR120 = DESCR120;
    }

    public int getIMCEUCONDI_UNISOL() {
        return IMCEUCONDI_UNISOL;
    }

    public void setIMCEUCONDI_UNISOL(int IMCEUCONDI_UNISOL) {
        this.IMCEUCONDI_UNISOL = IMCEUCONDI_UNISOL;
    }

    public int getIMCEUCONDI_UNISUR() {
        return IMCEUCONDI_UNISUR;
    }

    public void setIMCEUCONDI_UNISUR(int IMCEUCONDI_UNISUR) {
        this.IMCEUCONDI_UNISUR = IMCEUCONDI_UNISUR;
    }

    public String getIMCEUCONDI_UM_UNIT() {
        return IMCEUCONDI_UM_UNIT;
    }

    public void setIMCEUCONDI_UM_UNIT(String IMCEUCONDI_UM_UNIT) {
        this.IMCEUCONDI_UM_UNIT = IMCEUCONDI_UM_UNIT;
    }      

    @Override
    public String toString() {
        return "OrdenReabastoMedicamento{" + "IMCEUCONDI_GPO_ART=" + IMCEUCONDI_GPO_ART + ", IMCEUCONDI_GEN_ART=" + IMCEUCONDI_GEN_ART + ", IMCEUCONDI_CD_ESP=" + IMCEUCONDI_CD_ESP + ", IMCEUCONDI_CD_DIF=" + IMCEUCONDI_CD_DIF + ", IMCEUCONDI_CD_VAR=" + IMCEUCONDI_CD_VAR + ", DESCR120=" + DESCR120 + ", IMCEUCONDI_UNISOL=" + IMCEUCONDI_UNISOL + ", IMCEUCONDI_UNISUR=" + IMCEUCONDI_UNISUR + ", IMCEUCONDI_UM_UNIT=" + IMCEUCONDI_UM_UNIT + '}';
    }

}
