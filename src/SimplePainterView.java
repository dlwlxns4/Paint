import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class SimplePainterView extends JPanel {
	private DrawController drawController;
	private JPanel menuPanel, optionPanel, messagePanel;
	private JButton[] btnMenuArray;
	private JTextField txtSize;
	private JLabel drawMode, drawColor, drawSize, drawPoint, drawPoint2;
	private JButton btnColorChooser;
	private JCheckBox chkFill;
	private Color c;
	public int savedTxtSize;
	private int chkClicked=-1; // ���� Ŭ���� �޴� �ε���
	
	public SimplePainterView() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(820, 830));
		setLayout(null);
		savedTxtSize=5; //�ʱ⿡ txtSize 5
		
		//Color chooser ���� ����ǰ� �ϱ����� ����
		c = Color.black;
		
		drawController = new DrawController(this);
		drawController.setBounds(10, 10, 800, 600); 
		drawController.setBorder(BorderFactory.createTitledBorder("Drawing"));
		add(drawController);
		
		//menu
		menuPanel = new JPanel();
		menuPanel.setBounds(10, 610, 300, 200);
		menuPanel.setBackground(Color.white);
		menuPanel.setBorder(BorderFactory.createTitledBorder("MENU"));
		menuPanel.setLayout(new GridLayout(3,3));
		add(menuPanel);
		
		//option
		optionPanel = new JPanel();
		optionPanel.setBounds(310, 610, 200, 200);
		optionPanel.setBackground(Color.white);
		optionPanel.setBorder(BorderFactory.createTitledBorder("OPTION"));
		optionPanel.setLayout(new GridLayout(3,1));
		add(optionPanel);
		
		//message
		messagePanel = new JPanel();
		messagePanel.setBounds(510, 610, 300, 200);
		messagePanel.setBackground(Color.white);
		messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGE"));
		messagePanel.setLayout(null);
		drawMode = new JLabel();
		drawMode.setBounds(20, 20, 280, 35); 
		drawMode.setHorizontalTextPosition(SwingConstants.LEFT);
		drawMode.setFont(new Font("Verdana", Font.BOLD, 18));
		messagePanel.add(drawMode);// drawMode ��Ÿ���ִ� JLabel
		drawColor = new JLabel("");
		drawColor.setBounds(20, 55, 280, 35); 
		drawColor.setHorizontalTextPosition(SwingConstants.LEFT);
		drawColor.setFont(new Font("Verdana", Font.BOLD, 18));
		messagePanel.add(drawColor);// drawColor ��Ÿ���ִ� JLabel
		drawSize = new JLabel("");
		drawSize.setBounds(20, 90, 280, 35); 
		drawSize.setHorizontalTextPosition(SwingConstants.LEFT);
		drawSize.setFont(new Font("Verdana", Font.BOLD, 18));
		messagePanel.add(drawSize);// drawSize ��Ÿ���ִ� JLabel
		drawPoint = new JLabel("");
		drawPoint.setBounds(20, 125, 280, 35); 
		drawPoint.setHorizontalTextPosition(SwingConstants.LEFT);
		drawPoint.setFont(new Font("Verdana", Font.BOLD, 15));
		messagePanel.add(drawPoint);// drawSize ��Ÿ���ִ� JLabel DOT
		drawPoint2 = new JLabel("");
		drawPoint2.setBounds(20, 160, 280, 35); 
		drawPoint2.setHorizontalTextPosition(SwingConstants.LEFT);
		drawPoint2.setFont(new Font("Verdana", Font.BOLD, 15));
		messagePanel.add(drawPoint2);// drawSize ��Ÿ���ִ� JLabel LINE,RECT,OVAL
		
		
		
		add(messagePanel);
		
		btnMenuArray = new JButton[9];
		for(int i=0; i<9; i++) {
			btnMenuArray[i] = new JButton(Constants.MENU[i]);
			btnMenuArray[i].setBackground(Constants.HOVERING[0]);
			btnMenuArray[i].setForeground(Constants.HOVERING[1]);
			btnMenuArray[i].addMouseListener(new HoverListener());
			btnMenuArray[i].addActionListener(new MenuListener());
			menuPanel.add(btnMenuArray[i]);
		} // for
		
		
		btnColorChooser = new JButton("COLOR CHOOSER");
		btnColorChooser.setBackground(Constants.HOVERING[0]);
		btnColorChooser.setForeground(Constants.HOVERING[1]);
		btnColorChooser.addMouseListener(new HoverListener());
		btnColorChooser.addActionListener(new MenuListener());
		optionPanel.add(btnColorChooser);
		
		txtSize = new JTextField();
		txtSize.setFont(new Font("Verdana", Font.BOLD, 16));
		txtSize.setVisible(false);
		optionPanel.add(txtSize);
	
		chkFill = new JCheckBox("FILL");
		chkFill.setBackground(Color.white);
		chkFill.setFont(new Font("Verdana", Font.BOLD, 16));
		chkFill.setVisible(false);
		optionPanel.add(chkFill);
		
		
	}//SimplePainterView
	
	public void setTxtSize(int size){	txtSize.setText(Integer.toString(size));}
	public int getTxtSize() {	
		if( txtSize.getText().toString().length() == 0) {
			return 0; // �������� Ȯ���ϱ� ���Ͽ�
		}
		
		savedTxtSize = Integer.parseInt(txtSize.getText());
		return savedTxtSize;
		}
	
	public void setDrawMode(String txt) {drawMode.setText(txt);} // ��ο��� �޼����� ǥ��
	public void setDrawColor(String Colortxt, Color c) { drawColor.setText(Colortxt); drawColor.setForeground(c); } // ���� �޼����� ǥ��
	public void setDrawSize(int size) { drawSize.setText("TxtSize: "+size ); } // ������ �޼����� ǥ��
	public void setDrawPointDot(int x, int y) { drawPoint.setText("Pt1.x : " + x + " Pt1.y : " + y); } //Dotǥ��
	public void setDrawPointDot(int x1, int y1, int x2, int y2) { 
		drawPoint.setText("Pt1.x : " + x1 + " Pt1.y : " + y1);
		drawPoint2.setText("Pt2.x : " + x2 + " Pt2.y : " + y2);
	} // Line Oval Rect ǥ��
	public boolean getChkFill() {return chkFill.isSelected();}
	
	private class HoverListener implements MouseListener{

		@Override
		public void mouseEntered(MouseEvent e) {
			JButton btnMenu = (JButton)e.getSource();
			btnMenu.setBackground(Constants.HOVERING[2]);
			btnMenu.setForeground(Constants.HOVERING[3]);	
		}
		@Override
		public void mouseExited(MouseEvent e) {
			JButton btnMenu = (JButton)e.getSource();
			btnMenu.setBackground(Constants.HOVERING[0]);
			btnMenu.setForeground(Constants.HOVERING[1]);
			
			if(chkClicked != -1) {
			btnMenuArray[chkClicked].setBackground(Constants.HOVERING[2]);
			btnMenuArray[chkClicked].setForeground(Constants.HOVERING[3]); // Ŭ���� ��ư�� ������ ���� �״�� �����ϰ� �����
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {
			JButton btnMenu = (JButton)e.getSource();
			btnMenu.setBackground(Constants.HOVERING[2]);
			btnMenu.setForeground(Constants.HOVERING[3]);	    // Ŭ���� ��ư å�� ĥ�ϱ�
			if(btnMenu == btnMenuArray[0]) chkClicked =0;       //
			else if (btnMenu == btnMenuArray[1]) chkClicked =1; //
			else if (btnMenu == btnMenuArray[2]) chkClicked =2; //
			else if (btnMenu == btnMenuArray[3]) chkClicked =3; //
			else if (btnMenu == btnMenuArray[4]) chkClicked =4; //
			else if (btnMenu == btnMenuArray[5]) chkClicked =5; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ� 
			else if (btnMenu == btnMenuArray[6]) chkClicked =6; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ� 
			else if (btnMenu == btnMenuArray[7]) chkClicked =7; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ�
			else if (btnMenu == btnMenuArray[8]) chkClicked =8; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ�  
		}	//��ư �������� ��Ȯ�� ���̱����� ������� �߰�
		@Override
		public void mouseClicked(MouseEvent e) {
			JButton btnMenu = (JButton)e.getSource();
			for(int i=0; i<9; i++) {
				btnMenuArray[i].setBackground(Constants.HOVERING[0]);
				btnMenuArray[i].setForeground(Constants.HOVERING[1]);
			}
			btnMenu.setBackground(Constants.HOVERING[2]);
			btnMenu.setForeground(Constants.HOVERING[3]);	    // Ŭ���� ��ư å�� ĥ�ϱ�
			if(btnMenu == btnMenuArray[0]) chkClicked =0;       //
			else if (btnMenu == btnMenuArray[1]) chkClicked =1; //
			else if (btnMenu == btnMenuArray[2]) chkClicked =2; //
			else if (btnMenu == btnMenuArray[3]) chkClicked =3; //
			else if (btnMenu == btnMenuArray[4]) chkClicked =4; //
			else if (btnMenu == btnMenuArray[5]) chkClicked =5; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ� 
			else if (btnMenu == btnMenuArray[6]) chkClicked =6; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ� 
			else if (btnMenu == btnMenuArray[7]) chkClicked =7; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ�
			else if (btnMenu == btnMenuArray[8]) chkClicked =8; // Ŭ���� ��ȣ�� �ش��ϴ� �� �ֱ�  

		}
	}
	
	private class MenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object obj = e.getSource();
			
			txtSize.setVisible(true);
			chkFill.setVisible(false);
			
			if(obj == btnColorChooser) {
				c = JColorChooser.showDialog(btnColorChooser, "COLOR CHOOSER", c); // ���� �ѵ� �� �״�� ����
				drawController.setSelectedColor(c) ;
			}
			
			
			for(int i=0; i<9; i++) {
				if( obj == btnMenuArray[i]) {
					
					drawController.setDrawMode(i);
					if( i==Constants.RECT || i == Constants.OVAL)
						chkFill.setVisible(true);
					
					else if ( i== Constants.CLEAR) {
						chkFill.setVisible(false);
						drawController.resetPaint(); // ����Ʈ �ʱ�ȭ�ϱ�
						drawMode.setText(""); // �ؽ�Ʈ �ʱ�ȭ
						drawColor.setText(""); // �ؽ�Ʈ �ʱ�ȭ
						drawSize.setText(""); // �ؽ�Ʈ�ʱ�ȭ
						drawPoint.setText(""); // �ؽ�Ʈ�ʱ�ȭ
						drawPoint2.setText(""); // �ؽ�Ʈ�ʱ�ȭ
					}
					else if ( i== Constants.UNDO) {
						drawController.paintUndo(); //������ �׸� �Լ� �����
						drawMode.setText(""); // �ؽ�Ʈ �ʱ�ȭ
						drawColor.setText(""); // �ؽ�Ʈ �ʱ�ȭ
						drawSize.setText(""); // �ؽ�Ʈ�ʱ�ȭ
						drawPoint.setText(""); // �ؽ�Ʈ�ʱ�ȭ
						drawPoint2.setText(""); // �ؽ�Ʈ�ʱ�ȭ
					}
				}
			}
			
		}//actionPerformed()
		
	}

}
