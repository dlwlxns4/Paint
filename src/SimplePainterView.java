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
	private int chkClicked=-1; // 현재 클릭한 메뉴 인덱스
	
	public SimplePainterView() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(820, 830));
		setLayout(null);
		savedTxtSize=5; //초기에 txtSize 5
		
		//Color chooser 색깔 저장되게 하기위한 변수
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
		messagePanel.add(drawMode);// drawMode 나타내주는 JLabel
		drawColor = new JLabel("");
		drawColor.setBounds(20, 55, 280, 35); 
		drawColor.setHorizontalTextPosition(SwingConstants.LEFT);
		drawColor.setFont(new Font("Verdana", Font.BOLD, 18));
		messagePanel.add(drawColor);// drawColor 나타내주는 JLabel
		drawSize = new JLabel("");
		drawSize.setBounds(20, 90, 280, 35); 
		drawSize.setHorizontalTextPosition(SwingConstants.LEFT);
		drawSize.setFont(new Font("Verdana", Font.BOLD, 18));
		messagePanel.add(drawSize);// drawSize 나타내주는 JLabel
		drawPoint = new JLabel("");
		drawPoint.setBounds(20, 125, 280, 35); 
		drawPoint.setHorizontalTextPosition(SwingConstants.LEFT);
		drawPoint.setFont(new Font("Verdana", Font.BOLD, 15));
		messagePanel.add(drawPoint);// drawSize 나타내주는 JLabel DOT
		drawPoint2 = new JLabel("");
		drawPoint2.setBounds(20, 160, 280, 35); 
		drawPoint2.setHorizontalTextPosition(SwingConstants.LEFT);
		drawPoint2.setFont(new Font("Verdana", Font.BOLD, 15));
		messagePanel.add(drawPoint2);// drawSize 나타내주는 JLabel LINE,RECT,OVAL
		
		
		
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
			return 0; // 공백인지 확인하기 위하여
		}
		
		savedTxtSize = Integer.parseInt(txtSize.getText());
		return savedTxtSize;
		}
	
	public void setDrawMode(String txt) {drawMode.setText(txt);} // 드로우모드 메세지에 표시
	public void setDrawColor(String Colortxt, Color c) { drawColor.setText(Colortxt); drawColor.setForeground(c); } // 색깔 메세지에 표시
	public void setDrawSize(int size) { drawSize.setText("TxtSize: "+size ); } // 사이즈 메세지에 표시
	public void setDrawPointDot(int x, int y) { drawPoint.setText("Pt1.x : " + x + " Pt1.y : " + y); } //Dot표시
	public void setDrawPointDot(int x1, int y1, int x2, int y2) { 
		drawPoint.setText("Pt1.x : " + x1 + " Pt1.y : " + y1);
		drawPoint2.setText("Pt2.x : " + x2 + " Pt2.y : " + y2);
	} // Line Oval Rect 표시
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
			btnMenuArray[chkClicked].setForeground(Constants.HOVERING[3]); // 클릭한 버튼만 나가도 색깔 그대로 유지하게 만들기
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {
			JButton btnMenu = (JButton)e.getSource();
			btnMenu.setBackground(Constants.HOVERING[2]);
			btnMenu.setForeground(Constants.HOVERING[3]);	    // 클릭한 버튼 책깔 칠하기
			if(btnMenu == btnMenuArray[0]) chkClicked =0;       //
			else if (btnMenu == btnMenuArray[1]) chkClicked =1; //
			else if (btnMenu == btnMenuArray[2]) chkClicked =2; //
			else if (btnMenu == btnMenuArray[3]) chkClicked =3; //
			else if (btnMenu == btnMenuArray[4]) chkClicked =4; //
			else if (btnMenu == btnMenuArray[5]) chkClicked =5; // 클릭한 번호에 해당하는 값 주기 
			else if (btnMenu == btnMenuArray[6]) chkClicked =6; // 클릭한 번호에 해당하는 값 주기 
			else if (btnMenu == btnMenuArray[7]) chkClicked =7; // 클릭한 번호에 해당하는 값 주기
			else if (btnMenu == btnMenuArray[8]) chkClicked =8; // 클릭한 번호에 해당하는 값 주기  
		}	//버튼 색입히기 정확도 높이기위에 릴리즈에도 추가
		@Override
		public void mouseClicked(MouseEvent e) {
			JButton btnMenu = (JButton)e.getSource();
			for(int i=0; i<9; i++) {
				btnMenuArray[i].setBackground(Constants.HOVERING[0]);
				btnMenuArray[i].setForeground(Constants.HOVERING[1]);
			}
			btnMenu.setBackground(Constants.HOVERING[2]);
			btnMenu.setForeground(Constants.HOVERING[3]);	    // 클릭한 버튼 책깔 칠하기
			if(btnMenu == btnMenuArray[0]) chkClicked =0;       //
			else if (btnMenu == btnMenuArray[1]) chkClicked =1; //
			else if (btnMenu == btnMenuArray[2]) chkClicked =2; //
			else if (btnMenu == btnMenuArray[3]) chkClicked =3; //
			else if (btnMenu == btnMenuArray[4]) chkClicked =4; //
			else if (btnMenu == btnMenuArray[5]) chkClicked =5; // 클릭한 번호에 해당하는 값 주기 
			else if (btnMenu == btnMenuArray[6]) chkClicked =6; // 클릭한 번호에 해당하는 값 주기 
			else if (btnMenu == btnMenuArray[7]) chkClicked =7; // 클릭한 번호에 해당하는 값 주기
			else if (btnMenu == btnMenuArray[8]) chkClicked =8; // 클릭한 번호에 해당하는 값 주기  

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
				c = JColorChooser.showDialog(btnColorChooser, "COLOR CHOOSER", c); // 껏다 켜도 색 그대로 유지
				drawController.setSelectedColor(c) ;
			}
			
			
			for(int i=0; i<9; i++) {
				if( obj == btnMenuArray[i]) {
					
					drawController.setDrawMode(i);
					if( i==Constants.RECT || i == Constants.OVAL)
						chkFill.setVisible(true);
					
					else if ( i== Constants.CLEAR) {
						chkFill.setVisible(false);
						drawController.resetPaint(); // 페인트 초기화하기
						drawMode.setText(""); // 텍스트 초기화
						drawColor.setText(""); // 텍스트 초기화
						drawSize.setText(""); // 텍스트초기화
						drawPoint.setText(""); // 텍스트초기화
						drawPoint2.setText(""); // 텍스트초기화
					}
					else if ( i== Constants.UNDO) {
						drawController.paintUndo(); //이전에 그린 함수 지우기
						drawMode.setText(""); // 텍스트 초기화
						drawColor.setText(""); // 텍스트 초기화
						drawSize.setText(""); // 텍스트초기화
						drawPoint.setText(""); // 텍스트초기화
						drawPoint2.setText(""); // 텍스트초기화
					}
				}
			}
			
		}//actionPerformed()
		
	}

}
