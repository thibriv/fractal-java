/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.liienac.statemachine;

import fr.liienac.statemachine.event.Move;
import fr.liienac.statemachine.event.Press;
import fr.liienac.statemachine.event.Release;
import javafxdragpanzoom.statemachines.ITranslatable;

/**
 *
 * @author erber
 */
public class DragStateMachine extends StateMachine {
    

    public State start = new State() {
        Transition press = new Transition<Press>() {
            @Override
            public State goTo() {
                return dragging;
            }
        };
    };

    public State dragging = new State() {
        Transition up = new Transition<Release>() {
            @Override
            public State goTo() {
                return start;
            }
            Transition move = new Transition<Move>() {
                @Override
                public void action() {
                    ((ITranslatable) evt.graphicItem).translate(evt.p.x, evt.p.y);
                    //System.out.println("Drag" + evt.p.x + evt.p.y);
                }
                
                @Override
                public boolean guard(){
                    double seuil = 50;
                    boolean b = (evt.p.x*evt.p.x + evt.p.y*evt.p.y > seuil);
                    return b;
                }
            };
        };
    };
}
