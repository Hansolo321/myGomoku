package myGomoku;
/*
 * Author: Han Liao (lhan@iastate.edu or leslieileo@gmail.com)
 * This is the project for creative component in ISU
 */
import java.util.ArrayList;


public class Back_End {
	public ArrayList<Moves> five=new ArrayList<Moves>();
	private int inarow=5;
	public int[] scaled =new int[4];
	private int scalednum=2;


	public int[] stoneposition(int mouseX, int mouseY) {
		double close=100;
		double distance=0;
		int[] answer= new int[2];
		int r,c;
		for( c=mouseY-23; c<mouseY+23; c++) {
			for( r=mouseX-23; r<mouseX+23; r++) {
				if( ((r-1-20)%39==0) && ((c-1-20)%39==0) ) {
					distance = Math.sqrt(((r-mouseX)^2) + ((c-mouseY)^2));
					if(distance<close) {
						answer[0]=r;
						answer[1]=c;
						close=distance;
					}
				}
			}
		}
		return answer;
	}

	public  int[] AI1(char[][] board, int key, ArrayList<Moves> movinglist) {
		int[] result=new int[] {0,0};

		if(key<=225&&key%2==0) {
			int i=scaled[0];
			int j=scaled[1];
			int[] Black;
			int value=-100000;
			Moves candidate;
			Moves blackcand;
			for( i=scaled[0];i<=scaled[2];i++) {
				for( j=scaled[1];j<=scaled[3];j++) {
					if(board[i][j]!='B'&&board[i][j]!='W') {
						candidate = new Moves(i,j);
						movinglist.add(candidate);
						board[i][j]='W';
						int evaluresultB= black_evaluator(board, movinglist).getBoardEval();
						int evaluresultW= white_evaluator(board, movinglist).getBoardEval();
						if(evaluresultW-evaluresultB>value) {
							value=evaluresultW-evaluresultB;
							result[0]=candidate.getX();
							result[1]=candidate.getY();
							Black=AI2(board, key+1, movinglist);
							blackcand=new Moves(Black[0],Black[1]);
							board[Black[0]][Black[1]]='B';
							movinglist.add(blackcand);
							if( black_evaluator(board, movinglist).getBoardEval()>evaluresultW) {
								result[0]=blackcand.getX();
								result[1]=blackcand.getY();
								board[Black[0]][Black[1]]='-';
								board[i][j]='-';
								movinglist.remove(movinglist.size()-1);
								movinglist.remove(movinglist.size()-1);
								return result;
							}
							board[Black[0]][Black[1]]='-';
							movinglist.remove(movinglist.size()-1);
						}
						board[i][j]='-';
						movinglist.remove(movinglist.size()-1);}
				}
			}
		}
		if(key<=225&&key%2!=0) {
			int i=scaled[0];
			int j=scaled[1];
			int[] White;
			int value=-100000;
			Moves candidate;
			Moves whitecand;
			for( i=scaled[0];i<=scaled[2];i++) {
				for( j=scaled[1];j<=scaled[3];j++) {
					if(board[i][j]!='B'&&board[i][j]!='W') {
						candidate = new Moves(i,j);
						movinglist.add(candidate);
						board[i][j]='B';
						int evaluresultB= black_evaluator(board, movinglist).getBoardEval();
						int evaluresultW= white_evaluator(board, movinglist).getBoardEval();
						if(evaluresultB-evaluresultW>value) {
							value=evaluresultB-evaluresultW;
							result[0]=candidate.getX();
							result[1]=candidate.getY();
							White=AI2(board, key+1, movinglist);
							whitecand=new Moves(White[0],White[1]);
							board[White[0]][White[1]]='W';
							movinglist.add(whitecand);
							if( white_evaluator(board, movinglist).getBoardEval()>evaluresultB) {
								result[0]=whitecand.getX();
								result[1]=whitecand.getY();
								board[White[0]][White[1]]='-';
								board[i][j]='-';
								movinglist.remove(movinglist.size()-1);
								movinglist.remove(movinglist.size()-1);
								return result;
							}
							board[White[0]][White[1]]='-';
							movinglist.remove(movinglist.size()-1);
						}
						board[i][j]='-';
						movinglist.remove(movinglist.size()-1);}
				}
			}
		}
		return result;
	}

	public  int[] AI2(char[][] board, int key, ArrayList<Moves> movinglist) {
		int[] result=new int[] {0,0};

		if(key<=225&&key%2==0) {
			int i=scaled[0];
			int j=scaled[1];
			int value=-100000;
			Moves candidate;
			for( i=scaled[0];i<=scaled[2];i++) {
				for( j=scaled[1];j<=scaled[3];j++) {
					if(board[i][j]!='B'&&board[i][j]!='W') {
						candidate = new Moves(i,j);
						movinglist.add(candidate);
						board[i][j]='W';
						int evaluresultB= black_evaluator(board, movinglist).getBoardEval();
						int evaluresultW= white_evaluator(board, movinglist).getBoardEval();
						if(evaluresultW-evaluresultB>value) {
							value=evaluresultW-evaluresultB;
							result[0]=candidate.getX();
							result[1]=candidate.getY();
						}
						board[i][j]='-';
						movinglist.remove(movinglist.size()-1);}
				}
			}
		}
		if(key<=225&&key%2!=0) {
			int i=scaled[0];
			int j=scaled[1];
			int value=-100000;
			Moves candidate;
			for( i=scaled[0];i<=scaled[2];i++) {
				for( j=scaled[1];j<=scaled[3];j++) {
					if(board[i][j]!='B'&&board[i][j]!='W') {
						candidate = new Moves(i,j);
						movinglist.add(candidate);
						board[i][j]='B';
						int evaluresultB= black_evaluator(board, movinglist).getBoardEval();
						int evaluresultW= white_evaluator(board, movinglist).getBoardEval();
						if(evaluresultB-evaluresultW>value) {
							value=evaluresultB-evaluresultW;
							result[0]=candidate.getX();
							result[1]=candidate.getY();
						}
						board[i][j]='-';
						movinglist.remove(movinglist.size()-1);}
				}
			}
		}
		return result;
	}

	public char boardChecker(char[][] board) {
		five.clear();
		for(int x=0;x<15;x++) {
			for(int y=0;y<15;y++) {
				char stone=board[x][y];
				if(stone!='-') {
					int count3=0;
					for(int i=Math.max(Math.min(x, x-4),0);i<=Math.min(Math.max(x, x+4),14);i++) {
						if(board[i][y]==stone) {
							count3++;
							five.add(new Moves(i,y));
							if(count3==inarow) {
								return stone;}
						}
						else {count3=0;five.clear();}
					}
					five.clear();

					int count4=0;
					for(int i=Math.max(Math.min(y, y-4),0);i<=Math.min(Math.max(y, y+4),14);i++) {
						if(board[x][i]==stone) {
							count4++;
							five.add(new Moves(x,i));
							if(count4==inarow) {	
								return stone;}
						}
						else {count4=0;five.clear();}
					}
					five.clear();

					int add=1;
					int count1=0,count2=0;
					boolean end1=false;
					boolean end2=false;
					while(add<5) {
						five.add(new Moves(x,y));
						if(0<=x+add && 14>=x+add && 0<=y+add && 14>=y+add) {
							if(board[x+add][y+add]==stone&&!end1) {
								count1++;
								five.add(new Moves(x+add,y+add));
							}
							else {
								end1=true;
							}
						}
						if(0<=x-add && 14>=x-add && 0<=y-add && 14>=y-add) {
							if(board[x-add][y-add]==stone&&!end2) {
								count2++;
								five.add(new Moves(x-add,y-add));
							}
							else {
								end2=true;
							}
						}
						add++;
						if(count1+count2==inarow-1) {
							return stone;
						}	
					}
					five.clear();
					add=1;
					count1=0;
					count2=0;
					end1=false;
					end2=false;
					while(add<5) {
						five.add(new Moves(x,y));
						if(0<=x+add && 14>=x+add && 0<=y-add && 14>=y-add) {
							if(board[x+add][y-add]==stone&&!end1) {
								count1++;
								five.add(new Moves(x+add,y-add));
							}
							else {
								end1=true;
							}

						}
						if(0<=x-add && 14>=x-add && 0<=y+add && 14>=y+add) {
							if(board[x-add][y+add]==stone&&!end2) {
								count2++;
								five.add(new Moves(x-add,y+add));
							}
							else {
								end2=true;
							}
						}
						add++;
						if(count1+count2==inarow-1) {
							return stone;
						}
					}
					five.clear();
				}
			}
		}
		five.clear();
		return '-';
	}

	public int[] scale(ArrayList<Moves> movinglist) {
		scaled =new int[4];
		int minX=15,minY=15,maxX=0,maxY=0;
		if(movinglist.size()==0) {
			return null;
		}
		for(int i=0;i<movinglist.size();i++) {
			if(movinglist.get(i).getX()>maxX) {
				maxX=movinglist.get(i).getX();
			}
			if(movinglist.get(i).getY()>maxY) {
				maxY=movinglist.get(i).getY();
			}
			if(movinglist.get(i).getX()<minX) {
				minX=movinglist.get(i).getX();
			}
			if(movinglist.get(i).getY()<minY) {
				minY=movinglist.get(i).getY();
			}
		}
		minX-=scalednum;minY-=scalednum;maxX+=scalednum;maxY+=scalednum;
		minX=Math.max(minX, 0);minY=Math.max(minY, 0);
		maxX=Math.min(maxX, 14);maxY=Math.min(maxY, 14);
		scaled[0]=minX;scaled[1]=minY;scaled[2]=maxX;scaled[3]=maxY;
		return scaled;
	}

	public BoardState white_evaluator(char[][] board, ArrayList<Moves> movinglist) {
		BoardState bs= new BoardState(board);
		ArrayList<Moves> checked = new ArrayList<Moves>();
		ArrayList<Moves> moves=new ArrayList<Moves>();
		if(movinglist.size()<2) {
			return bs;
		}
		int index=1;
		int count=0;
		boolean block1=false;
		boolean block2=false;
		int cut1=0;
		int cut2=0;
		int cut3=0;//2row
		int cut4=0;//2row

		int i,j;
		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//Upper
			i=x;
			j=Math.max(0, y-1);
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(j>=Math.max(0, y-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				j--;
			}
			//Bottom
			i=x;
			j=Math.min(14, y+1);
			while(j<=Math.min(14, y+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				j++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}


		//////////////////////////////////
		if(movinglist.size()<2) {
			return bs;
		}
		index=1;
		count=0;
		block1=false;
		block2=false;
		cut1=0;
		cut2=0;
		cut3=0;//2row
		cut4=0;//2row
		checked.clear();
		checked = new ArrayList<Moves>();

		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//Left
			i=Math.max(0, x-1);
			j=Math.max(0, y-1);
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(i>=Math.max(0, x-4)&&j>=Math.max(0, y-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				i--;
				j--;
			}
			//Right
			i=Math.min(14, x+1);
			j=Math.min(14, y+1);
			while(i<=Math.min(14, x+4)&&j<=Math.min(14, y+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				i++;
				j++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}



		//////////////////////////////////
		if(movinglist.size()<2) {
			return bs;
		}
		index=1;
		count=0;
		block1=false;
		block2=false;
		cut1=0;
		cut2=0;
		cut3=0;//2row
		cut4=0;//2row
		checked.clear();
		checked = new ArrayList<Moves>();

		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//UpperLeft
			i=Math.max(0, x-1);
			j=y;
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(i>=Math.max(0, x-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				i--;
			}
			//BottomRight
			i=Math.min(14, x+1);
			j=y;
			while(i<=Math.min(14, x+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				i++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}

		//////////////////////////////////
		if(movinglist.size()<2) {
			return bs;
		}
		index=1;
		count=0;
		block1=false;
		block2=false;
		cut1=0;
		cut2=0;
		cut3=0;//2row
		cut4=0;//2row
		checked.clear();
		checked = new ArrayList<Moves>();

		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//UpperLeft
			i=Math.min(14, x+1);
			j=Math.max(0, y-1);
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(i<=Math.min(14, x+4)&&j>=Math.max(0, y-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				i++;
				j--;
			}
			//BottomRight
			i=Math.max(0, x-1);
			j=Math.min(14, y+1);
			while(i>=Math.max(0, x-4)&&j<=Math.min(14, y+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='W') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				i--;
				j++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}

		return bs;
	}

	public BoardState black_evaluator(char[][] board, ArrayList<Moves> movinglist) {
		BoardState bs= new BoardState(board);
		ArrayList<Moves> checked = new ArrayList<Moves>();
		ArrayList<Moves> moves=new ArrayList<Moves>();
		if(movinglist.size()<1) {
			return bs;
		}
		int index=0;
		int count=0;
		boolean block1=false;
		boolean block2=false;
		int cut1=0;
		int cut2=0;
		int cut3=0;//2row
		int cut4=0;//2row

		int i,j;
		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//Upper
			i=x;
			j=Math.max(0, y-1);
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(j>=Math.max(0, y-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				j--;
			}
			//Bottom
			i=x;
			j=Math.min(14, y+1);
			while(j<=Math.min(14, y+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				j++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}


		//////////////////////////////////
		if(movinglist.size()<1) {
			return bs;
		}
		index=0;
		count=0;
		block1=false;
		block2=false;
		cut1=0;
		cut2=0;
		cut3=0;//2row
		cut4=0;//2row
		checked.clear();
		checked = new ArrayList<Moves>();

		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//Left
			i=Math.max(0, x-1);
			j=Math.max(0, y-1);
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(i>=Math.max(0, x-4)&&j>=Math.max(0, y-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				i--;
				j--;
			}
			//Right
			i=Math.min(14, x+1);
			j=Math.min(14, y+1);
			while(i<=Math.min(14, x+4)&&j<=Math.min(14, y+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				i++;
				j++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}



		//////////////////////////////////
		if(movinglist.size()<1) {
			return bs;
		}
		index=0;
		count=0;
		block1=false;
		block2=false;
		cut1=0;
		cut2=0;
		cut3=0;//2row
		cut4=0;//2row
		checked.clear();
		checked = new ArrayList<Moves>();

		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//UpperLeft
			i=Math.max(0, x-1);
			j=y;
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(i>=Math.max(0, x-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				i--;
			}
			//BottomRight
			i=Math.min(14, x+1);
			j=y;
			while(i<=Math.min(14, x+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				i++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}

		//////////////////////////////////
		if(movinglist.size()<1) {
			return bs;
		}
		index=0;
		count=0;
		block1=false;
		block2=false;
		cut1=0;
		cut2=0;
		cut3=0;//2row
		cut4=0;//2row
		checked.clear();
		checked = new ArrayList<Moves>();

		while(index<=movinglist.size()) {
			moves=new ArrayList<Moves>();
			checked.add(movinglist.get(index));
			moves.add(movinglist.get(index));
			count=1;
			int x=movinglist.get(index).getX();
			int y=movinglist.get(index).getY();
			//UpperLeft
			i=Math.min(14, x+1);
			j=Math.max(0, y-1);
			block1=false;
			block2=false;
			cut1=0;
			cut2=0;
			cut3=0;
			cut4=0;
			boolean isFiveinrow=true;
			boolean exist=false;
			while(i<=Math.min(14, x+4)&&j>=Math.max(0, y-4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut1==1) {
						isFiveinrow=false;
						cut3++;
					}
					if(cut1==2) {
						isFiveinrow=false;
						cut3+=2;
					}
					if(cut1==3) {
						isFiveinrow=false;
						cut3+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut1++;
				}
				else {
					block1=true;
					break;
				}
				i++;
				j--;
			}
			//BottomRight
			i=Math.max(0, x-1);
			j=Math.min(14, y+1);
			while(i>=Math.max(0, x-4)&&j<=Math.min(14, y+4)) {
				exist=false;
				for(int a=0;a<checked.size();a++) {
					if(checked.get(a).getX()==i&&checked.get(a).getY()==j) {
						exist=true;
					}
				}
				if(exist) {break;}
				if(board[i][j]=='B') {
					count++;
					moves.add(new Moves(i,j));
					if(cut2==1) {
						isFiveinrow=false;
						cut4++;
					}
					if(cut2==2) {
						isFiveinrow=false;
						cut4+=2;
					}
					if(cut2==3) {
						isFiveinrow=false;
						cut4+=3;
					}
				}
				else if(board[i][j]=='-'){
					cut2++;
				}
				else {
					block2=true;
					break;
				}
				i--;
				j++;
			}
			//Five in a row
			if(count==5&&isFiveinrow==true) {bs.FiveInrow().add(moves);}
			//Live two
			if(count==2&&cut1>=2&&cut4==0&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==1&&cut3==0&&cut2>=2){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=2&&cut4==0&&cut3==1&&cut2>=1){bs.Livetwo().add(moves);}

			if(count==2&&cut1>=1&&cut4==2&&cut3==0&&cut2>=3){bs.Livetwo().add(moves);}
			if(count==2&&cut1>=3&&cut4==0&&cut3==2&&cut2>=1){bs.Livetwo().add(moves);}
			//Dead two
			if(count==2&&cut1>=3&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=2&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==1&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==1&&cut2>=2&&cut3==1&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==2&&cut3==0&&cut4==2&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2>=3&&cut3==0&&cut4==2&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1>=3&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==2&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadtwo().add(moves);}

			if(count==2&&cut1>=1&&cut2==3&&cut3==0&&cut4==3){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2>=1&&cut3==3&&cut4==0){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==0&&cut2==3&&cut3==0&&cut4==3&&block1==true){bs.Deadtwo().add(moves);}
			if(count==2&&cut1==3&&cut2==0&&cut3==3&&cut4==0&&block2==true){bs.Deadtwo().add(moves);}
			//Live three bug
			if(count==3&&cut1==1&&cut2>=1&&cut3==0&&cut4==0&&block1==true&&block2==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==0&&block2==true&&block1==false){bs.Livethree().add(moves);}	
			if(count==3&&cut1>=2&&cut2>=2&&cut3==0&&cut4==0){bs.Livethree().add(moves);}	


			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==2){bs.Livethree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			//if(count==3&&cut1==2&&cut2>=1&&cut3==1&&cut4==0){bs.Livethree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==1){bs.Livethree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Livethree().add(moves);}

			//Dead three
			if(count==3&&cut1>=2&&cut2==0&&cut3==0&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==2&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==1&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==1&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==2&&cut4==0&&block1==true){bs.Deadthree().add(moves);}

			if(count==3&&cut1==0&&cut2>=2&&cut3==0&&cut4==2&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1==1&&cut2>=1&&cut3==1&&cut4==0&&block1==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2==0&&cut3==2&&cut4==0&&block2==true){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2==1&&cut3==0&&cut4==1&&block2==true){bs.Deadthree().add(moves);}

			//bug
			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==4){bs.Deadthree().add(moves);}
			if(count==3&&cut1==3&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==2&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==4&&cut4==0){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=1&&cut2>=2&&cut3==0&&cut4==2){bs.Deadthree().add(moves);}

			if(count==3&&cut1>=1&&cut2==2&&cut3==0&&cut4==3){bs.Deadthree().add(moves);}
			if(count==3&&cut1>=2&&cut2>=2&&cut3==1&&cut4==1){bs.Deadthree().add(moves);}
			if(count==3&&cut1==2&&cut2>=1&&cut3==3&&cut4==0){bs.Deadthree().add(moves);}

			if(count==3&&cut1==1&&cut2==1&&cut3==0&&cut4==0&&block1==true&&block2==true){bs.Deadthree().add(moves);}
			//Live Four
			if(count==4&&cut1>=1&&cut2>=1&&cut3==0&&cut4==0){bs.Livefour().add(moves);}
			//Dead Four
			if(count==4&&cut1>=1&&cut2==0&&block2==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1==0&&cut2>=1&&block1==true&&cut3==0&&cut4==0){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2==1&&cut3==0&&cut4==3){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=1&&cut2>=0&&cut3==3&&cut4==0){bs.Deadfour().add(moves);}
			//	if(count==4&&cut1>=0&&cut2>=1&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==1){bs.Deadfour().add(moves);}

			if(count==4&&cut1>=0&&cut2>=0&&cut3==2&&cut4==0){bs.Deadfour().add(moves);}
			if(count==4&&cut1>=0&&cut2>=0&&cut3==0&&cut4==2){bs.Deadfour().add(moves);}


			if(index+2>=movinglist.size()) {break;}
			else{index=index+2;}
		}

		return bs;

	}
}
