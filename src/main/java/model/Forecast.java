package model;

import java.time.LocalDate;


public class Forecast {
    private String precipitaProb;
    private String tMin;
    private String tMax;
    private String predWinDir;
    private int idWeaterType;
    private int classWindSpeed;
    private String longitude;
    private String forecastDate;
    private String latitude;

    public Forecast(String precipitaProb, String tMin, String tMax, String predWinDir, int idWeaterType, int classWindSpeed, String longitude, String forecastDate, String latitude) {
        this.precipitaProb = precipitaProb;
        this.tMin = tMin;
        this.tMax = tMax;
        this.predWinDir = predWinDir;
        this.idWeaterType = idWeaterType;
        this.classWindSpeed = classWindSpeed;
        this.longitude = longitude;
        this.forecastDate = forecastDate;
        this.latitude = latitude;
    }

    public String getPrecipitaProb() {
        return precipitaProb;
    }

    public void setPrecipitaProb(String precipitaProb) {
        this.precipitaProb = precipitaProb;
    }

    public String gettMin() {
        return tMin;
    }

    public void settMin(String tMin) {
        this.tMin = tMin;
    }

    public String gettMax() {
        return tMax;
    }

    public void settMax(String tMax) {
        this.tMax = tMax;
    }

    public String getPredWinDir() {
        return predWinDir;
    }

    public void setPredWinDir(String predWinDir) {
        this.predWinDir = predWinDir;
    }

    public int getIdWeaterType() {
        return idWeaterType;
    }

    public void setIdWeaterType(int idWeaterType) {
        this.idWeaterType = idWeaterType;
    }

    public int getClassWindSpeed() {
        return classWindSpeed;
    }

    public void setClassWindSpeed(int classWindSpeed) {
        this.classWindSpeed = classWindSpeed;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "precipitaProb='" + precipitaProb + '\'' +
                ", tMin='" + tMin + '\'' +
                ", tMax='" + tMax + '\'' +
                ", predWinDir='" + predWinDir + '\'' +
                ", idWeaterType=" + idWeaterType +
                ", classWindSpeed=" + classWindSpeed +
                ", longitude='" + longitude + '\'' +
                ", forecastDate=" + forecastDate +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
