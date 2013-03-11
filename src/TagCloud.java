import processing.core.PApplet;

public class TagCloud {

    public static void main(String[] args){
        if(args.length < 3){
            System.out.println("Use can provide 3 arguments: 1st: shape file, 2nd text file, 3rd output file name");
            System.out.println("Defaults to: shape.png, input.txt, out.png");
        }
        else{
            Configuration.shape_file = args[0];
            Configuration.input_file = args[1];
            Configuration.output_file = args[2];
        }

        PApplet.main(new String[] { "--present", "Tree" });
    }
}
