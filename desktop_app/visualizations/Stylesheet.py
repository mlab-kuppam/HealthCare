"""
This example demonstrates the "bmh" style, which is the design used in the
Bayesian Methods for Hackers online book.
"""
from numpy.random import beta
import matplotlib.pyplot as plt

plt.style.use('bmh')


def plot_beta_hist(a, b):
    plt.hist(beta(a, b, size=10000), histtype="stepfilled",
             bins=25, alpha=0.8, normed=True)
    return

def showfig8():
	fig = plt.figure(figsize=(6*3.13,4*3.13))
	ax = fig.add_subplot(111)
	ax.set_ylabel('Affected %')
	ax.set_xlabel('Density Ratio')	
	fig.canvas.set_window_title("Health Analyzer")
	fig.patch.set_facecolor('white')
	plot_beta_hist(10, 10)
	plot_beta_hist(4, 12)
	plot_beta_hist(50, 12)
	plot_beta_hist(6, 55)

	plt.show()
