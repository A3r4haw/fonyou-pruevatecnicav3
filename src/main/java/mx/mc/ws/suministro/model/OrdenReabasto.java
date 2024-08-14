package mx.mc.ws.suministro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hramirez
 */
public class OrdenReabasto implements Serializable {

    private String SETID;
    private String IMNF_UMF;
    private String IMCEUCONDI_NFOLIO;
    private String IMCADIT_SAIFOLIO;
    private Date CREATE_DATE;
    private Date LAST_DTTM_UPDATE;
    private String IMCADIT_PROCESSFLG;
    private String IM_CLASS_PTAL;
    private String DEPTID;
    private String DESCR;
    private String IM_ESPECIALIDAD;
    private String DESCR50;
    private String AREA_CODE;
    private String AREA_DESCR;
    private String IMCEUCONDI_TIPSOL;
    private String IMCEUCONDI_ESTSOL;
    private String DESCR1;
    
    private List<OrdenReabastoMedicamento> ordenReabastoMedicamentoList;

    public OrdenReabasto() {
        //No code needed in constructor
    }

    public String getSETID() {
        return SETID;
    }

    public void setSETID(String SETID) {
        this.SETID = SETID;
    }

    public String getIMNF_UMF() {
        return IMNF_UMF;
    }

    public void setIMNF_UMF(String IMNF_UMF) {
        this.IMNF_UMF = IMNF_UMF;
    }

    public String getIMCEUCONDI_NFOLIO() {
        return IMCEUCONDI_NFOLIO;
    }

    public void setIMCEUCONDI_NFOLIO(String IMCEUCONDI_NFOLIO) {
        this.IMCEUCONDI_NFOLIO = IMCEUCONDI_NFOLIO;
    }

    public String getIMCADIT_SAIFOLIO() {
        return IMCADIT_SAIFOLIO;
    }

    public void setIMCADIT_SAIFOLIO(String IMCADIT_SAIFOLIO) {
        this.IMCADIT_SAIFOLIO = IMCADIT_SAIFOLIO;
    }

    public Date getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(Date CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public Date getLAST_DTTM_UPDATE() {
        return LAST_DTTM_UPDATE;
    }

    public void setLAST_DTTM_UPDATE(Date LAST_DTTM_UPDATE) {
        this.LAST_DTTM_UPDATE = LAST_DTTM_UPDATE;
    }

    public String getIMCADIT_PROCESSFLG() {
        return IMCADIT_PROCESSFLG;
    }

    public void setIMCADIT_PROCESSFLG(String IMCADIT_PROCESSFLG) {
        this.IMCADIT_PROCESSFLG = IMCADIT_PROCESSFLG;
    }

    public String getIM_CLASS_PTAL() {
        return IM_CLASS_PTAL;
    }

    public void setIM_CLASS_PTAL(String IM_CLASS_PTAL) {
        this.IM_CLASS_PTAL = IM_CLASS_PTAL;
    }

    public String getDEPTID() {
        return DEPTID;
    }

    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID;
    }

    public String getDESCR() {
        return DESCR;
    }

    public void setDESCR(String DESCR) {
        this.DESCR = DESCR;
    }

    public String getIM_ESPECIALIDAD() {
        return IM_ESPECIALIDAD;
    }

    public void setIM_ESPECIALIDAD(String IM_ESPECIALIDAD) {
        this.IM_ESPECIALIDAD = IM_ESPECIALIDAD;
    }

    public String getDESCR50() {
        return DESCR50;
    }

    public void setDESCR50(String DESCR50) {
        this.DESCR50 = DESCR50;
    }

    public String getAREA_CODE() {
        return AREA_CODE;
    }

    public void setAREA_CODE(String AREA_CODE) {
        this.AREA_CODE = AREA_CODE;
    }

    public String getAREA_DESCR() {
        return AREA_DESCR;
    }

    public void setAREA_DESCR(String AREA_DESCR) {
        this.AREA_DESCR = AREA_DESCR;
    }
    
     public String getIMCEUCONDI_TIPSOL() {
        return IMCEUCONDI_TIPSOL;
    }

    public void setIMCEUCONDI_TIPSOL(String IMCEUCONDI_TIPSOL) {
        this.IMCEUCONDI_TIPSOL = IMCEUCONDI_TIPSOL;
    }

    public String getIMCEUCONDI_ESTSOL() {
        return IMCEUCONDI_ESTSOL;
    }

    public void setIMCEUCONDI_ESTSOL(String IMCEUCONDI_ESTSOL) {
        this.IMCEUCONDI_ESTSOL = IMCEUCONDI_ESTSOL;
    }

    public List<OrdenReabastoMedicamento> getOrdenReabastoMedicamentoList() {
        return ordenReabastoMedicamentoList;
    }

    public void setOrdenReabastoMedicamentoList(List<OrdenReabastoMedicamento> ordenReabastoMedicamentoList) {
        this.ordenReabastoMedicamentoList = ordenReabastoMedicamentoList;
    }

     public String getDESCR1() {
        return DESCR1;
    }

    public void setDESCR1(String DESCR1) {
        this.DESCR1 = DESCR1;
    }

    @Override
    public String toString() {
        return "OrdenReabasto{" + "SETID=" + SETID + ", IMNF_UMF=" + IMNF_UMF + ", IMCEUCONDI_NFOLIO=" + IMCEUCONDI_NFOLIO + ", IMCADIT_SAIFOLIO=" + IMCADIT_SAIFOLIO + ", CREATE_DATE=" + CREATE_DATE + ", LAST_DTTM_UPDATE=" + LAST_DTTM_UPDATE + ", IMCADIT_PROCESSFLG=" + IMCADIT_PROCESSFLG + ", IM_CLASS_PTAL=" + IM_CLASS_PTAL + ", DEPTID=" + DEPTID + ", DESCR=" + DESCR + ", IM_ESPECIALIDAD=" + IM_ESPECIALIDAD + ", DESCR50=" + DESCR50 + ", AREA_CODE=" + AREA_CODE + ", AREA_DESCR=" + AREA_DESCR + ", IMCEUCONDI_TIPSOL=" + IMCEUCONDI_TIPSOL + ", IMCEUCONDI_ESTSOL=" + IMCEUCONDI_ESTSOL + ", DESCR1=" + DESCR1 + ", ordenReabastoMedicamentoList=" + ordenReabastoMedicamentoList + '}';
    }        

}
