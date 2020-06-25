package br.com.amedigital.weather.api.model.partner.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cidades")
public class INPECityResponse {
    private List<City> cities;

    public static class City {
        private String name;
        private String uf;
        private int id;

        @XmlElement(name = "nome")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name = "uf")
        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        @XmlElement(name = "id")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    @XmlElement(name = "cidade")
    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
