package osc.oscpeer;

import com.illposed.osc.OSCMessage;
import java.util.List;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

/**
 *
 * @author alex
 */
public class PeerOscMsg implements Message{
    
    private Id from;
    private Id to;
    
    String address;
    private List<Object> arguments;

    public PeerOscMsg(Id from , Id to, OSCMessage message)
    {
        this.from = from;
        this.to = to;
                
        address = message.getAddress();        
        arguments = message.getArguments();
                
    }

    public PeerOscMsg() {
    }
    
    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }

    public Id getFrom() {
        return from;
    }

    public void setFrom(Id from) {
        this.from = from;
    }

    public Id getTo() {
        return to;
    }

    public void setTo(Id to) {
        this.to = to;
    }
    
     
    
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("Message from ");
        build.append(from.toString());
        build.append(" to ");
        build.append(to.toString());
        build.append(": ");
        build.append(address);
        build.append(":\n ");
        for(Object obj : arguments)
        {
            build.append(obj.toString());
            build.append("\n");
        }
        return build.toString();
    }

    /**
    * Use low priority to prevent interference with overlay maintenance traffic.
    */
    @Override
    public int getPriority() {
        return Message.DEFAULT_PRIORITY;
    }
    
}
