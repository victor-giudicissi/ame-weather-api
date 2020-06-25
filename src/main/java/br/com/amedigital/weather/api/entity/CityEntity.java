package br.com.amedigital.weather.api.entity;

public class CityEntity {
    private int id;
    private String name;
    private String uf;


    public CityEntity() { }

    public CityEntity(int id, String name, String uf) {
        this.id = id;
        this.name = name;
        this.uf = uf;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
