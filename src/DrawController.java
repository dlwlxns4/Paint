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
			view.setTxtSize(view.getTxtSize()); // 아니면 이전에 세
			else {
				view.setTxtSize(5); // 0이면 1을 해주고 초기 txt사이즈 5
				view.savedTxtSize = view.getTxtSize();
			}
		}
	} // 그림 메뉴 다른 것 클릭 했을 때 그전 txtSize유지
	
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
			view.setDrawMode("DOT"); // 현재 드로우모드 표시
			view.setDrawColor("COLOR", nowData.selectedColor); // 현재 색깔 표시
			view.setDrawSize(view.savedTxtSize); //현재 글씨 크기 표시
			view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y); // 현재 좌표 표시
		}
		
		if(bDrag) {
			//now data
			switch(nowData.nDrawMode){
			
				case Constants.LINE:
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					page.drawLine(nowData.pt1.x, nowData.pt1.y, 
							nowData.pt2.x, nowData.pt2.y);
					view.setDrawMode("LINE"); // 현재 드로우모드 표시
					view.setDrawColor("COLOR", nowData.selectedColor); // 현재 색깔 표시
					view.setDrawSize(view.savedTxtSize); //현재 글씨 크기 표시
					view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y, nowData.pt2.x, nowData.pt2.y); // 현재 좌표 표시
					break;
				case Constants.RECT:
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					draw4Rect(page, nowData.pt1, nowData.pt2, nowData.bFill);
					view.setDrawMode("RECT"); // 현재 드로우모드 표시
					view.setDrawColor("COLOR", nowData.selectedColor); // 현재 색깔 표시
					view.setDrawSize(view.savedTxtSize); //현재 글씨 크기 표시
					view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y, nowData.pt2.x, nowData.pt2.y); // 현재 좌표 표시
					break;
				case Constants.OVAL:
					page.setColor(nowData.selectedColor);
					page2.setStroke(new BasicStroke(nowData.nSize));
					drawOvalPaint(page, nowData.pt1, nowData.pt2, nowData.bFill);
					view.setDrawMode("OVAL"); // 현재 드로우 모드 표시
					view.setDrawColor("COLOR", nowData.selectedColor); // 현재 색깔 표시
					view.setDrawSize(view.savedTxtSize); //현재 글씨 크기 표시
					view.setDrawPointDot(nowData.pt1.x, nowData.pt1.y, nowData.pt2.x, nowData.pt2.y); // 현재 좌표 표시
					break;
				case Constants.FREE:
						if(nowData.vStart.size() >1 ) { // vStart자유곡선 arrayList가 있는지 확인
						
						page.setColor(nowData.selectedColor);
						page2.setStroke(new BasicStroke(nowData.nSize));
						  for (int i = 1; i < nowData.vStart.size(); i++) { // 자유곡선 ArrayList의 사이즈까지 그림그리기 위해서
							  if (nowData.vStart.get(i - 1) == null)
								  continue;
							  else if (nowData.vStart.get(i) == null)
								  continue;
							  else
								  page.drawLine((int) nowData.vStart.get(i - 1).getX(), (int) nowData.vStart.get(i - 1).getY(), 
										  (int) nowData.vStart.get(i).getX(), (int) nowData.vStart.get(i).getY()); // 선긋기 
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
					if(data.vStart.size()>1) { // ArrayList 가 들어있는지 확인하기 위해서
						page.setColor(data.selectedColor);
						page2.setStroke(new BasicStroke(data.nSize));
						  for (int i = 1; i < data.vStart.size(); i++) { // 사이즈크기만큼 선긋기
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
	}//draw4Rect 사각형그리기
	
	
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
	}//drawOvalPaint 원 그리기
	
	public void resetPaint() {
		savedList.clear();
		repaint();
	} //Clear 버튼 클릭시 리셋 함수
	public void paintUndo() {
		
		
		
		if(savedList.size() !=0) {
			
			savedList.remove(savedList.size()-1);
		
		repaint();
		}
	} //이전에 그린 그림 지우기 함수
	 
	
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
				nowData.vStart.add(e.getPoint()); // free전용 arrayList에 좌표대입
				nowData.vStart.add(null); // 실수 최소화하려구
				savedList.add(new SimplePainterModel(nowData)); // savedList에 저장하기
				System.out.println(nowData.vStart.size()); // 이건 그냥 검토용
				nowData.vStart.removeAllElements(); // 저장했으니 요소값 다시 초기화
				
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
				 nowData.vStart.add(e.getPoint()); // 움직일때마다 프리전용 arrayList에 좌표대입
				 
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
  