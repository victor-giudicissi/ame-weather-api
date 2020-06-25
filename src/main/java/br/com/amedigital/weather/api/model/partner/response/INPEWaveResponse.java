package br.com.amedigital.weather.api.model.partner.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cidade")
public class INPEWaveResponse {
    private String name;
    private String state;
    private String update;
    private Wave morning;
    private Wave afternoon;
    private Wave night;

    public static class Wave {
        private String day;
        private String unrest;
        private float heigth;
        private String direction;
        private float wind;
        private String windDirection;

        @XmlElement(name = "day")
        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        @XmlElement(name = "agitacao")
        public String getUnrest() {
            return unrest;
        }

        public void setUnrest(String unrest) {
            this.unrest = unrest;
        }

        @XmlElement(name = "altura")
        public float getHeigth() {
            return heigth;
        }

        public void setHeigth(float heigth) {
            this.heigth = heigth;
        }

        @XmlElement(name = "direcao")
        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        @XmlElement(name = "vento")
        public float getWind() {
            return wind;
        }

        public void setWind(float wind) {
            this.wind = wind;
        }

        @XmlElement(name = "vento_dir")
        public String getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(String windDirection) {
            this.windDirection = windDirection;
        }
    }

    @XmlElement(name = "nome")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "uf")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlElement(name = "atualizacao")
    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    @XmlElement(name = "manha")
    public Wave getMorning() {
        return morning;
    }

    public void setMorning(Wave morning) {
        this.morning = morning;
    }

    @XmlElement(name = "tarde")
    public Wave getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(Wave afternoon) {
        this.afternoon = afternoon;
    }

    @XmlElement(name = "noite")
    public Wave getNight() {
        return night;
    }

    public void setNight(Wave night) {
        this.night = night;
    }
}
