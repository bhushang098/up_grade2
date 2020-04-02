package my.upgrade007.upgrade;

public class DownloadedList {

    public DownloadedList(String name, String path) {
        this.name = name;
        this.path = path;
    }

    String name,path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
