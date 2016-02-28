from Tkinter import *
str1 =""
def eror(msg):
	top = Tk()
	top.title("Alert")
	top.geometry("400x200+500+200")
	top1 = Label(top,bg="#6495ED",fg = "white",
		text=msg,font=("ANDY",20,"bold italic"))
	top1.pack()
	mainloop()

def send(top,val):
	global str1
	str1=val.get()
	top.destroy()
	if(str1 != None):
		eror("Report Correctly \nGenerated For\n :-%s"%str1)


def entr(msg):
	top = Tk()
	top.title("Report Generation")
	top.geometry("400x300+500+150")
	top1 = Label(top,bg="#6495ED",fg = "white",
		text=msg,font=("ANDY",20,"bold italic"))						
	E1 = Entry(top, bd =2,cursor="arrow",font=("ANDY",14,"bold italic"),
		fg="#5F9EA0",justify=CENTER,relief=RAISED,selectforeground="#191970")

	bv2 = Button(top,fg="#696969",relief=RIDGE,activebackground="#D3D3D3",
    activeforeground="#000000",bg="#FFFFFF",bd=0,text="Confirm",font=("ANDY",20,),command=lambda:send(top,E1))

	top1.place(x=45,y=20)
	E1.place(x=70,y=160)
	bv2.place(x=145,y=230)
	mainloop()
	