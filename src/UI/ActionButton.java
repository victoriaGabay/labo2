package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Me evito tener que agregar el action listener cada vez que creo un boton. 
//Obligo a todos los botones a definir doSomething() y nada mas. Limpia un poco el codigo y evita repeticiones
public abstract class ActionButton {
    private JButton actionButton;

    public ActionButton(String btnText){
        this.actionButton = new JButton(btnText);

        this.actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSomething();
            }
        });
    }

    public abstract void doSomething();

    public JButton get(){
        return this.actionButton;
    }
}


