"""
Demo of scatter plot on a polar axis.

Size increases radially in this example and color increases with angle (just to
verify the symbols are being scattered correctly).
"""
import numpy as np
import matplotlib.pyplot as plt

def showfig2():
	fig = plt.figure(figsize=(6*3.13,4*3.13))	
	fig.canvas.set_window_title("Health Analyzer")
	fig.patch.set_facecolor('white')
	N = 20 #No Of Bubbles
	r = 2 * np.random.rand(N) #Less Than One so Max Radius = 2 and list as No of Bubbles = N
	theta = 2 * np.pi * np.random.rand(N)
	print(theta)
	area = 200 * r**2 * np.random.rand(N) #Area of bubbles Propotional to its distance from center
	colors = theta

	ax = plt.subplot(111, projection='polar')
	ax.set_xlabel('Score')
	ax.set_ylabel('Parameter') #First Param is Size,Establishes Empty Graph
	c = plt.scatter(theta, r, c=colors, s=area, cmap=plt.cm.hsv)
	c.set_alpha(10) #Brightness

	plt.show()
