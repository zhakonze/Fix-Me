import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 *
 * Test client for NIO server
 *
 */
public class broker
{


    public void startClient() throws IOException, InterruptedException
    {

        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5000);
        SocketChannel client = SocketChannel.open(hostAddress);

        System.out.println("Connected to router. Router ID:");

        String threadName = Thread.currentThread().getName();

        // Send messages to server
        String[] messages = new String[] { threadName + ": msg1", threadName + ": msg2", threadName + ": msg3" , threadName + ": msg4"};

        for (int i = 0; i < messages.length; i++)
        {
            ByteBuffer buffer = ByteBuffer.allocate(74);
            buffer.put(messages[i].getBytes());
            buffer.flip();
            client.write(buffer);
            System.out.println(messages[i]);
            buffer.clear();
            Thread.sleep(5000);
        }
        client.close();
    }

    public static void main(String[] args)
    {
        Runnable client = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    new broker().startClient();
                }
                catch (IOException e)
                {
                    System.out.println("No connection was to port was found");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        };
        new Thread(client, "client-A").start();
        //new Thread(client, "client-B").start();
    }

}
