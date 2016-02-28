from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np

def randrange(n, vmin, vmax):
    return (vmax - vmin)*np.random.rand(n) + vmin

def showfig3():
	fig = plt.figure(figsize=(6*3.13,4*3.13))
	ax = fig.add_subplot(111, projection='3d')
	#*****************************ABOVE-COMMON*************************************
	n = 100
	for c, m, zl, zh in [('r', 'o', -50, -25), ('b', '^', -30, -5)]:
		xs = randrange(n, 23, 32)
		ys = randrange(n, 0, 100)
		zs = randrange(n, zl, zh)
		ax.scatter(xs, ys, zs, c=c, marker=m)

	ax.set_xlabel('Conditions(X)')
	ax.set_ylabel('Cumm-Age Group()')
	ax.set_zlabel('Pos/Neg')
	ax.set_zticks([])
	fig.canvas.set_window_title("Health Analyzer")
	fig.patch.set_facecolor('white')
	plt.show()



