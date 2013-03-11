import processing.core.PApplet;

public class TagCloud {

    public static void main(String[] args){
        Configuration.getInstance().load();
        PApplet.main(new String[] { "--present", "Tree" });
    }
}
