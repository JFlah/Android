package com.example.jack.weather;

/**
 * Created by Jack on 4/12/2016.
 */
import android.graphics.Bitmap;

public class Weather {

    public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow()	;
    public Clouds clouds = new Clouds();

    public Bitmap iconData;

    public  class CurrentCondition {
        private int weatherId;
        private String condition;
        private String descr;
        private String icon;
        private float pressure;
        private float humidity;
        private int date;

        public int getDate() {
            return date;
        }
        public void setDate(int date) {
            this.date = date;
        }
        public int getWeatherId() {
            return weatherId;
        }
        public void setWeatherId(int weatherId) {
            this.weatherId = weatherId;
        }
        public String getCondition() {
            return condition;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
        public String getDescr() {
            return descr;
        }
        public void setDescr(String descr) {
            this.descr = descr;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public float getPressure() {
            return pressure;
        }
        public void setPressure(float pressure) {
            this.pressure = pressure;
        }
        public float getHumidity() {
            return humidity;
        }
        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }


    }

    public  class Temperature {
        private float temp;
        private float minTemp;
        private float maxTemp;

        public float getTemp() {
            return temp;
        }
        public void setTemp(float temp) {
            this.temp = temp;
        }
        public float getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }
        public float getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }

    }

    public  class Wind {
        private float speed;
        private float deg;
        public float getSpeed() {
            return speed;
        }
        public void setSpeed(float speed) {
            this.speed = speed;
        }
        public float getDeg() {
            return deg;
        }
        public void setDeg(float deg) {
            this.deg = deg;
        }


    }

    public  class Rain {
        private String time;
        private float ammount;
        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getAmmount() {
            return ammount;
        }
        public void setAmmount(float ammount) {
            this.ammount = ammount;
        }



    }

    public  class Snow {
        private String time;
        private float ammount;

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public float getAmmount() {
            return ammount;
        }
        public void setAmmount(float ammount) {
            this.ammount = ammount;
        }


    }

    public  class Clouds {
        private int perc;
        public int getPerc() {
            return perc;
        }
        public void setPerc(int perc) {
            this.perc = perc;
        }
    }

}