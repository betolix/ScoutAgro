package io.h3llo.scoutagro;

public class SectorEstadistico {

    int item;
    String departamento;
    String provincia;
    String distrito;
    String sector_nombre;
    String cultivo;
    String superf_aseg_has;
    String rend_prom_kg_x_ha;
    String rend_disp;
    String nom_file;

    public SectorEstadistico(int item, String departamento, String provincia, String distrito, String sector_nombre, String cultivo, String superf_aseg_has, String rend_prom_kg_x_ha, String rend_disp, String nom_file) {
        this.item = item;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.sector_nombre = sector_nombre;
        this.cultivo = cultivo;
        this.superf_aseg_has = superf_aseg_has;
        this.rend_prom_kg_x_ha = rend_prom_kg_x_ha;
        this.rend_disp = rend_disp;
        this.nom_file = nom_file;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getSector_nombre() {
        return sector_nombre;
    }

    public void setSector_nombre(String sector_nombre) {
        this.sector_nombre = sector_nombre;
    }

    public String getCultivo() {
        return cultivo;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    public String getSuperf_aseg_has() {
        return superf_aseg_has;
    }

    public void setSuperf_aseg_has(String superf_aseg_has) {
        this.superf_aseg_has = superf_aseg_has;
    }

    public String getRend_prom_kg_x_ha() {
        return rend_prom_kg_x_ha;
    }

    public void setRend_prom_kg_x_ha(String rend_prom_kg_x_ha) {
        this.rend_prom_kg_x_ha = rend_prom_kg_x_ha;
    }

    public String getRend_disp() {
        return rend_disp;
    }

    public void setRend_disp(String rend_disp) {
        this.rend_disp = rend_disp;
    }

    public String getNom_file() {
        return nom_file;
    }

    public void setNom_file(String nom_file) {
        this.nom_file = nom_file;
    }
}
