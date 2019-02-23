package c.example.speechtobsl.entities;

/**
 * The type Image.
 */
public class Image {

    private byte[] image;
    private String desc;

    /**
     * Instantiates a new Image.
     *
     * @param image the image
     * @param desc  the description
     */
    public Image(byte[] image, String desc) {
        this.image = image;
        this.desc = desc;
    }

    /**
     * Gets image.
     *
     * @return the byte[] for that image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Gets description of image.
     *
     * @return the description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets description of image
     *
     * @param desc the description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
