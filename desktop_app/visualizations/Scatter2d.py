"""
Simple demo of a scatter plot.
"""
import numpy as np
import matplotlib.pyplot as plt

def showfig5():
	fig = plt.figure(figsize=(6*3.13,4*3.13))
	fig.canvas.set_window_title("Health Analyzer")
	fig.patch.set_facecolor('white')
	N = 50
	x = np.random.rand(N)
	y = np.random.rand(N)
	colors = np.random.rand(N)
	area = np.pi * (15 * np.random.rand(N))**2  # 0 to 15 point radiuses
	ax = fig.add_subplot(111)
	ax.set_xlabel('Param-1')
	ax.set_ylabel('Param-2')
	plt.scatter(x, y, s=area, c=colors, alpha=0.5)
	data=np.arange(9).reshape((3,3))
	plt.show()