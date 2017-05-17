import tensorflow as tf
from tensorflow.contrib.layers import fully_connected
import numpy as np
from numpy import genfromtxt
datax, datay = genfromtxt('C:/Users/mkupper/num.csv', delimiter=','), genfromtxt('C:/Users/mkupper/numy.csv', delimiter=',')
print(datax.size)
print(datay.size)

#n_steps=datax.size
n_steps=20
n_inputs=1
n_neurons=100
n_outputs=1

X = tf.placeholder(tf.float32, [None, n_steps, n_inputs])
y = tf.placeholder(tf.float32, [None, n_steps, n_outputs])

cell = tf.contrib.rnn.BasicRNNCell(num_units=n_neurons, activation=tf.nn.relu)
rnn_outputs, states = tf.nn.dynamic_rnn(cell, X, dtype=tf.float32)

#cell = tf.contrib.rnn.OutputProjectionWrapper(tf.contrib.rnn.BasicRNNCell(num_units=n_neurons, activation=tf.nn.relu), output_size=n_outputs)

stacked_rnn_outputs = tf.reshape(rnn_outputs, [-1, n_neurons])
stacked_outputs = fully_connected(stacked_rnn_outputs, n_outputs, activation_fn=None)
outputs=tf.reshape(stacked_outputs, [-1, n_steps, n_outputs])

learning_rate = 0.001

loss = tf.reduce_mean(tf.square(outputs - tf.cast(y, tf.float32)))
optimizer = tf.train.AdamOptimizer(learning_rate=learning_rate) 
training_op = optimizer.minimize(loss)

init = tf.global_variables_initializer()
n_iterations = 10
batch_size = 3
X_list = np.array_split(datax, datax.size//n_steps)
y_list = np.array_split(datay, datay.size//n_steps)

#X_arrays = np.asarray(X_list)
#y_arrays = np.asarray(y_list)
print(len(X_list))
with tf.Session() as sess:
	init.run()
	for i in range(300):
		for iteration in range(43,293//batch_size):
			X_batch, y_batch = np.array([X_list[iteration]]), np.array([y_list[iteration]])
			#for iteration in range(n_iterations):
			#X_batch, y_batch = datax, datay
			X_batch = X_batch.reshape((-1, n_steps, n_inputs))
			y_batch = y_batch.reshape((-1, n_steps, n_outputs))
			sess.run(training_op, feed_dict={X: X_batch, y: y_batch})
			print(iteration)
			if iteration % 100 == 0:
				mse = loss.eval(feed_dict={X: X_batch, y: y_batch})
				print(iteration, "\tMSE: ", mse)
	sequence = [110,105,109,97,108,115,46,10,10,73,116,32,119,111,117,108,100,32,98,101]
	for iteration in range(300):
		X_batch = np.array(sequence[-n_steps:]).reshape(1, n_steps, 1)
		y_pred = sess.run(outputs, feed_dict={X: X_batch})
		sequence.append(np.round(y_pred[0, 1, 0]))
		print(iteration)
	print(sequence)