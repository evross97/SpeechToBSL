package c.example.speechtobsl.entities;

public class Image {

    private byte[] image;
    private String desc;

    public Image(byte[] image, String desc) {
        this.image = image;
        this.desc = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
