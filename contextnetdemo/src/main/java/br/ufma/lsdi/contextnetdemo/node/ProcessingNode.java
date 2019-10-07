package br.ufma.lsdi.contextnetdemo.node;

import br.ufma.lsdi.contextnetdemo.data.MessageData;
import br.ufma.lsdi.contextnetdemo.event.NewMessageDataEvent;
import com.google.gson.Gson;
import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.groups.Group;
import lac.cnclib.net.groups.GroupCommunicationManager;
import lac.cnclib.net.groups.GroupMembershipListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
import lombok.Getter;
import lombok.val;
import org.greenrobot.eventbus.EventBus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessingNode implements NodeConnectionListener, GroupMembershipListener {

    @Getter
    private UUID uuid;
    private MrUdpNodeConnection connection;
    private GroupCommunicationManager groupManager;

    public ProcessingNode() {
        uuid = UUID.randomUUID();
        connect2Gateway();
    }

    private void connect2Gateway() {
        val address = new InetSocketAddress("127.0.0.1", 5500);
        try {
            connection = new MrUdpNodeConnection(uuid);
            connection.addNodeConnectionListener(this);
            connection.connect(address);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connected(NodeConnection nodeConnection) {
        System.out.println("Processing node connected to the gateway.");
        registerOnGroup(nodeConnection);
        sendBogusMessage();
        sendNewProcessingNodeMessage();
    }

    private void sendNewProcessingNodeMessage() {
        val applicationMessage = new ApplicationMessage();
        val messageData = new MessageData();
        messageData.setType("uuid");
        messageData.setMessage(uuid.toString());
        val json = new Gson().toJson(messageData);
        applicationMessage.setContentObject(json);
        new Thread(() -> {
            try {
                while(true) {
                    groupManager.sendGroupcastMessage(applicationMessage, new Group(101, 1));
                    Thread.sleep(5000);
                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void sendBogusMessage() {
        ApplicationMessage bogus = new ApplicationMessage();
        bogus.setContentObject("Bogus");
        try {
            connection.sendMessage(bogus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerOnGroup(NodeConnection nodeConnection) {
        groupManager = new GroupCommunicationManager(nodeConnection);
        groupManager.addMembershipListener(this);
    }

    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {

    }

    @Override
    public void disconnected(NodeConnection nodeConnection) {

    }

    @Override
    public void newMessageReceived(NodeConnection nodeConnection, Message message) {
        val json = (String) message.getContentObject();
        val messageData = new Gson().fromJson(json, MessageData.class);
        EventBus.getDefault().post(new NewMessageDataEvent(messageData));
    }

    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {

    }

    @Override
    public void internalException(NodeConnection nodeConnection, Exception e) {

    }

    public void sendMessage(String message, String uuid) {
        ApplicationMessage applicationMessage = new ApplicationMessage();
        val messageData = new MessageData();
        messageData.setType("message");
        messageData.setMessage(message);
        messageData.setSender(uuid);
        val json = new Gson().toJson(messageData);
        applicationMessage.setContentObject(json);
        applicationMessage.setRecipientID(UUID.fromString(uuid));
        try {
            connection.sendMessage(applicationMessage);
            System.out.println("Message sent...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enteringGroups(List<Group> list) {
        System.out.println("Entered in groups " + list);
    }

    @Override
    public void leavingGroups(List<Group> list) {

    }
}

