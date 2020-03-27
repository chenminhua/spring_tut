import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.List;

public class MMapBBDemo {

    static Path getFileURIFromResources(String fileName) throws Exception {
        ClassLoader classLoader = MMapBBDemo.class.getClassLoader();
        return Paths.get(classLoader.getResource(fileName).getPath());
    }

    static void testMMBBRead() throws Exception {
        // given
        CharBuffer charBuffer = null;
        Path pathToRead = getFileURIFromResources("fileToRead.txt");

        // when
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToRead, EnumSet.of(StandardOpenOption.READ))) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

            if (mappedByteBuffer != null) {
                charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
            }
        }
        System.out.println(charBuffer.toString());
    }

    static void testMMBBWrite() throws Exception {
        final CharBuffer charBuffer = CharBuffer.wrap("this will be written to the file");

        final Path pathToWrite = getFileURIFromResources("fileToWrite.txt");

        // when
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathToWrite, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, charBuffer.length());

            if (mappedByteBuffer != null) {
                mappedByteBuffer.put(Charset.forName("utf-8").encode(charBuffer));
            }
            mappedByteBuffer.force();
        } catch (Exception e) {
            e.printStackTrace();
        }


//
//        // then
//        final List<String> fileContent = Files.readAllLines(pathToWrite);
//        System.out.println(fileContent.get(0));

    }

    public static void main(String[] args) throws Exception {
        //testMMBBRead();
        testMMBBWrite();
    }

}
