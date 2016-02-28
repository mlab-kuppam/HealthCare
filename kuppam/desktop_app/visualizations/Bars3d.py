from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
def showfig1():
	fig = plt.figure(figsize=(6*3.13,4*3.13))
	ax = fig.add_subplot(111, projection='3d')
	fig.canvas.set_window_title("Health Analyzer")
	fig.patch.set_facecolor('white')
	#*****************************ABOVE-COMMON*************************************
	for c, z in zip(['g','r','b','y'], [30, 20, 10, 0]):
	    xs = np.arange(20)
	    ys = np.random.rand(20)
	    # You can provide either a single color or an array. To demonstrate this,
	    # the first bar of each set will be colored cyan.
	    cs = [c] * len(xs)
	    cs[0] = 'c'
	    ax.bar(xs, ys, zs=z, zdir='y', color=cs, alpha=0.8)

	ax.set_xlabel('Time')
	ax.set_ylabel('Schools')
	ax.set_zlabel('Score')
	ax.set_yticks([])

	plt.show()
