package bg.sofia.uni.fmi.mjt.markdown;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarkdownConverterTest {
    MarkdownConverter markdownConverter = new MarkdownConverter();

//    @BeforeAll
//    static void init() throws IOException {
//        pathToTempFile =
//            "test" + File.separator + "bg" + File.separator + "sofia" + File.separator + "uni" + File.separator +
//                "fmi" + File.separator + "mjt" + File.separator + "markdown";
//        dir = new File(pathToTempFile);
//
//        File testFileFrom = File.createTempFile("TestFileFrom", ".txt", dir);
//        File testFileTo = File.createTempFile("TestFileTo", ".txt", dir);
//        try (var writer = new BufferedWriter(new FileWriter(testFileFrom))) {
//            writer.write("# Heading level 1");
//            writer.flush();
//        }
//
//        reader = new BufferedReader(new FileReader(testFileFrom));
//        writer = new BufferedWriter(new FileWriter(testFileTo));
//        markdownConverter = new MarkdownConverter();
//    }

    @Test
    void testConvertMarkdownHeadingOne() {
        String str = "# Heading level 1";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body><h1>Heading level 1</h1></body></html>"));
    }

    @Test
    void testConvertMarkdownHeadingTwo() {
        String str = "## Heading level 2";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body><h2>Heading level 2</h2></body></html>"));
    }

    @Test
    void testConvertMarkdownHeadingThree() {
        String str = "### Heading level 3";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body><h3>Heading level 3</h3></body></html>"));
    }

    @Test
    void testConvertMarkdownHeadingFour() {
        String str = "#### Heading level 4";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body><h4>Heading level 4</h4></body></html>"));
    }

    @Test
    void testConvertMarkdownHeadingFive() {
        String str = "##### Heading level 5";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body><h5>Heading level 5</h5></body></html>"));
    }

    @Test
    void testConvertMarkdownHeadingSix() {
        String str = "###### Heading level 6";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body><h6>Heading level 6</h6></body></html>"));
    }

    @Test
    void testConvertMarkdownBold() {
        String str = "I just love **bold text**.";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body>I just love <strong>bold text</strong>.</body></html>"));
    }

    @Test
    void testConvertMarkdownItalic() {
        String str = "Italicized text is the *cat's meow*.";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body>Italicized text is the <em>cat's meow</em>.</body></html>"));
    }

    @Test
    void testConvertMarkdownCode() {
        String str = "Always `.close()` your streams";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString().equals("<html><body>Always <code>.close()</code> your streams</body></html>"));
    }

    @Test
    void testConvertMarkdownMixed() {
        String str = "`.close()` *your* **eyes**";
        Reader reader = new StringReader(str);
        Writer writer = new StringWriter();
        markdownConverter.convertMarkdown(reader, writer);
        StringBuilder str2 = new StringBuilder();
        String line;
        try (var reader2 = new BufferedReader(new StringReader(writer.toString()))) {
            while ((line = reader2.readLine()) != null) {
                str2.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(str2.toString()
            .equals("<html><body><code>.close()</code> <em>your</em> <strong>eyes</strong></body></html>"));
    }

    @Test
    void testConvertMarkdownPaths() throws IOException {
        File directoryPath = new File(
            "test" + File.separator + "bg" + File.separator + "sofia" + File.separator + "uni" + File.separator +
                "fmi" + File.separator + "mjt" + File.separator + "markdown");

        File a = File.createTempFile("TempFile", ".txt", directoryPath);
        File b = File.createTempFile("TempFile", ".txt", directoryPath);

        Files.writeString(Path.of(a.getPath()), "# Heading level 1");

        markdownConverter.convertMarkdown(Path.of(a.getPath()), Path.of(b.getPath()));

        String str = Files.readString(Path.of(b.getPath()));

        Files.delete(Path.of(a.getPath()));
        Files.delete(Path.of(b.getPath()));

        assertTrue(str.toString().equals("<html><body><h1>Heading level 1</h1>" + System.lineSeparator() + "</body></html>"));
    }


    @Test
    void testConvertMarkdownDirectories() throws IOException {
        Path directoryPath = Path.of(
            "test" + File.separator + "bg" + File.separator + "sofia" + File.separator + "uni" + File.separator +
                "fmi" + File.separator + "mjt" + File.separator + "markdown");

        Path dir1 = Files.createTempDirectory(directoryPath, "dir1");
        Path dir2 = Files.createTempDirectory(directoryPath, "dir2");

        File a = File.createTempFile("TempFile", ".md", dir1.toFile());

        Files.writeString(Path.of(a.getPath()), "# Heading level 1");

        markdownConverter.convertAllMarkdownFiles(dir1, dir2);

        String str = new String();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir2)) {
            for (Path dir : stream) {
                str = Files.readString(dir);
            }
        } catch (IOException | DirectoryIteratorException e) {
            throw new RuntimeException("There was problem while iterating through directory!", e);
        }

        assertTrue(str.equals("<html><body><h1>Heading level 1</h1>" + System.lineSeparator() + "</body></html>"));
    }

    @Test
    void testConvertMarkdownDirectoriesHtmlExtension() throws IOException {
        Path directoryPath = Path.of(
            "test" + File.separator + "bg" + File.separator + "sofia" + File.separator + "uni" + File.separator +
                "fmi" + File.separator + "mjt" + File.separator + "markdown");

        Path dir1 = Files.createTempDirectory(directoryPath, "dir1");
        Path dir2 = Files.createTempDirectory(directoryPath, "dir2");

        File a = File.createTempFile("TempFile", ".md", dir1.toFile());

        Files.writeString(Path.of(a.getPath()), "# Heading level 1");

        markdownConverter.convertAllMarkdownFiles(dir1, dir2);

        String str = new String();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir2)) {
            for (Path dir : stream) {
                int index = dir.getFileName().toString().lastIndexOf(".");
                str = dir.getFileName().toString().substring(index);
                Files.delete(dir);
            }
        } catch (IOException | DirectoryIteratorException e) {
            throw new RuntimeException("There was problem while iterating through directory!", e);
        }
        Files.delete(dir2);

        Files.delete(Path.of(a.getPath()));
        Files.delete(dir1);

        assertTrue(str.equals(".html"));
    }

}
