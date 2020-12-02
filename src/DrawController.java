import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

public class DrawController extends JPanel {
	
	private SimplePainterModel nowData;
	private ArrayList<SimplePainterModel> savedList;
	private DrawListener drawL;
	private SimplePainterView view;
	private boolean bDrag;
	public DrawController(SimplePainterView v) {
		this.setBackground(Color.white);
		view = v;
		
		
		drawL = new DrawListener();
		addMouseListener(drawL);
		addMouseMotionListener(drawL);
		
		nowData = new SimplePainterModel();
		savedList = new ArrayList<SimplePainterModel>();
	
		nowData.nDrawMode = Constants.NONE;
		bDrag = false;
	}//Constructor
	
	public void setDrawMode(int mode) {
		nowData.nDrawMode = mode;
		
		if( nowData.nDrawMode == Constants.DOT) {
			view.setTxtSize(view.savedTxtSize);
		}
		else {
			if( view.getTxtSize() != 0)
			view.setTxtSize(view.getTxtSize()); // �ƴϸ� ������ ��
			else {
				view.setTxtSize(5); // 0�̸� 1�� ���ְ� �ʱ� txt������ 5
				view.savedTxtSize = view.getTxtSize();
			}
		}
	} // �׸� �޴� �ٸ� �� Ŭ�� ���� �� ���� txtSize����
	
	public void setSelectedColor(Color color) {
		nowData.selectedColor = color;
	}
	
	
	public void paintComponent(Graphics page) {
		super.paintComponent(page);

		Graphics2D page2 = (Graphics2D)page;
		
		if(nowData.nDrawMode == Constants.DOT) {
			page.setColor(nowData.selectedColor);
			page2.setStroke(new BasicStroke(nowData.nSize));
			page.fillOval(nowData.pt1.x-nowData.nSize/2,
					nowData.pt1.y-nowData.nSize/2,
					nowData.nSize, nowData.nSize);
			view.setDrawMode("DOT"); // ���� ��ο��� ǥ��
			view.setDrawColor("COLOR", nowData.selectedColor); // ���� ���� ǥ��
			view.setDrawSize(view.savedTxtSize); //���� �۾� ũ�� ǥ��
			view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y); // ���� ��ǥ ǥ��
		}
		
		if(bDrag) {
			//now data
			switch(nowData.nDrawMode){
			
				case Constants.LINE:
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					page.drawLine(nowData.pt1.x, nowData.pt1.y, 
							nowData.pt2.x, nowData.pt2.y);
					view.setDrawMode("LINE"); // ���� ��ο��� ǥ��
					view.setDrawColor("COLOR", nowData.selectedColor); // ���� ���� ǥ��
					view.setDrawSize(view.savedTxtSize); //���� �۾� ũ�� ǥ��
					view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y, nowData.pt2.x, nowData.pt2.y); // ���� ��ǥ ǥ��
					break;
				case Constants.RECT:
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4Rect(page, nowData.pt1, nowData.pt2, nowData.bFill);
					view.setDrawMode("RECT"); // ���� ��ο��� ǥ��
					view.setDrawColor("COLOR", nowData.selectedColor); // ���� ���� ǥ��
					view.setDrawSize(view.savedTxtSize); //���� �۾� ũ�� ǥ��
					view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y, nowData.pt2.x, nowData.pt2.y); // ���� ��ǥ ǥ��
					break;
				case Constants.OVAL:
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					drawOvalPaint(page, nowData.pt1, nowData.pt2, nowData.bFill);
					view.setDrawMode("OVAL"); // ���� ��ο� ��� ǥ��
					view.setDrawColor("COLOR", nowData.selectedColor); // ���� ���� ǥ��
					view.setDrawSize(view.savedTxtSize); //���� �۾� ũ�� ǥ��
					view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y, nowData.pt2.x, nowData.pt2.y); // ���� ��ǥ ǥ��
					break;
				case Constants.FREE:
						if(nowData.vStart.size() >1 ) { // vStart����� arrayList�� �ִ��� Ȯ��
						
						page.setColor(nowData.selectedColor);
						page2.setStroke(new BasicStroke(nowData.nSize));
						  for (int i = 1; i < nowData.vStart.size(); i++) { // ����� ArrayList�� ��������� �׸��׸��� ���ؼ�
							  if (nowData.vStart.get(i - 1) == null)
								  continue;
							  else if (nowData.vStart.get(i) == null)
								  continue;
							  else
								  page.drawLine((int) nowData.vStart.get(i - 1).getX(), (int) nowData.vStart.get(i - 1).getY(), 
										  (int) nowData.vStart.get(i).getX(), (int) nowData.vStart.get(i).getY()); // ���߱� 
						  }
					}
					break; //Dot
		
		}// switch
		} // if
		
		for(SimplePainterModel data:savedList) {
			switch(data.nDrawMode){
				case Constants.DOT:
					page.setColor(data.selectedColor);
					page.fillOval(data.pt1.x-data.nSize/2,
							data.pt1.y-data.nSize/2,
							data.nSize, data.nSize);
					break; //Dot
				case Constants.LINE:
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					page.drawLine(data.pt1.x, data.pt1.y, 
							data.pt2.x, data.pt2.y);
					break; //Line
				case Constants.RECT:
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					draw4Rect(page, data.pt1, data.pt2, data.bFill);
					break; // RECT
				case Constants.OVAL:
					page.setColor(data.selectedColor);
					page2.setStroke(new BasicStroke(data.nSize));
					drawOvalPaint(page, data.pt1,data.pt2, data.bFill);
					break; // OVAL
				case Constants.FREE:
					if(data.vStart.size()>1) { // ArrayList �� ����ִ��� Ȯ���ϱ� ���ؼ�
						page.setColor(data.selectedColor);
						page2.setStroke(new BasicStroke(data.nSize));
						  for (int i = 1; i < data.vStart.size(); i++) { // ������ũ�⸸ŭ ���߱�
							  if (data.vStart.get(i - 1) == null)
								  continue;
							  else if (data.vStart.get(i) == null)
								  continue;
							  else
								  page.drawLine((int) data.vStart.get(i - 1).getX(), (int) data.vStart.get(i - 1).getY(), 
										  (int) data.vStart.get(i).getX(), (int) data.vStart.get(i).getY());

						  }
					}
					break; //Free Line
	
			}// switch
		} // for
		
	}//paintComponent
	
	
	
	private void draw4Rect(Graphics page, Point pt1, Point pt2, boolean fill) {
		int x, y, width, height;
		x=y=width=height=0;
		
		if(pt1.x < pt2.x  && pt1.y < pt2.y) {
			x = pt1.x;
			y = pt1.y;
			width = pt2.x - pt1.x;
			height = pt2.y - pt1.y;
		}else if(pt1.x < pt2.x  && pt1.y > pt2.y) {
			x = pt1.x;
			y = pt2.y;
			width = pt2.x - pt1.x;
			height = pt1.y - pt2.y;
		}else if(pt1.x > pt2.x  && pt1.y < pt2.y) {
			x = pt2.x;
			y = pt1.y;
			width = pt1.x - pt2.x;
			height = pt2.y - pt1.y; 
		}else if(pt1.x > pt2.x  && pt1.y > pt2.y) {
			x = pt2.x;
			y = pt2.y;
			width = pt1.x - pt2.x;
			height = pt1.y - pt2.y;
		}
		
		if(fill) page.fillRect(x, y, width, height);
		else page.drawRect(x, y, width, height);
	}//draw4Rect �簢���׸���
	
	
	private void drawOvalPaint(Graphics page, Point pt1, Point pt2, boolean fill) {
		int x, y, width, height;
		x=y=width=height=0;
		
		if(pt1.x < pt2.x  && pt1.y < pt2.y) {
			x = pt1.x;
			y = pt1.y;
			width = pt2.x - pt1.x;
			height = pt2.y - pt1.y;
		}else if(pt1.x < pt2.x  && pt1.y > pt2.y) {
			x = pt1.x;
			y = pt2.y;
			width = pt2.x - pt1.x;
			height = pt1.y - pt2.y;
		}else if(pt1.x > pt2.x  && pt1.y < pt2.y) {
			x = pt2.x;
			y = pt1.y;
			width = pt1.x - pt2.x;
			height = pt2.y - pt1.y; 
		}else if(pt1.x > pt2.x  && pt1.y > pt2.y) {
			x = pt2.x;
			y = pt2.y;
			width = pt1.x - pt2.x;
			height = pt1.y - pt2.y;
		}
		
		if(fill) page.fillOval(x, y, width, height);
		else page.drawOval(x, y, width, height);
	}//drawOvalPaint �� �׸���
	
	public void resetPaint() {
		savedList.clear();
		repaint();
	} //Clear ��ư Ŭ���� ���� �Լ�
	public void paintUndo() {
		
		
		
		if(savedList.size() !=0) {
			
			savedList.remove(savedList.size()-1);
		
		repaint();
		}
	} //������ �׸� �׸� ����� �Լ�
	 
	
	private class DrawListener implements MouseListener, MouseMotionListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(nowData.nDrawMode == Constants.DOT) {
				nowData.pt1 = e.getPoint();
				nowData.nSize = view.getTxtSize();
				savedList.add(new SimplePainterModel(nowData));
				repaint();
			} // if
		}//mouseClicked
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(nowData.nDrawMode == Constants.LINE ) {
				bDrag=true;
				nowData.pt1 = e.getPoint();
				nowData.nSize = view.getTxtSize();
			}//if line
			else if(nowData.nDrawMode == Constants.RECT) {
				bDrag=true;
				nowData.pt1 = e.getPoint();
				nowData.nSize = view.getTxtSize();	
				nowData.bFill = view.getChkFill();
			}// if rect
			else if(nowData.nDrawMode == Constants.OVAL) {
				bDrag=true;
				nowData.pt1 = e.getPoint();
				nowData.nSize = view.getTxtSize();	
				nowData.bFill = view.getChkFill();	
			}// if oval
			else if(nowData.nDrawMode == Constants.FREE) {
				bDrag=true;

				nowData.nSize = view.getTxtSize();	
				nowData.vStart.add(null);
				nowData.vStart.add(e.getPoint());

			}// if free
		
		}
		//mousePressed
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(nowData.nDrawMode == Constants.LINE) {
				bDrag=false;
				nowData.pt2 = e.getPoint();
				savedList.add(new SimplePainterModel(nowData));
				repaint();
			}//if Line
			else if(nowData.nDrawMode == Constants.RECT) {
				bDrag=false;
				nowData.pt2 = e.getPoint();
				savedList.add(new SimplePainterModel(nowData));
				repaint();
			}// if rect
			else if(nowData.nDrawMode == Constants.OVAL) {
				bDrag=false;
				nowData.pt2 = e.getPoint();
				savedList.add(new SimplePainterModel(nowData));
				repaint();
			}// if oval
			else if(nowData.nDrawMode == Constants.FREE) {
				bDrag=false;
				nowData.vStart.add(e.getPoint()); // free���� arrayList�� ��ǥ����
				nowData.vStart.add(null); // �Ǽ� �ּ�ȭ�Ϸ���
				savedList.add(new SimplePainterModel(nowData)); // savedList�� �����ϱ�
				System.out.println(nowData.vStart.size()); // �̰� �׳� �����
				nowData.vStart.removeAllElements(); // ���������� ��Ұ� �ٽ� �ʱ�ȭ
				
			}// if free
			

		}//mouseReleased

		@Override
		public void mouseDragged(MouseEvent e) {
			if(nowData.nDrawMode == Constants.LINE) {
				nowData.pt2 = e.getPoint();
				repaint();
				
			}//if line
			else if(nowData.nDrawMode == Constants.RECT) {
				nowData.pt2 = e.getPoint();

				repaint();
				
			}// if rect
			else if(nowData.nDrawMode == Constants.OVAL) {
				nowData.pt2 = e.getPoint();

				repaint();
			}// if oval
			else if(nowData.nDrawMode== Constants.FREE) {
				 nowData.vStart.add(e.getPoint()); // �����϶����� �������� arrayList�� ��ǥ����
				 
			     repaint();
			}// if free

		}//mouseDragged

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
} // DrawContrller Class
  