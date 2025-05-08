package nio;// Modified Code – Writing to a File

import java.nio.file.Path;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class Ex1{

	static void NIOFileWriter(){

		Path path = Path.of("output.txt");
		String content = "This is the modification testing i am doing\nThanks";
		ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
		try(
			FileChannel channel = FileChannel.open(path, EnumSet.of(
				StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
			))
		){
			while(buffer.hasRemaining()){
				channel.write(buffer);
			}

		}catch(IOException e){
			e.printStackTrace();

		}
		System.out.println("Data written to file successfully.");
	}


	public static void main(String[] args) {

		Ex1.NIOFileWriter();
		
	}



}