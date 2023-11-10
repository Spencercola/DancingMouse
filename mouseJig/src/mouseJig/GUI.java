package mouseJig;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/*import java.awt.AWTException;
import java.awt.BorderLayout;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
*/

/*
	 * private JFrame frame; private JCheckBox c1, c2;
	 * 
	 * public GUI() { initialize(); }
	 * 
	 * public void initialize() { frame = new JFrame(); JPanel panel = new JPanel();
	 * 
	 * panel.setBorder(BorderFactory.createEmptyBorder(70, 150, 70, 150));
	 * panel.setLayout(new GridLayout(0, 1));
	 * 
	 * c1 = new JCheckBox("Enable jiggle"); c1.addActionListener(this);
	 * c1.setBounds(5, 5, 5, 5); c2 = new JCheckBox("Zen jiggle");
	 * c2.addActionListener(this); c2.setBounds(5, 5, 5, 5); panel.add(c1);
	 * panel.add(c2);
	 * 
	 * frame.add(panel, BorderLayout.CENTER);
	 * frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 * frame.setTitle("MouseJig"); frame.pack(); frame.setVisible(true);
	 * frame.setResizable(true);
	 * 
	 * }
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { if (c1.isSelected() &&
	 * !c2.isSelected()) { jiggle(); return; } if (c2.isSelected() &&
	 * c1.isSelected()) { zenJiggle(); return; } }
	 * 
	 * private void zenJiggle() {
	 * 
	 * }
	 * 
	 * private void jiggle() { Robot r; try { r = new Robot(); Point p =
	 * MouseInfo.getPointerInfo().getLocation(); int x = (int) p.getX(); int y =
	 * (int) p.getY(); r.mouseMove(x+=10, y+=10); } catch (AWTException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */

public class GUI implements ActionListener, MouseMotionListener {

	private JFrame jf;
	private JCheckBox c1;// c2;
	private boolean jigOn = false;
	private boolean isMouseMoving = false;
	private Robot r;

	public GUI() throws InterruptedException {
		initialize();
	}

	private void initialize() throws InterruptedException {
		jf = new JFrame("Dancing Mouse");
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(70, 150, 70, 150));
		panel.setLayout(new GridLayout(0, 1));
		
		c1 = new JCheckBox("Enable Jiggle");
		// c2 = new JCheckBox("Zen Jiggle");
		c1.addActionListener(this);
		// c2.addActionListener(this);
		panel.add(c1);
		// panel.add(c2);
		jf.add(panel, BorderLayout.CENTER);
		jf.pack();
		jf.addMouseMotionListener(this);

		Thread thread = new Thread(() -> {

			while (true) {
				//if the Enable Jig box is checked and the mouse is not currently moving, jiggle the mouse
				if (jigOn && !isMouseMoving) {
					Point p = MouseInfo.getPointerInfo().getLocation();
					r.mouseMove(p.x += 10, p.y -= 10);
					r.mouseMove(p.x -= 10, p.y += 10);
				}
				//wait 5 seconds to move the mouse again
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//reset isMouseMoving to false so that Jiggle can continue to run
				isMouseMoving = false;
			}
		});

		thread.setDaemon(true);
		thread.start();
		jf.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		jigOn = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		isMouseMoving = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		isMouseMoving = true;
	}

}
