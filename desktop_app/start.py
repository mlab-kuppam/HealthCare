import os,random
from Tkinter import *
from PIL import Image
import ImageTk
from panels1 import *
from internal import *
import threading
import webbrowser

i=0
root = Tk()
root.geometry('+%d+%d' % (0,0)) #controls where the window is
root.title("Healthcare Data Analyzer")
fnt1 = ("ANDY",28,"bold italic")
fnt2 = ("Helvetica",24,"bold italic")

def open_url(url):
    root.destroy()
    dbcon.connect(0)
    T = threading.Thread(target=makepanel1)
    T.start()

def open_map():
    #err.eror("Map Not Found !!")
    try:
        webbrowser.open('http://127.0.0.1/yo.php', new=2)
    except FileNotFoundError:
        err.eror("Map Not Found !!")

def report():
    err.entr("Enter Corresponding\n Student ID")

def summ_create():
    T = threading.Thread(target=summ_code.create)
    T.start()
    err.eror("Successfully created \nsummary for current\n Database-Status !!")
	
def changeImage():
    global root
    global tkpi 
    global i
    i=i+1
    if i == 11:
        i=1

    finalimg = "./Images/"+str(i)+".png"
    image = Image.open(finalimg)
    RWidth=root.winfo_screenwidth()
    RHeight=root.winfo_screenheight()
    #set size to show, in this case the whole picture
    root.geometry('%dx%d' % (RWidth,RHeight))

    #Creates a compatible photo image
    tkpi = ImageTk.PhotoImage(image)

    #Put image in a label and place it
    label_image = Label(root, image=tkpi,bg="#1E90FF",bd="3px",relief="raised")
    label_image.place(x=20,y=420,width=450,height=300)
    url=i
    label_image.bind("<Button-1>",lambda e,url=url:open_url(url))
    # call this function again in 1/2 a second
    root.after(2000, changeImage)



tkpi = None #create this global variable so that the image is not derefrenced

FILENAME = 'Images/back3.jpg'
backx = ImageTk.PhotoImage(file = FILENAME)
RWidth=root.winfo_screenwidth()
RHeight=root.winfo_screenheight()
background = Label(root,image=backx)
background.place(w=RWidth,h=RHeight,x=-50, y=0)

bv2 = Button(root,fg="#696969",command=report,relief=RIDGE,activebackground="#D3D3D3",
    activeforeground="#000000",bg="#FFFFFF",bd=0,text="REPORT",font=fnt1)

bv2.place(x=1040,y=650)

bv3 = Button(root,fg="#696969",command=open_map,relief=RIDGE,activebackground="#D3D3D3",
    activeforeground="#000000",bg="#FFFFFF",bd=0,text="Geospatial\nMap",font=fnt2)

bv3.place(x=600,y=630)

bv4 = Button(root,fg="#696969",command=summ_create,relief=RIDGE,activebackground="#D3D3D3",
    activeforeground="#000000",bg="#FFFFFF",bd=0,text="SUMMARY",font=fnt2)

bv4.place(x=830,y=655)

head = Image.open("./Images/"+"head2.png")
head1 = ImageTk.PhotoImage(head)
headi = Label(root,image=head1)
headi.place(w=300,h=150,x=50, y=22)


logo = Image.open("./Images/"+"logo.jpeg")
logo1 = ImageTk.PhotoImage(logo)
heading = Label(root,image=logo1)
heading.place(w=130,h=125,x=1150, y=22)

changeImage()
mainloop()	




