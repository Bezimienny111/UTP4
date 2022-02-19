package zad2;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Futil {
	static String setIn = "Cp1250";
	static String setOut = "UTF-8";
	static Path outPathForFile;
	static FileChannel output;
	static OpenOption ONE = StandardOpenOption.CREATE;
	static OpenOption TWO = StandardOpenOption.APPEND;
	static FileVisitOption OPTION = FileVisitOption.FOLLOW_LINKS;
	static FileChannel input;
	static ByteBuffer buff;
	public static void processDir(String dir, String file) {
		String fullPath = "." +"/"+file;
		outPathForFile = Paths.get(fullPath);
		boolean exists = Files.exists(outPathForFile);
		if(exists) {
			try {
				Files.delete(outPathForFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Path toGo = Paths.get(dir);
			output = FileChannel.open(outPathForFile, new OpenOption[] {ONE, TWO});
			Files.walk(toGo, OPTION).filter(Files::isRegularFile)
			.forEach(new Consumer<Path>(){

				@Override
				public void accept(Path go) {
					try {
						input = FileChannel.open(go);
						File tmp = go.toFile();
						int size = (int) tmp.toString().length();
						buff = ByteBuffer.allocate(size);
						buff.clear();
						input.read(buff);
					} catch (IOException error1) {
						System.err.println("Coś poszło nie tak przy wczytywaniu");
						error1.printStackTrace();
					}
					buff.flip();
					CharBuffer decoderOfInput = Charset.forName(setIn).decode(buff);
					ByteBuffer decoderToOutput = Charset.forName(setOut).encode(decoderOfInput);
					while (decoderToOutput.hasRemaining())
						try {
							output.write(decoderToOutput);
						} catch (IOException error2) {
							System.err.println("Coś poszło nie tak przyzapisywaniu");
							error2.printStackTrace();
						}
			
				}
					
			});
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
