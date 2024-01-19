package commons;

import java.util.Objects;

public class Emote {

    private String path;
    private String name;

    public Emote(String path, String name){
        this.path = path;
        this.name = name;
    }

    public Emote(){

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emote emote = (Emote) o;
        return Objects.equals(path, emote.path) && Objects.equals(name, emote.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, name);
    }
}
