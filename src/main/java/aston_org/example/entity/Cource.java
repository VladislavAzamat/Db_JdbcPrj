package aston_org.example.entity;

import java.util.Objects;

public class Cource {
    private Integer id;
    private String title;
    private Float duration;
    private Integer price;
    public Cource() {
    }
    public Cource(Integer id) {
        this.id = id;
    }

    public Cource(String title, Float duration, Integer price) {
        this.title = title;
        this.duration = duration;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cource cource = (Cource) o;
        return id == cource.id && Objects.equals(title, cource.title) && Objects.equals(duration, cource.duration) && Objects.equals(price, cource.price);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, duration, price);
    }

    @Override
    public String toString() {
        return "Cource{" +
                "id= " + id +
                ", title= " + title + '\'' +
                ", duration= " + duration +
                ", price= " + price +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }
}
