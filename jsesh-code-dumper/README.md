# JSesh code dumper

A software to dump gardiner codes (only) from JSesh files. Works with individual files or with whole folders.

There was a javafx GUI, which I have removed as I don't have the time to maintain it.

## Usage

Compile with :

~~~bash
./gradlew build
~~~

The application is available in `build/libs`. The whole content of the `libs` folder is needed. 

You can run the app with :

~~~bash
java -jar build/libs/jsesh-code-dumper.jar SOURCE_FOLDER TARGET_FOLDER
~~~

It will search for jsesh files in the source folder, and dump a text file with the list of gardiner codes for each file in the target folder. The name of the text file is the same as the jsesh file, but with a `.txt` extension instead of `.gly`.