<img src="https://raw.github.com/neopoly/tagcloud/master/examples/rabbit.png" alt="Example of TagCloud" title="TagCloud" align="right" height="400"/>

# TagCloud

Tag clouds are very popular but seem to look always the same. TagCloud allows you to generate tag cloud images which base on an input word list and a shape image. It will try to fill the shape with the provided words and thereby choose a font size according to the weights.

Shapes can be provided a simple image files which have a clear foreground shape. You also can configure the colors which are used randomly for the words in the tag cloud.

This project bases on the impressive work of [Processing](http://processing.org) and [WordCram](http://wordcram.org). The generated images are inspired by the examples at [Tagxedo](http://www.tagxedo.com).


## Installation

1. 
    a) Clone this repository and build the jar, e.g. 
        
        $ ant -f tagcloud.xml
        
    b) Or use a prebuild tagcloud.jar from `/build`
    
2. Place the jar in an directory and provide the following files (Sample can be used from this project root directory):

        \
        - tagcloud.jar       
        - input.txt          The input file containing the words and weights
        - shape.png          The shape file
        - config.properties  The configuration for the generation process (see sample in repository)
  

## Usage

Provide a `input.txt` with an entry per line in the following format: `WORD,COUNT`. The `WORD` should not contain any `,` as this is used as a separator. 

### Example: input.txt

    acceptation, 5.0
    acceptance, 1.0
    appreciation, 2.0
    recognition, 3.0
    compliment, 1.0
    acknowledgement, 1.5
    praise, 6.3
    
You also must provide a `shape.png` image. It should have a white background and a black foreground. The image dimensions should match the configured result dimensions (see `config.properties`).

### Example: shape.png

<img src="https://raw.github.com/neopoly/tagcloud/master/examples/rabbit-shape.png" alt="Example of shape.png" title="Example of shape.png" height="400" border="1"/>

Your directory should also include a `config.properties` file for the generation process. See the included file as an example.

Now you can start the generation process with:

    $ java -jar tagcloud.jar
    
Per default the generated image will be written to `out.png`. TIFF, TARGA, JPEG, and PNG format are supported by determine the extension of the configure output file.

**Headless:** This project uses Processing which cannot be run without a frame buffer. The `-D java.awt.headless=true` trick doesn't work. In the project repository a simple shell script is included with can be used to generate an image on headless machines using `Xvfb`.

## TODO

* Allow configuration with command line arguments


## License

TagCloud is distributed under MIT license. See [LICENSE](https://raw.github.com/neopoly/tagcloud/master/LICENSE) for details.

The official Processing.org's jars, used as dependencies, are distributed under LGPL and their code can be found on http://processing.org/

The official WordCram's jars, also used as dependencies, are distributes under Apache License and their code can be found on http://wordcram.org