import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


interface Observer {
    void update(String message);
}

interface Subject {
    void addObserver(Observer o);
    void notifyObservers(String message);
}


class MotionSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    public void detectMotion() {
        notifyObservers("⚠ Motion detected!");
    }
}


class AlertSystem implements Observer {
    private JTextArea output;

    public AlertSystem(JTextArea output) {
        this.output = output;
    }

    public void update(String message) {
        output.append("AlertSystem: " + message + "\n");
    }
}


interface Command {
    void execute();
}


class EmergencyCommand implements Command {
    private JTextArea output;

    public EmergencyCommand(JTextArea output) {
        this.output = output;
    }

    public void execute() {
        output.append(" Emergency Action Triggered!\n");
    }
}


public class SimpleSurveillanceApp extends JFrame {
    private JTextArea outputArea;
    private MotionSensor sensor;
    private EmergencyCommand emergencyCommand;

    public SimpleSurveillanceApp() {
        setTitle("Simple Surveillance App");
        setSize(350, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        sensor = new MotionSensor();
        sensor.addObserver(new AlertSystem(outputArea));
        emergencyCommand = new EmergencyCommand(outputArea);

        JPanel panel = new JPanel();
        JButton detectBtn = new JButton("Detect Motion");
        JButton emergencyBtn = new JButton("Emergency Action");

        detectBtn.addActionListener(e -> sensor.detectMotion());
        emergencyBtn.addActionListener(e -> emergencyCommand.execute());

        panel.add(detectBtn);
        panel.add(emergencyBtn);
        add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleSurveillanceApp().setVisible(true);
        });
    }
}
