package model;

public class Local {
    private int idRegiao;
    private String idAreaAviso;
    private int idConcelho;
    private int globalIdLocal;
    private String latitude;
    private int idDistrito;
    private String local;
    private String longitude;

    public Local(int idRegiao, String idAreaAviso, int idConcelho, int globalIdLocal, String latitude, int idDistrito, String local, String longitude) {
        this.idRegiao = idRegiao;
        this.idAreaAviso = idAreaAviso;
        this.idConcelho = idConcelho;
        this.globalIdLocal = globalIdLocal;
        this.latitude = latitude;
        this.idDistrito = idDistrito;
        this.local = local;
        this.longitude = longitude;
    }

    public int getIdRegiao() {
        return idRegiao;
    }

    public String getIdAreaAviso() {
        return idAreaAviso;
    }

    public int getIdConcelho() {
        return idConcelho;
    }

    public int getGlobalIdLocal() {
        return globalIdLocal;
    }

    public String getLatitude() {
        return latitude;
    }

    public int getIdDistrito() {
        return idDistrito;
    }

    public String getLocal() {
        return local;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Local{" +
                "idRegiao=" + idRegiao +
                ", idAreaAviso='" + idAreaAviso + '\'' +
                ", idConcelho=" + idConcelho +
                ", globalIdLocal=" + globalIdLocal +
                ", latitude='" + latitude + '\'' +
                ", idDistrito=" + idDistrito +
                ", local='" + local + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
