/*******************************************************************************

"FreePastry" Peer-to-Peer Application Development Substrate

Copyright 2002-2007, Rice University. Copyright 2006-2007, Max Planck Institute 
for Software Systems.  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

- Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.

- Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.

- Neither the name of Rice  University (RICE), Max Planck Institute for Software 
Systems (MPI-SWS) nor the names of its contributors may be used to endorse or 
promote products derived from this software without specific prior written 
permission.

This software is provided by RICE, MPI-SWS and the contributors on an "as is" 
basis, without any representations or warranties of any kind, express or implied 
including, but not limited to, representations or warranties of 
non-infringement, merchantability or fitness for a particular purpose. In no 
event shall RICE, MPI-SWS or contributors be liable for any direct, indirect, 
incidental, special, exemplary, or consequential damages (including, but not 
limited to, procurement of substitute goods or services; loss of use, data, or 
profits; or business interruption) however caused and on any theory of 
liability, whether in contract, strict liability, or tort (including negligence
or otherwise) arising in any way out of the use of this software, even if 
advised of the possibility of such damage.

*******************************************************************************

*/

package osc.oscpeer;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPort;
import com.illposed.osc.OSCPortOut;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rice.p2p.commonapi.*;
import rice.pastry.leafset.LeafSet;

/**
 * A very simple application.
 * 
 * @author Jeff Hoye
 */
public class OSCApp implements Application {
    /**
   * The Endpoint represents the underlieing node.  By making calls on the 
   * Endpoint, it assures that the message will be delivered to a MyApp on whichever
   * node the message is intended for.
   */
  protected Endpoint endpoint;
  protected OSCPortOut sender;
  public OSCApp(Node node,OSCPortOut sender) {
    // We are only going to use one instance of this application on each PastryNode
    this.endpoint = node.buildEndpoint(this, "__OSCPEER");
    
    // the rest of the initialization code could go here
    
    // now we can receive messages
    this.endpoint.register();
    
    this.sender = sender;
  }

  /**
   * Called to route a message to the id
   */
  public void routeMyMsg(Id id) {
    System.out.println(this+" sending to "+id);    
    Message msg = new PeerMsg(endpoint.getId(), id,"Hola auto1");
    endpoint.route(id, msg, null);
  }
  
  /**
   * Called to directly send a message to the nh
   */
  public void routeMyMsgDirect(NodeHandle nh) {
    System.out.println(this+" sending direct to "+nh);    
    Message msg = new PeerMsg(endpoint.getId(), nh.getId(),"Hola auto");
    endpoint.route(null, msg, nh);
  }
  
  public void routeOscMessaget(NodeHandle nh,OSCMessage osc){
      System.out.println(this+" sending OSC direct to "+nh);    
      Message msg = new PeerOscMsg(endpoint.getId(),nh.getId(),osc);
       endpoint.route(null, msg, nh);
  }
    
  /**
   * Called when we receive a message.
   */
  @Override
  public void deliver(Id id, Message message) {
    System.out.println(this+" received "+message);
        try {
            PeerOscMsg m = (PeerOscMsg)message;
            
            OSCMessage msg = new OSCMessage("/p2p",m.getArguments());
            sender.send(msg);
            
        
        } catch (Exception ex) {
            
        }
    
    
  }

  /**
   * Called when you hear about a new neighbor.
   * Don't worry about this method for now.
   */
  @Override
  public void update(NodeHandle handle, boolean joined) {
  }
  
  /**
   * Called a message travels along your path.
   * Don't worry about this method for now.
   */
  @Override
  public boolean forward(RouteMessage message) {
    return true;
  }
  
  @Override
  public String toString() {
    return "OSC "+endpoint.getId();
  }
}
