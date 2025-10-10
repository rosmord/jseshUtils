# Large font importer

A system which can read an external font (typically a true type font) and export it to SVG.

I haven't touched the software for quite some time, and I should write a documentation, as I don't remember well what the options were supposed to do!

The *fonts description files* are supposed to contain the following **lines**:

1. a line with the font name : keyword `system` or `file` followed by font name or file path
2. the scale of the font;
3. a list of lines, each containing a character position followed by the Gardiner code to map it to.
