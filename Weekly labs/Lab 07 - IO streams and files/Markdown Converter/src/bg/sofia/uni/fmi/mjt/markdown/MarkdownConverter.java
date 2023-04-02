package bg.sofia.uni.fmi.mjt.markdown;

import bg.sofia.uni.fmi.mjt.markdown.tags.ClosingHtmlTags;
import bg.sofia.uni.fmi.mjt.markdown.tags.OpeningHtmlTags;
import bg.sofia.uni.fmi.mjt.markdown.tags.html.headings.Headings;

import java.io.*;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MarkdownConverter implements MarkdownConverterAPI {

    @Override
    public void convertMarkdown(Reader source, Writer output) {
        String line;

        try (var reader = new BufferedReader(source)) {
            output.write("<html>");
            output.flush();
            output.write("<body>");
            output.flush();
            while ((line = reader.readLine()) != null) {
                output.write(convertToHtml(line));
                output.flush();
            }
            output.write("</body>");
            output.flush();
            output.write("</html>");
            output.flush();

        } catch (IOException e) {
            throw new RuntimeException("There was problem while reading from the reader!", e);
        }
    }

    @Override
    public void convertMarkdown(Path from, Path to) {
        try (BufferedReader reader = Files.newBufferedReader(from)) {
            try (BufferedWriter writer = Files.newBufferedWriter(to)) {
                convertMarkdown(reader, writer);
            } catch (IOException e) {
                throw new RuntimeException("There was problem with path to!", e);
            }
        } catch (IOException e) {
            throw new IllegalStateException("A problem occurred while reading from a file", e);
        }
    }

    @Override
    public void convertAllMarkdownFiles(Path sourceDir, Path targetDir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir)) {

            for (Path filePath : stream) {
                String destinationFileName = filePath.getFileName().toString();
                destinationFileName = destinationFileName.replaceAll("\\.md", ".html");
                convertMarkdown(filePath, targetDir.resolve(destinationFileName));
            }

        } catch (IOException | DirectoryIteratorException e) {
            throw new RuntimeException("There was problem in iterating through the directory!", e);
        }
    }

    private String convertToHtml(String line) {
        line = convertHeading(line);
        line = convertBold(line);
        line = convertItalic(line);
        line = convertCode(line);

        return line + System.lineSeparator();
    }

    private String convertBold(String line) {
        return line.replaceFirst("\\*\\*", OpeningHtmlTags.BOLD.toString())
            .replaceAll("\\*\\*", ClosingHtmlTags.BOLD.toString());
    }

    private String convertItalic(String line) {
        return line.replaceFirst("\\*", OpeningHtmlTags.ITALIC.toString())
            .replaceAll("\\*", ClosingHtmlTags.ITALIC.toString());
    }

    private String convertCode(String line) {
        return line.replaceFirst("`", OpeningHtmlTags.CODE.toString())
            .replaceAll("`", ClosingHtmlTags.CODE.toString());
    }

    private String convertHeading(String line) {
        int start = Headings.Six.getNumber();

        for (int i = start; i > 0; i--) {
            StringBuilder regex = new StringBuilder("#{" + i + "}(.+?)");
            if (line.matches(regex.toString())) {
                line = convertRegex(line, regex.toString(), i);
            }
        }

        return line;
    }

    private String convertRegex(String line, String regex, int i) {
        final int headingOne = 1;
        final int headingTwo = 2;
        final int headingThree = 3;
        final int headingFour = 4;
        final int headingFive = 5;
        final int headingSix = 6;

        switch (i) {
            case headingOne -> {
                return line.replaceFirst(regex, OpeningHtmlTags.HEADER1.toString()) + ClosingHtmlTags.HEADER1;
            }
            case headingTwo -> {
                return line.replaceFirst(regex, OpeningHtmlTags.HEADER2.toString()) + ClosingHtmlTags.HEADER2;
            }
            case headingThree -> {
                return line.replaceFirst(regex, OpeningHtmlTags.HEADER3.toString()) + ClosingHtmlTags.HEADER3;
            }
            case headingFour -> {
                return line.replaceFirst(regex, OpeningHtmlTags.HEADER4.toString()) + ClosingHtmlTags.HEADER4;
            }
            case headingFive -> {
                return line.replaceFirst(regex, OpeningHtmlTags.HEADER5.toString()) + ClosingHtmlTags.HEADER5;
            }
            case headingSix -> {
                return line.replaceFirst(regex, OpeningHtmlTags.HEADER6.toString()) + ClosingHtmlTags.HEADER6;
            }
        }

        return line;
    }

}