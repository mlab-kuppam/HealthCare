from mpl_toolkits.mplot3d import axes3d
import matplotlib.pyplot as plt
from matplotlib import cm
import numpy as np

def randrange(n, vmin, vmax):
    return (vmax - vmin)*np.random.rand(n) + vmin

def showfig4():
	fig = plt.figure(figsize=(6*3.13,4*3.13))
	#ax = fig.add_subplot(111, projection='3d')
	ax = fig.gca(projection='3d')
	X, Y, Z = axes3d.get_test_data(0.05)
	ax.plot_surface(X, Y, Z, rstride=8, cstride=8, alpha=0.3)
	cset = ax.contour(X, Y, Z, zdir='z', offset=-100, cmap=cm.coolwarm)
	cset = ax.contour(X, Y, Z, zdir='x', offset=-40, cmap=cm.coolwarm)
	cset = ax.contour(X, Y, Z, zdir='y', offset=40, cmap=cm.coolwarm)

	ax.set_xlabel('Blood Pressure')
	ax.set_xlim(-40, 40)
	ax.set_ylabel('Diabetes')
	ax.set_ylim(-40, 40)
	ax.set_zlabel('Cholestrol')
	ax.set_zlim(-100, 100)
	fig.canvas.set_window_title("Health Analyzer")
	fig.patch.set_facecolor('white')
	plt.show()
