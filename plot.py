import matplotlib.pyplot as plt

a=[]
b=[]
with open('D:/out.txt','r') as f:
    for line in f:
        data=line.split()
        a.append(float(data[0]))
        b.append(float(data[1]))
f.close()
plt.plot(a,b,'.')
plt.xscale('log')
plt.yscale('log')
plt.title('Data Stream')
plt.show()
