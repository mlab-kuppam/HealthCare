import MySQLdb
from Tkinter import *
from err import eror
def connect(check):
	try:
		conn = MySQLdb.connect("127.0.0.1","root","","healthcaredb" )
		print("SUCCESSFULLY CONNECTED TO DB")
	except(Exception):
		eror("Something Went Wrong!\nCheck ur Connection")
		exit(1)
		
	curr = conn.cursor()

	#****************Write Queries Here***************************
	if(check==1):
		q1 = '''Select * from school'''

	#*************************************************************
	if(check==1):
		curr.execute(q1)
		rows = curr.fetchall()
		ls=[]
		for i in rows:
			ls.append(i[4])
		return ls
