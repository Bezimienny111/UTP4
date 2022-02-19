package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil extends SimpleFileVisitor<Path>{
	String setIn = "Cp1250";
	String setOut = "UTF-8";
	static String endFileName;
	static Path outPathForFile;
	static FileChannel output;
	static OpenOption ONE = StandardOpenOption.CREATE;
	static OpenOption TWO = StandardOpenOption.APPEND;
	FileChannel input;
	ByteBuffer buff;
	FileVisitResult end = FileVisitResult.CONTINUE;
	
	public static void processDir(String dir, String file) {
		endFileName = file;
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
			output = FileChannel.open(outPathForFile, new OpenOption[] {ONE, TWO});
			Path toTree = Paths.get(dir);
			Path walkInTreeDir = Files.walkFileTree(toTree, new Futil());
		} catch(IOException error) {
			 System.err.println("Coś poszło nie tak :(");
			 error.printStackTrace();
		}
		
		
		
	}
	
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		// TODO Auto-generated method stub
		return end;
	}


	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		// TODO Auto-generated method stub
		return end;
	}


	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		boolean isFileTest = attrs.isRegularFile();
		if (isFileTest ) {
			input = FileChannel.open(file);
			int size = (int) attrs.size();
			buff = ByteBuffer.allocate(size);
			buff.clear();
			input.read(buff);
			buff.flip();
			CharBuffer decoderOfInput = Charset.forName(setIn).decode(buff);
			ByteBuffer decoderToOutput = Charset.forName(setOut).encode(decoderOfInput);
			while (decoderToOutput.hasRemaining())
				output.write(decoderToOutput);
		}
		
		
		return end;
	}


	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		System.err.println("Coś poszło nie tak :(");
		System.err.println(exc);
		return end;
	}
	
	
}
