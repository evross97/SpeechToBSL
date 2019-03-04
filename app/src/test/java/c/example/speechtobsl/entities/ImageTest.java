package c.example.speechtobsl.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageTest {

    private Image img1;
    private Image img2;

    @Before
    public void setUp() throws Exception {
        this.img1 = new Image(new byte[]{}, "");
        this.img2 = new Image(new byte[]{80,65,78,75,65,74}, "trial");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getImage() {
        byte[] result1 = this.img1.getImage();
        assertEquals(0, result1.length);
        byte[] result2 = this.img2.getImage();
        assertEquals(6, result2.length);
    }

    @Test
    public void setImage() {
        byte[] result1 = this.img1.getImage();
        this.img1.setImage(new byte[]{62,65});
        byte[] result2 = this.img1.getImage();
        assertEquals(0, result1.length);
        assertEquals(2, result2.length);
    }

    @Test
    public void getDesc() {
        String result1 = this.img1.getDesc();
        assertEquals("", result1);

        String result2 = this.img2.getDesc();
        assertEquals("trial", result2);
    }

    @Test
    public void setDesc() {
        String result1 = this.img1.getDesc();
        this.img1.setDesc("image");
        String result2 = this.img1.getDesc();
        assertEquals("", result1);
        assertEquals("image", result2);
    }
}
