import numpy as np
import matplotlib.pyplot as plot
def hash_fun(a,b,n_buckets,x):
    p = 123457
    y = x % p
    hash_val = (a * y + b) % p
    return hash_val % n_buckets
cArray = np.zeros((5,10000))
a = []
b = []
E = []
WFe = []
with open('D:/HW4-q4/hash_params.txt','r') as f:
    for line in f:
        lineArr = line.split()
        a.append(int(lineArr[0]))
        b.append(int(lineArr[1]))
f.close()
t = 0
f = open('D:/HW4-q4/words_stream_tiny.txt','r')
for line in f:
        lineTemp = line.split()
        streamNumber = int (lineTemp[0])
        t += 1
        for i in range(5):
                cArray[i][hash_fun(a[i],b[i],10000,streamNumber)] += 1
f.close()
print ('read data done')
f = open('D:/HW4-q4/counts_tiny.txt','r')
for line in f:
        lineTemp = line.split()
        number = int (lineTemp[0])
        Fi = int (lineTemp[1])
        tempArr = np.zeros(5)
        for i in range(5):
                 tempArr[i] = cArray[i][hash_fun(a[i],b[i],10000,number)]
        np.amin(tempArr)
        E.append(float(np.amin(tempArr) - Fi) / Fi)
        WFe.append(float(Fi)/t)
f.close()
plot.plot(WFe,E,'.')
plot.xscale('log')
plot.yscale('log')
plot.xlabel('word frequency')
plot.ylabel('relative error')
plot.title('Data Stream ')
plot.show()
 
        
