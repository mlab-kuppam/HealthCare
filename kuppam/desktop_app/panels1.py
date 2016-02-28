from Tkinter import *
from panels2 import *
from PIL import Image
import ImageTk
from visualizations import *
from internal import *

def makepanel1():
	root = Tk()
	#root.attributes('-fullscreen', True)
	w, h = root.winfo_screenwidth(), root.winfo_screenheight()
	root.geometry("%dx%d+0+0" % (w, h))
	root.title("DATA ANALYZER")
	fnt = ("ANDY",16,"bold")
	fnt1 = ("Helvetica",14,"bold")
	fnt2 = ("Helvetica",14,"bold italic")
	fnt3 = ("ANDY",24,"bold italic")


	#*************************EVENTS********************
	def fun():
		print(Lb1.curselection())

	def make():
		makepanel2()	

	def event1():
		Bars3d.showfig1()

	def event2():
		Piescatter.showfig2()

	def event3():
		Scatter3d.showfig3()

	def event4():
		Mixture.showfig4()

	def query(par,Lb1):
		ls = dbcon.connect(par)
		Lb1.delete(0,len(ls))
		for i in range(0,len(ls)):
			Lb1.insert(i+1,ls[i])
	#***************************************************


	#*****************************************************WIDGETS***************************************************************************
	'''Define either @time of defn or 
	using configure or['property'].'''
	#Each widget has a parent
	#To forecefully call event use widget.invoke()
	#(#BA55D3)

	#*******************************PANEL1*************************************************
	fp1 = Frame(root,bg='#6495ED',height='800',width='340'
		,relief=GROOVE,bd="3px",highlightcolor='#1E90FF')
	head1 = Label(root,bg="#6495ED",fg = "white",text="Score Classification\n(Bar Representation)",font=fnt2)
	top1 = Label(root,bg="#6495ED",fg = "white",text="School Selection: ",font=fnt)
	flb1 = Frame(root)
	scb1 = Scrollbar(flb1,activebackground="#2F4F4F")
	Lb1 = Listbox(flb1,selectmode = "multiple",yscrollcommand=scb1.set,
	bd="1px",font="fnt",height="15",highlightcolor='#191970',
		highlightthickness="2px",selectbackground="#6495ED")

	bv1 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="VISUALIZE",command=event1,font=fnt1)
	br1 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="REFRESH",command=lambda: query(1,Lb1),font=fnt1)
	#***************************************************************************************

	#*******************************PANEL2*************************************************
	fp2 = Frame(root,bg='#6A5ACD',height='800',width='340'
		,relief=GROOVE,bd="3px",highlightcolor='#1E90FF')
	head2 = Label(root,bg="#6A5ACD",fg = "white",text="Parameter VS Score\n(Bubble-Pie Scatterplot)",font=fnt2)
	top2 = Label(root,bg="#6A5ACD",fg = "white",text="Parameter Selection: ",font=fnt)
	flb2 = Frame(root)
	scb2 = Scrollbar(flb2,activebackground="#2F4F4F")
	Lb2 = Listbox(flb2,selectmode = "multiple",yscrollcommand=scb2.set,
		bd="1px",font="fnt",height="15",highlightcolor='#191970',
		highlightthickness="2px",selectbackground="#6A5ACD")

	bv2 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="VISUALIZE",command=event2,font=fnt1)
	br2 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="REFRESH",command=lambda: query(2),font=fnt1)
	#***************************************************************************************

	#*******************************PANEL3*************************************************
	fp3 = Frame(root,bg='#FF8C00',height='800',width='340'
		,relief=GROOVE,bd="3px",highlightcolor='#1E90FF')
	head3 = Label(root,bg="#FF8C00",fg = "white",text="Cumm-Age VS Condition\n(Scatter Plot)",font=fnt2)
	top3 = Label(root,bg="#FF8C00",fg = "white",text="Condition Selection: ",font=fnt)
	flb3 = Frame(root)
	scb3 = Scrollbar(flb3,activebackground="#2F4F4F")
	Lb3 = Listbox(flb3,selectmode = "multiple",yscrollcommand=scb3.set,
		bd="1px",font="fnt",height="15",highlightcolor='#191970',
		highlightthickness="2px",selectbackground="#FF8C00")

	bv3 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="VISUALIZE",command=event3,font=fnt1)
	br3 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="REFRESH",command=lambda: query(3),font=fnt1)
	#***************************************************************************************

	#*******************************PANEL4*************************************************
	fp4 = Frame(root,bg='#5F9EA0',height='800',width='310'
		,relief=GROOVE,bd="3px",highlightcolor='#1E90FF')
	head4 = Label(root,bg="#5F9EA0",fg = "white",text="Multi-Parameter Dependence\n(Multi-Relation Graph)",font=fnt2)
	top4 = Label(root,bg="#5F9EA0",fg = "white",text="Trait(3) Selection: ",font=fnt)
	flb4 = Frame(root)
	scb4 = Scrollbar(flb4,activebackground="#2F4F4F")
	Lb4 = Listbox(flb4,selectmode = "multiple",yscrollcommand=scb4.set,
		bd="1px",font="fnt",height="15",highlightcolor='#191970',
		highlightthickness="2px",selectbackground="#5F9EA0")

	bv4 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="VISUALIZE",command=event4,font=fnt1)
	br4 = Button(root,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
		activeforeground="#000000",bg="#FFFFFF",bd=0,text="REFRESH",command=lambda: query(4),font=fnt1)
	#***************************************************************************************

	bv = Button(root,bd="2px",fg="#696969",relief=RAISED,activebackground="#D3D3D3",width=3,height=800,
		activeforeground="#000000",bg="#F0FFF0",text="NEXT",command=make,font=fnt3,wraplength=1,justify=LEFT)

	#****************************************************************************************************************************************

	#****************FILLINGS**************************
	scb1.config(command=Lb1.yview)
	scb2.config(command=Lb2.yview)
	scb3.config(command=Lb3.yview)
	scb4.config(command=Lb4.yview)
	#************************************************ 


	#****************PACK-HERE***********************
	bv1.place(x=74,y=600)
	br1.place(x=78,y=550)
	scb1.pack(side=RIGHT ,fill=Y)
	Lb1.pack()
	head1.place(x=10,y=10)
	top1.place(x=20,y=160)
	fp1.place(x=0,y=0)
	flb1.place(x=30,y=200)

	fp2.place(x=300,y=0)
	flb2.place(x=340,y=200)
	bv2.place(x=389,y=600)
	br2.place(x=393,y=550)
	scb2.pack(side=RIGHT ,fill=Y)
	Lb2.pack()
	head2.place(x=320,y=10)
	top2.place(x=330,y=160)

	fp3.place(x=600,y=0)
	flb3.place(x=640,y=200)
	bv3.place(x=689,y=600)
	br3.place(x=693,y=550)
	scb3.pack(side=RIGHT ,fill=Y)
	Lb3.pack()
	head3.place(x=620,y=10)
	top3.place(x=630,y=160)

	fp4.place(x=900,y=0)
	flb4.place(x=940,y=200)
	bv4.place(x=989,y=600)
	br4.place(x=993,y=550)
	scb4.pack(side=RIGHT ,fill=Y)
	Lb4.pack()
	head4.place(x=920,y=10)
	top4.place(x=930,y=160)

	bv.pack(side=RIGHT)

	#************************************************
	mainloop()