/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osc.oscpeer;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import java.util.Date;

/**
 *
 * @author alex
 */
public class OscAction implements OSCListener {

        @Override
        public void acceptMessage(Date time, OSCMessage message) {
            System.out.println("Mensaje enviado "+message.getAddress());             
            
            /*try{
                
                LeafSet lf = node.getLeafSet();
                for (int i=0; i<lf.cwSize(); i++) {
                    if(i==0)
                        continue;
                    
                    // select the item
                    NodeHandle nh = lf.get(i);
                    application.routeOscMessaget(nh, message);
                    enviroment.getTimeSource().sleep(500);
                    
                }
            }catch(Exception e){
                
            }
            */
        }
        
    }